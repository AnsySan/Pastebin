package com.ansysan.pastebin.service.hash;

import com.ansysan.pastebin.encoder.Base62Encoder;
import com.ansysan.pastebin.model.Hash;
import com.ansysan.pastebin.repository.HashRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class HashServiceImpl implements HashService {

    @Value("${services.hash.batch}")
    private int batchSize;
    private final Base62Encoder encoder;
    private final HashRepository hashRepository;

    @Override
    @Async("generateHashesExecutor")
    @Transactional
    public void generateHashes() {
        List<Hash> hashes = encoder.encodeSymbolsToHash(hashRepository.findUniqueNumbers(batchSize)).stream()
                .map(Hash::new)
                .toList();
        hashRepository.saveAll(hashes);
        List<Hash> h = hashRepository.getAll();
        log.info("Generated new hashes batch");
        log.info("All hashes: {}", h);
    }

    @Override
    @Transactional
    public List<String> getHashes(int amount) throws RuntimeException {
        try {
            return hashRepository.getHashBatch(amount);
        } catch (Exception e) {
            log.error("Error getting hashes", e);
            throw new RuntimeException("Error getting hashes", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Async("generateBatchExecutor")
    public CompletableFuture<List<String>> getHashesAsync(int amount) throws RuntimeException {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return getHashes(amount);
            } catch (Exception e) {
                log.error("Error getting hashes asynchronously", e);
                throw new RuntimeException("Error getting hashes asynchronously", e);
            }
        });
    }
}