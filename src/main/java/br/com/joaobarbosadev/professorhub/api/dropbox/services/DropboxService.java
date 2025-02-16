package br.com.joaobarbosadev.professorhub.api.dropbox.services;

import br.com.joaobarbosadev.professorhub.api.dropbox.dtos.DropboxInitial;
import br.com.joaobarbosadev.professorhub.core.models.entities.Dropbox;

import java.util.List;

public interface DropboxService {

    Dropbox initialize(DropboxInitial dropboxInitial);
    List<Dropbox> findAll();
    Dropbox findById(Long id);
}
