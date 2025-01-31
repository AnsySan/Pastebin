package com.ansysan.pastebin.service.post;

import com.ansysan.pastebin.dto.PostCreateDto;
import com.ansysan.pastebin.dto.PostDto;
import com.ansysan.pastebin.dto.PostResponse;
import com.ansysan.pastebin.dto.PostUpdateDto;
import com.ansysan.pastebin.exception.NotFoundException;
import com.ansysan.pastebin.mapper.PostMapper;
import com.ansysan.pastebin.model.Post;
import com.ansysan.pastebin.repository.PostRepository;
import com.ansysan.pastebin.service.s3.AmazonS3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final AmazonS3Service s3Service;
    private final RedisTemplate<String, Object> redisTemplate;

    public static final String HASH_KEY = "post";

    @Override
    public PostResponse getPostById(Long postId) {
        Post post = (Post) redisTemplate.opsForHash().get(HASH_KEY, postId);

        if (post == null) {
            post = postRepository.findById(postId)
                    .orElseThrow(() -> new NotFoundException(String.format("Post with id %s not found", postId)));

            String postContent = s3Service.getContent(post.getContentId());

            PostResponse postResponse = postMapper.toPostResponse(post);
            postResponse.setContent(postContent);

            redisTemplate.opsForHash().put(HASH_KEY, postId, post);

            return postResponse;
        }

        String postContent = s3Service.getContent(post.getContentId());
        PostResponse postResponse = postMapper.toPostResponse(post);
        postResponse.setContent(postContent);

        return postResponse;
    }

    @Override
    public PostResponse getPostByUrl(String url) {
        Post post = (Post) redisTemplate.opsForHash().get(HASH_KEY, url);

        if (post == null) {
            post = postRepository.findPostByUrl(url)
                    .orElseThrow(() -> new NotFoundException(String.format("Post with url %s not found", url)));

            String postContent = s3Service.getContent(post.getContentId());

            PostResponse postResponse = postMapper.toPostResponse(post);
            postResponse.setContent(postContent);

            redisTemplate.opsForHash().put(HASH_KEY, post.getUrl(), post);

            return postResponse;
        }

        String postContent = s3Service.getContent(post.getContentId());
        PostResponse postResponse = postMapper.toPostResponse(post);
        postResponse.setContent(postContent);

        return postResponse;
    }

    @Override
    @Transactional
    public PostDto create(PostCreateDto postCreateDto) {
        Post post = postMapper.toEntity(postCreateDto);

        String contentId = s3Service.uploadContent(postCreateDto.getContent());
        post.setContentId(contentId);

        post = postRepository.save(post);

        redisTemplate.opsForHash().put(HASH_KEY, post.getId(), post);

        return postMapper.toDto(post);
    }

    @Override
    @Transactional
    public PostDto update(Long id, PostUpdateDto postUpdateDto) {
        Post post = (Post) redisTemplate.opsForHash().get(HASH_KEY, id);

        if (post == null) {
            post = postRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException(String.format("Post with id %s not found", id)));

            s3Service.deleteContent(post.getContentId());

            String newContentId = s3Service.uploadContent(postUpdateDto.getContent());
            post.setContentId(newContentId);

            post = postRepository.save(post);

            redisTemplate.opsForHash().put(HASH_KEY, post.getId(), post);
        }

        s3Service.deleteContent(post.getContentId());

        String newContentId = s3Service.uploadContent(postUpdateDto.getContent());
        post.setContentId(newContentId);

        post = postRepository.save(post);

        redisTemplate.opsForHash().put(HASH_KEY, post.getId(), post);

        return postMapper.toDto(post);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Post with id %s not found", id)));

        s3Service.deleteContent(post.getContentId());

        postRepository.deleteById(id);

        redisTemplate.opsForHash().delete(HASH_KEY, id);
    }

    @Override
    public List<PostDto> findAllAuthorPosts(Long userId) {
        return postRepository.findAllByUserId(userId).stream()
                .map(post -> {
                    redisTemplate.opsForHash().put(HASH_KEY, post.getId(), post);
                    return postMapper.toDto(post);
                })
                .toList();
    }
}