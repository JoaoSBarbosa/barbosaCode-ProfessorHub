package br.com.joaobarbosadev.professorhub.api.dropbox.services;

import br.com.joaobarbosadev.professorhub.api.common.Utils.Util;
import br.com.joaobarbosadev.professorhub.api.common.exceptions.custom.CustomEntityNotFoundException;
import br.com.joaobarbosadev.professorhub.api.common.interfaces.DropboxAPI;
import br.com.joaobarbosadev.professorhub.api.dropbox.dtos.DropboxAuth;
import br.com.joaobarbosadev.professorhub.api.dropbox.dtos.DropboxInitial;
import br.com.joaobarbosadev.professorhub.api.dropbox.mappers.DropboxMapper;
import br.com.joaobarbosadev.professorhub.core.models.entities.Dropbox;
import br.com.joaobarbosadev.professorhub.core.repositories.DropboxRepository;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DropboxConfigServiceImpl implements DropboxConfigService {
    //    private final DropboxRepository dropboxRepository;
    private final DropboxMapper dropboxMapper;
    //    private final RestTemplateBuilder restTemplateBuilder;
    private final DropboxRepository dropboxRepository;
    private final RestTemplate restTemplate;
    private final DropboxAPI dropboxAPI;

    @Override
    @Transactional
    public Dropbox initialize(DropboxInitial dropboxInitial) {
        var dropbox = dropboxMapper.toDropbox(dropboxInitial);
        dropbox = dropboxRepository.save(dropbox);
        return dropbox;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Dropbox> findAll() {
        return dropboxRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Dropbox findById(Long id) {
        return dropboxRepository.findById(id).orElseThrow(() -> new RuntimeException("Não localizado com o id: " + id));
    }

    @Override
    public String getRefreshToken() throws IOException {
        Dropbox dropbox = getDropboxEntity();
        if (dropbox.getAuthCode() == null || dropbox.getAuthCode().isEmpty()) {
            throw new RuntimeException("Auth code não encontrado. Salve um código antes de gerar o refresh token.");
        }
        String clientId = dropbox.getApiKey();
        String clientSecret = dropbox.getApiSecret();
        String authorizationCode = dropbox.getAuthCode();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", authorizationCode);
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("https://api.dropbox.com/oauth2/token", requestEntity, String.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            JsonObject responseBody = JsonParser.parseString(response.getBody()).getAsJsonObject();

            if (responseBody.has("refresh_token")) {
                dropbox.setRefreshToken(responseBody.get("refresh_token").getAsString());
            } else {
                throw new RuntimeException("Erro: resposta não contém refresh_token.");
            }
            if (responseBody.has("access_token")) {
                dropbox.setAccessToken(responseBody.get("access_token").getAsString());
            }
            if (responseBody.has("expires_in")) {
                long expiresIn = responseBody.get("expires_in").getAsLong();
                LocalDateTime expirationDate = LocalDateTime.now().plusSeconds(expiresIn);
                dropbox.setExpiresIn(expirationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                dropbox.setExpiresAccessDateTime(expirationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
            dropboxRepository.save(dropbox);
            return responseBody.get("refresh_token").getAsString();
        } else {
            throw new RuntimeException("Erro ao obter o refresh token do Dropbox. Status: " + response.getStatusCodeValue());
        }
    }


    private HttpEntity<MultiValueMap<String, String>> createTokenRequest(Dropbox dropbox) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", dropbox.getAuthCode());
        body.add("client_id", dropbox.getApiKey());
        body.add("client_secret", dropbox.getApiSecret());

        return new HttpEntity<>(body, headers);
    }

    @Override
    public Dropbox saveAuthCode(DropboxAuth authCode) throws IOException {
        if (authCode.getAuthCode() == null || authCode.getAuthCode().isEmpty()) {
            throw new RuntimeException("Codigo de autorização deve estar preenchido");
        }
        Dropbox dropbox = getDropboxEntity();
        dropbox.setAuthCode(authCode.getAuthCode());
        dropbox = dropboxRepository.save(dropbox);
        return dropbox;


    }

    //    @Override
//    public void checkoutValidateAcessToken() throws IOException {
//        Dropbox dropbox = getDropboxEntity();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime dateExpires = LocalDateTime.parse(dropbox.getExpiresIn(), formatter);
//        LocalDateTime now = LocalDateTime.now();
//
//        if (dateExpires.isBefore(now)) {
//            try {
//                refreshAccessToken(dropbox);
//            }catch (Exception e) {
//                e.printStackTrace();
//               throw new RuntimeException("Erro ao fazer o refresh token do Dropbox."+ e.getMessage());
//            }
//        }
//    }
    @Override
    public void checkoutValidateAcessToken() throws IOException {
        Dropbox dropbox = getDropboxEntity();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Converte a data de expiração do token para LocalDateTime
        LocalDateTime dateExpires = LocalDateTime.parse(dropbox.getExpiresIn(), formatter);
        LocalDateTime now = LocalDateTime.now();

        // Verifica se o token expirou
        if (dateExpires.isBefore(now)) {
            try {
                refreshAccessToken(dropbox);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao fazer o refresh token do Dropbox: " + e.getMessage());
            }
        }
    }

    @Override
    public void refreshAccessToken(Dropbox dropbox) throws IOException {
        String refreshAccessToken = dropboxAPI.refreshAccessToken(
                dropbox.getRefreshToken(),
                "refresh_token",
                dropbox.getApiKey(),
                dropbox.getApiSecret()
        );

        JsonObject refreshTokenObj = JsonParser.parseString(refreshAccessToken).getAsJsonObject();
        String newAccessToken = refreshTokenObj.get("access_token").getAsString();

        // LOGANDO O NOVO TOKEN
        System.out.println("NOVO ACCESS TOKEN: " + newAccessToken);

        dropbox.setAccessToken(newAccessToken);

        // Converte expires_in (segundos) para LocalDateTime
        long expiresInSeconds = refreshTokenObj.get("expires_in").getAsLong();
        LocalDateTime expirationDate = LocalDateTime.now().plusSeconds(expiresInSeconds);

        // Salva expires_in como uma data no formato "yyyy-MM-dd HH:mm:ss"
        dropbox.setExpiresIn(expirationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        // Define a data de criação e expiração do token
        dropbox.setCreateAccessDateTime(Util.getDateTime());
        dropbox.setExpiresAccessDateTime(expirationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        dropboxRepository.save(dropbox);
    }

//    @Override
//    public void refreshAccessToken(Dropbox dropbox) throws IOException {
//        String refreshAccessToken = dropboxAPI.refreshAccessToken(
//                dropbox.getRefreshToken(),
//                "refresh_token",
//                dropbox.getApiKey(),
//                dropbox.getApiSecret()
//        );
//        JsonObject refreshTokenObj = JsonParser.parseString(refreshAccessToken).getAsJsonObject();
//        dropbox.setAccessToken(refreshTokenObj.get("access_token").getAsString());
//        dropbox.setExpiresIn(refreshTokenObj.get("expires_in").getAsString());
//        dropbox.setCreateAccessDateTime(Util.getDateTime());
//        dropbox.setExpiresAccessDateTime(Util.expiresDateTime());
//
//        dropboxRepository.save(dropbox);
//    }

    private Dropbox getDropboxEntity() {
        return dropboxRepository.findById(1L).orElseThrow(() -> new CustomEntityNotFoundException("Não foi possivel localizar registros de configuração dropbox com o id 1"));

    }
}
