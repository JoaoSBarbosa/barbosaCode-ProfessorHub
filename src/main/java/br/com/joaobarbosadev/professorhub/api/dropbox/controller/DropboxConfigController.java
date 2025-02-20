package br.com.joaobarbosadev.professorhub.api.dropbox.controller;

import br.com.joaobarbosadev.professorhub.api.dropbox.dtos.DropboxAuth;
import br.com.joaobarbosadev.professorhub.api.dropbox.dtos.DropboxInitial;
import br.com.joaobarbosadev.professorhub.api.dropbox.services.DropboxConfigService;
import br.com.joaobarbosadev.professorhub.core.models.entities.Dropbox;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/config/dropbox")
@PreAuthorize("isAuthenticated()")
public class DropboxConfigController {

    private final DropboxConfigService dropboxConfigService;

    @PostMapping
    public ResponseEntity<Dropbox> save(@RequestBody @Valid DropboxInitial dropbox) {
        var dropboxResponse = dropboxConfigService.initialize(dropbox);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dropboxResponse.getId()).toUri();
        return ResponseEntity.created(uri).body(dropboxResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dropbox> findById(@PathVariable Long id) {
        var dropboxResponse = dropboxConfigService.findById(id);
        return ResponseEntity.ok(dropboxResponse);
    }

    public Dropbox initialize(DropboxInitial dropboxInitial) {
        return dropboxConfigService.initialize(dropboxInitial);
    }

    @GetMapping("/all")
    public List<Dropbox> findAll() {
        return dropboxConfigService.findAll();
    }

    @GetMapping
    public ResponseEntity<String> getRefreshToken() throws IOException {
        String retorno = dropboxConfigService.getRefreshToken();
        return ResponseEntity.ok(retorno);
    }

    @PostMapping("/auth")
    public ResponseEntity<Dropbox> saveAuthCode(@RequestBody DropboxAuth authCode) throws IOException {
        Dropbox dropbox = dropboxConfigService.saveAuthCode(authCode);
        return ResponseEntity.ok(dropbox);
    }
}
