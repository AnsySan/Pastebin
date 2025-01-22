package com.ansysan.pastebin.service.url;

import com.ansysan.pastebin.dto.Request;
import com.ansysan.pastebin.dto.Response;
import org.springframework.web.servlet.view.RedirectView;

public interface UrlService {

    RedirectView getRedirectView(String hash);

    Response createShortUrl(Request dto);

    String getUrl(String hash);
}