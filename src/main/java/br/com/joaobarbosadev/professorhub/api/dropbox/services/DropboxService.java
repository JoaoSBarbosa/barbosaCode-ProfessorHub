package br.com.joaobarbosadev.professorhub.api.dropbox.services;

import br.com.joaobarbosadev.professorhub.api.dropbox.dtos.DropboxAuth;
import br.com.joaobarbosadev.professorhub.api.dropbox.dtos.DropboxInitial;
import br.com.joaobarbosadev.professorhub.core.models.entities.Dropbox;

import java.io.IOException;
import java.util.List;

public interface DropboxService {

    Dropbox initialize(DropboxInitial dropboxInitial);
    List<Dropbox> findAll();
    Dropbox findById(Long id);
    String getRefreshToken() throws IOException;
    Dropbox saveAuthCode(DropboxAuth authCode) throws IOException;
}
