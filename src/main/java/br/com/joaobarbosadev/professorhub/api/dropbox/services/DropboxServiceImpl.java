package br.com.joaobarbosadev.professorhub.api.dropbox.services;

import br.com.joaobarbosadev.professorhub.api.common.exceptions.custom.CustomEntityNotFoundException;
import br.com.joaobarbosadev.professorhub.api.dropbox.dtos.DropboxAuth;
import br.com.joaobarbosadev.professorhub.api.dropbox.dtos.DropboxInitial;
import br.com.joaobarbosadev.professorhub.api.dropbox.mappers.DropboxMapper;
import br.com.joaobarbosadev.professorhub.core.models.entities.Dropbox;
import br.com.joaobarbosadev.professorhub.core.repositories.DropboxRepository;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DropboxServiceImpl implements DropboxService {
//    private final DropboxRepository dropboxRepository;
    private final DropboxMapper dropboxMapper;
//    private final RestTemplateBuilder restTemplateBuilder;
    private final DropboxRepository dropboxRepository;
    private final RestTemplate restTemplate;
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
        return dropboxRepository.findById(id).orElseThrow(()-> new RuntimeException("Não localizado com o id: " +id));
    }

    @Override
    public String getRefreshToken() throws IOException {
        Dropbox dropbox = getDropboxEntity();

        if (dropbox.getAuthCode() == null || dropbox.getAuthCode().isEmpty()) {
            throw new RuntimeException("Auth code não encontrado. Salve um código antes de gerar o refresh token.");
        }

        ResponseEntity<String> response = restTemplate.exchange(
                "https://api.dropbox.com/oauth2/token",
                HttpMethod.POST,
                createTokenRequest(dropbox),
                String.class
        );

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            JsonObject responseBody = JsonParser.parseString(response.getBody()).getAsJsonObject();

            if (!responseBody.has("refresh_token")) {
                throw new RuntimeException("Erro: resposta não contém refresh_token.");
            }

            String refreshToken = responseBody.get("refresh_token").getAsString();
            int expiresInValue = responseBody.has("expires_in") ? responseBody.get("expires_in").getAsInt() : 14400; // Padrão: 4 horas

            // Calcula a data de expiração com base no expires_in
            LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
            LocalDateTime expiresAt = now.plusSeconds(expiresInValue);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Convertendo para String
            String createAccessDateTimeStr = now.format(formatter);
            String expiresAccessDateTimeStr = expiresAt.format(formatter);
            String expiresInStr = String.valueOf(expiresInValue); // Conversão correta para String

            // Atualiza os valores na entidade
            dropbox.setRefreshToken(refreshToken);
            dropbox.setCreateAccessDateTime(createAccessDateTimeStr);
            dropbox.setExpiresIn(expiresInStr); // Agora é String
            dropbox.setExpiresAccessDateTime(expiresAccessDateTimeStr);

            dropboxRepository.save(dropbox);

            return refreshToken;
        } else {
            throw new RuntimeException("Erro ao obter o refresh token do Dropbox. Status: " + response.getStatusCodeValue());
        }
    }



//    @Override
//    public String getRefreshToken() throws IOException {
//        Dropbox dropbox = getDropboxEntity();
//
//        if (dropbox.getAuthCode() == null || dropbox.getAuthCode().isEmpty()) {
//            throw new RuntimeException("Auth code não encontrado. Salve um código antes de gerar o refresh token.");
//        }
//
//        ResponseEntity<String> response = restTemplate.exchange(
//                "https://api.dropbox.com/oauth2/token",
//                HttpMethod.POST,
//                createTokenRequest(dropbox),
//                String.class
//        );
//
//        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
//            JsonObject responseBody = JsonParser.parseString(response.getBody()).getAsJsonObject();
//
//            if (!responseBody.has("refresh_token")) {
//                throw new RuntimeException("Erro: resposta não contém refresh_token.");
//            }
//
//            String refreshToken = responseBody.get("refresh_token").getAsString();
//
//            // Atualiza o refresh token na entidade
//            dropbox.setRefreshToken(refreshToken);
//            dropboxRepository.save(dropbox);
//
//            return refreshToken;
//        } else {
//            throw new RuntimeException("Erro ao obter o refresh token do Dropbox. Status: " + response.getStatusCodeValue());
//        }
//    }

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
        if(authCode.getAuthCode() == null || authCode.getAuthCode().isEmpty()) {
            throw new RuntimeException("Codigo de autorização deve estar preenchido");
        }
        Dropbox dropbox = getDropboxEntity();
        dropbox.setAuthCode(authCode.getAuthCode());
        dropbox = dropboxRepository.save(dropbox);
        return dropbox;


    }

    private Dropbox getDropboxEntity(){
        return dropboxRepository.findById(1L).orElseThrow(()-> new CustomEntityNotFoundException("Não foi possivel localizar registros de configuração dropbox com o id 1"));

    }
}
