package com.ansysan.pastebin.service.post;

import com.ansysan.pastebin.dto.PostCreateDto;
import com.ansysan.pastebin.dto.PostDto;
import com.ansysan.pastebin.dto.PostResponse;
import com.ansysan.pastebin.dto.PostUpdateDto;

import java.util.List;

public interface PostService {

    PostResponse getPostById(Long postId);

    PostDto create(PostCreateDto postCreateDto);

    PostDto update(Long id, PostUpdateDto postUpdateDto);

    void deleteById(Long id);

    List<PostDto> findAllAuthorPosts(Long userId);
}