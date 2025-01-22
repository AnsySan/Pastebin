package com.ansysan.pastebin.scheduler;

import com.ansysan.pastebin.service.hash.HashService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenerateHashesScheduler {

    private final HashService hashService;

    @Scheduled(cron = "${scheduler.generate.cron}")
    public void generateHashes() {
        hashService.generateHashes();
    }
}