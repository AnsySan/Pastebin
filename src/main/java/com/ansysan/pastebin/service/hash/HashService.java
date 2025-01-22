package com.ansysan.pastebin.service.hash;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface HashService {

    void generateHashes();

    List<String> getHashes(int amount);

    CompletableFuture<List<String>> getHashesAsync(int amount);
}