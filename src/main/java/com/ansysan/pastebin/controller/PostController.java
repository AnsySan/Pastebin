package com.ansysan.pastebin.controller;

import com.ansysan.pastebin.dto.PostCreateDto;
import com.ansysan.pastebin.dto.PostDto;
import com.ansysan.pastebin.dto.PostResponse;
import com.ansysan.pastebin.dto.PostUpdateDto;
import com.ansysan.pastebin.service.post.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("id/{postId}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long postId) {
        PostResponse postResponse = postService.getPostById(postId);
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/{url}")
    public ResponseEntity<PostResponse> getPostByUrl(@PathVariable String url) {
        PostResponse postResponse = postService.getPostByUrl(url);
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("user/{userId}")
    public List<PostDto> findAllAuthorPosts(@PathVariable Long userId) {
        return postService.findAllAuthorPosts(userId);
    }

    @PostMapping
    public PostDto create(@RequestBody @Valid PostCreateDto postCreateDto) {
        return postService.create(postCreateDto);
    }

    @PatchMapping("{postId}")
    public PostDto update(
            @PathVariable Long postId,
            @RequestBody @Valid PostUpdateDto postUpdateDto
    ) {
        return postService.update(postId, postUpdateDto);
    }

    @DeleteMapping("{postId}")
    public void deleteById(@PathVariable Long postId) {
        postService.deleteById(postId);
    }
}