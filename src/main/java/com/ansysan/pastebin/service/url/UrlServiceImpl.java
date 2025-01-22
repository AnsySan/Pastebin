package com.ansysan.pastebin.service.url;

import com.ansysan.pastebin.config.cache.HashCache;
import com.ansysan.pastebin.dto.Request;
import com.ansysan.pastebin.dto.Response;
import com.ansysan.pastebin.exception.NotFoundException;
import com.ansysan.pastebin.model.Url;
import com.ansysan.pastebin.repository.UrlCacheRepository;
import com.ansysan.pastebin.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

    private final UrlCacheRepository urlCacheRepository;
    private final UrlRepository urlRepository;
    private final HashCache hashCache;

    @Override
    @Transactional(readOnly = true)
    public RedirectView getRedirectView(String hash) {
        return new RedirectView(
                urlCacheRepository.getUrlByHash(hash)
                        .orElseGet(() -> urlRepository.getUrlByHash(hash).map(Url::getUrl)
                                .orElseThrow(() -> new NotFoundException("URL not found for url: " + hash))));
    }

    @Override
    @Transactional(readOnly = true)
    public String getUrl(String hash) {
        return urlCacheRepository.getUrlByHash(hash)
                .orElseGet(() -> urlRepository.getUrlByHash(hash).map(Url::getUrl)
                        .orElseThrow(() -> new NotFoundException("URL not found for url: " + hash)));
    }

    @Override
    @Transactional
    public Response createShortUrl(Request dto) {
        String hash = hashCache.getHash();

        if (hash == null || hash.isEmpty()) {
            throw new RuntimeException("Failed to generate a url");
        }

        urlRepository.save(new Url(hash, dto.getUrl(), LocalDateTime.now()));
        log.info("Saved url: {}", dto.getUrl());

        urlCacheRepository.saveUrlByHash(dto.getUrl(), hash);
        log.info("Saved url: {} by url: {} in cache", dto.getUrl(), hash);
        return new Response(hash);
    }
}