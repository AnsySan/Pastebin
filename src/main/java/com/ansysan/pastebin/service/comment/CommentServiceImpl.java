package com.ansysan.pastebin.service.comment;

import com.ansysan.pastebin.dto.CommentDto;
import com.ansysan.pastebin.dto.CommentToCreateDto;
import com.ansysan.pastebin.dto.CommentToUpdateDto;
import com.ansysan.pastebin.exception.NotFoundException;
import com.ansysan.pastebin.mapper.CommentMapper;
import com.ansysan.pastebin.model.Comment;
import com.ansysan.pastebin.model.Post;
import com.ansysan.pastebin.repository.CommentRepository;
import com.ansysan.pastebin.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    @Transactional
    public CommentDto createComment(Long postId, Long userId, CommentToCreateDto commentDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(String.format("Post with id %d not found", postId)));

        Comment comment = commentMapper.toEntity(commentDto);
        comment.setAuthorId(userId);
        comment.setPost(post);

        commentRepository.save(comment);
        log.info("Created comment on post {} by user {}",  postId, userId);

        return commentMapper.toDto(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getAllPostComments(Long postId) {
        return commentRepository.findAllByPostId(postId).stream()
                .sorted(Comparator.comparing(Comment::getCreatedAt))
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommentDto updateComment(Long commentId, Long userId, CommentToUpdateDto updatedCommentDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(String.format("Comment with id %s not found", commentId)));

        commentMapper.update(updatedCommentDto, comment);

        commentRepository.save(comment);
        log.info("Updated comment {} on post {} by user {}", commentId, comment.getId(), userId);

        return commentMapper.toDto(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(String.format("Comment with id %s not found", commentId)));

        commentRepository.deleteById(commentId);
        log.info("Deleted comment {}", commentId);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDto getById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(String.format("Comment with id %d not found", commentId)));

        return commentMapper.toDto(comment);
    }
}