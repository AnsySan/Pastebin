package com.ansysan.pastebin.service.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.ansysan.pastebin.exception.S3Exception;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AmazonS3ServiceImpl implements AmazonS3Service {

    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;


    @Override
    public String uploadContent(String content) {
        String key = generateUniqueKey();
        try {
            s3Client.putObject(bucketName, key, content);
            log.info("Content uploaded successfully to S3 with key: {}", key);
        } catch (Exception e) {
            log.error("Failed to upload content to S3: {}", e.getMessage());
            throw new S3Exception("Unable to upload content to S3");
        }

        return key;
    }

    @Override
    public String getContent(String key) {
        try (S3Object s3Object = s3Client.getObject(bucketName, key);
             InputStream inputStream = s3Object.getObjectContent()) {
            log.info("Content downloaded successfully from S3 with key: {}", key);
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Failed to read content from S3: {}", e.getMessage());
            throw new S3Exception("Unable to read content from S3");
        }
    }

    @Override
    public void deleteContent(String key) {
        try {
            s3Client.deleteObject(bucketName, key);
            log.info("Content deleted successfully from S3 with key: {}", key);
        } catch (Exception e) {
            log.error("Failed to delete content from S3: {}", e.getMessage());
            throw new S3Exception("Unable to delete content from S3");
        }
    }

    private String generateUniqueKey() {
        return UUID.randomUUID().toString();
    }
}