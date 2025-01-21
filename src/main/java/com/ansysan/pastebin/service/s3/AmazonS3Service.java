package com.ansysan.pastebin.service.s3;

public interface AmazonS3Service {

    String uploadContent(String content);

    String getContent(String contentId);

    void deleteContent(String contentId);
}