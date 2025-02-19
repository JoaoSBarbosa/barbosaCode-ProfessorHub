package br.com.joaobarbosadev.professorhub.api.common.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "https://api.dropbox.com/", name = "dropbox")
public interface DropboxAPI {
    @PostMapping("/oauth2/token")
    String refreshAccessToken(
            @RequestParam String refresh_token,
            @RequestParam String grant_type,
            @RequestParam String client_id,
            @RequestParam String client_secret
    );
}
