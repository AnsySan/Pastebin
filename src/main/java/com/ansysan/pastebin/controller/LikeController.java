package com.ansysan.pastebin.controller;

import com.ansysan.pastebin.context.UserContext;
import com.ansysan.pastebin.dto.LikeDto;
import com.ansysan.pastebin.service.like.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;
    private final UserContext userContext;

    @PostMapping("/post/{postId}")
    @ResponseStatus(HttpStatus.CREATED)
    public LikeDto likePost(@PathVariable long postId) {
        long userId = userContext.getUserId();
        return likeService.addLikeOnPost(userId, postId);
    }

    @DeleteMapping("/post/{postId}/{likeId}")
    public void deleteLikeFromPost(@PathVariable long postId) {
        long userId = userContext.getUserId();
        likeService.removeLikeFromPost(userId, postId);
    }

    @PostMapping("/comment/{commentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public LikeDto likeComment(@PathVariable long commentId) {
        long userId = userContext.getUserId();
        return likeService.addLikeOnComment(userId, commentId);
    }

    @DeleteMapping("/comment/{commentId}")
    public void deleteLikeFromComment(@PathVariable long commentId) {
        long userId = userContext.getUserId();
        likeService.removeLikeFromComment(userId, commentId);
    }
}