package br.com.joaobarbosadev.professorhub.api.dropbox.controller;

import br.com.joaobarbosadev.professorhub.api.dropbox.dtos.DropboxInitial;
import br.com.joaobarbosadev.professorhub.api.dropbox.services.DropboxService;
import br.com.joaobarbosadev.professorhub.core.models.entities.Dropbox;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dropbox")
@PreAuthorize("isAuthenticated()")
public class DropboxController {

    private final DropboxService dropboxService;

    @PostMapping
    public ResponseEntity<Dropbox> save(@RequestBody @Valid DropboxInitial dropbox) {
        var dropboxResponse = dropboxService.initialize(dropbox);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dropboxResponse.getId()).toUri();
        return ResponseEntity.created(uri).body(dropboxResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dropbox> findById(@PathVariable Long id) {
        var dropboxResponse = dropboxService.findById(id);
        return ResponseEntity.ok(dropboxResponse);
    }

}
