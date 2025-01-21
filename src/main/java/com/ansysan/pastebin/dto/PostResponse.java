package com.ansysan.pastebin.dto;

import com.ansysan.pastebin.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {

    private Long postId;

    private String url;

    private Long authorId;

    private String content;

    private LocalDateTime expiresAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private int likeCount;

    private int commentCount;

    private List<Comment> comments;
}