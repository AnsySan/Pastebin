package com.ansysan.pastebin.controller;

import com.ansysan.pastebin.dto.Request;
import com.ansysan.pastebin.dto.Response;
import com.ansysan.pastebin.service.url.UrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Valid
@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @GetMapping("r/{hash}")
    public RedirectView getRedirectView(@PathVariable("hash") String hash) {
        return urlService.getRedirectView(hash);
    }

    @GetMapping("/{hash}")
    public String getUrl(@PathVariable("hash") String hash) {
        return urlService.getUrl(hash);
    }

    @PostMapping("/url")
    public Response createShortUrl(@RequestBody Request dto) {
        return urlService.createShortUrl(dto);
    }
}