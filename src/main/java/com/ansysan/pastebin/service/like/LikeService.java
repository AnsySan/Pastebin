package com.ansysan.pastebin.service.like;

import com.ansysan.pastebin.dto.LikeDto;

public interface LikeService {

    LikeDto addLikeOnPost(long userId, long postId);

    LikeDto addLikeOnComment(long userId, long commentId);

    void removeLikeFromPost(long userId, long postId);

    void removeLikeFromComment(long userId, long postId);
}