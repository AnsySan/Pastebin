package com.ansysan.pastebin.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Entity
    @Table(name = "comment")
    public class Comment {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @Column(name = "content", nullable = false, length = 4096)
        private String content;

        @Column(name = "author_id", nullable = false)
        private long authorId;

        @ElementCollection
        @CollectionTable(name = "comment_likes", joinColumns = @JoinColumn(name = "comment_id"))
        @Column(name = "user_id")
        private List<Long> likesIds = new ArrayList<>();

        @ManyToOne
        @JoinColumn(name = "post_id", nullable = false)
        private Post post;

        @CreationTimestamp
        @Column(name = "created_at", updatable = false)
        private LocalDateTime createdAt;

        @UpdateTimestamp
        @Column(name = "updated_at")
        private LocalDateTime updatedAt;

        @Column(name = "verified")
        private Boolean verified = false; // Set a default value

        @Column(name = "verified_date")
        private LocalDateTime verifiedDate;
    }