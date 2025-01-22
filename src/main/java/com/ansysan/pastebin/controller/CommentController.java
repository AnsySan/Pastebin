package com.ansysan.pastebin.controller;

import com.ansysan.pastebin.context.UserContext;
import com.ansysan.pastebin.dto.CommentDto;
import com.ansysan.pastebin.dto.CommentToCreateDto;
import com.ansysan.pastebin.dto.CommentToUpdateDto;
import com.ansysan.pastebin.service.comment.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final UserContext userContext;

    @PostMapping("/post/{postId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto createComment(@PathVariable("postId") long postId,
                                    @RequestBody @Valid CommentToCreateDto commentDto) {
        long userId = userContext.getUserId();
        return commentService.createComment(postId, userId, commentDto);
    }

    @GetMapping("/post/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> getAllPostComments(@PathVariable("postId") long postId) {
        return commentService.getAllPostComments(postId);
    }

    @PutMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto updateComment(@PathVariable("commentId") long commentId,
                                    @RequestBody @Valid CommentToUpdateDto commentDto) {
        long userId = userContext.getUserId();
        return commentService.updateComment(commentId, userId, commentDto);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@PathVariable("commentId") long commentId) {
        long userId = userContext.getUserId();
        commentService.deleteComment(commentId, userId);
    }
}