package com.ansysan.pastebin.service.comment;

import com.ansysan.pastebin.dto.CommentDto;
import com.ansysan.pastebin.dto.CommentToCreateDto;
import com.ansysan.pastebin.dto.CommentToUpdateDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(Long postId, Long userId, CommentToCreateDto commentDto);

    List<CommentDto> getAllPostComments(Long postId);

    CommentDto updateComment(Long commentId, Long userId, CommentToUpdateDto commentDto);

    void deleteComment(Long commentId, Long userId);

    CommentDto getById(Long commentId);
}