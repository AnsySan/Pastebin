package com.ansysan.pastebin.service.like;

import com.ansysan.pastebin.dto.LikeDto;
import com.ansysan.pastebin.exception.NotFoundException;
import com.ansysan.pastebin.mapper.LikeMapper;
import com.ansysan.pastebin.model.Comment;
import com.ansysan.pastebin.model.Like;
import com.ansysan.pastebin.model.Post;
import com.ansysan.pastebin.repository.CommentRepository;
import com.ansysan.pastebin.repository.LikeRepository;
import com.ansysan.pastebin.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeMapper likeMapper;

    @Override
    @Transactional
    public LikeDto addLikeOnPost(long userId, long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post with postId: " + postId + " not found"));

        Like like = likeRepository.findByUserIdAndPostId(userId, postId)
                .orElse(null); // Check if user already liked the post

        if (like == null) {
            like = new Like();
            like.setUserId(userId);
            like.setPostId(postId);

            like = likeRepository.save(like);
            post.getLikesIds().add(like.getId());
        }

        return likeMapper.toDto(like);
    }

    @Override
    @Transactional
    public void removeLikeFromPost(long userId, long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post with postId: " + postId + " not found"));

        Like like = likeRepository.findByUserIdAndPostId(userId, postId)
                .orElseThrow(() -> new NotFoundException(
                        "Not found like on post with postId: " + postId + " by user userId: " + userId));

        post.getLikesIds().remove(like.getId());

        likeRepository.deleteById(like.getId());
    }

    @Override
    @Transactional
    public LikeDto addLikeOnComment(long userId, long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment with id: " + commentId + " not found"));

        Like like = likeRepository.findByUserIdAndCommentId(userId, commentId)
                .orElse(null);

        if (like == null) {
            like = new Like();
            like.setUserId(userId);
            like.setCommentId(commentId);

            like = likeRepository.save(like);
            comment.getLikesIds().add(like.getId());
        }

        return likeMapper.toDto(like);
    }

    @Override
    @Transactional
    public void removeLikeFromComment(long userId, long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment with commentId: " + commentId + " not found"));

        Like like = likeRepository.findByUserIdAndCommentId(userId, commentId)
                .orElseThrow(() -> new NotFoundException(
                        "Not found like on comment with commentId: " + commentId + " by user userId: " + userId));

        comment.getLikesIds().remove(like.getId());

        likeRepository.deleteById(like.getId());
    }
}