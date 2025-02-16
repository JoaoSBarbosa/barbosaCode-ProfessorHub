package br.com.joaobarbosadev.professorhub.api.dropbox.mappers;

import br.com.joaobarbosadev.professorhub.api.dropbox.dtos.DropboxInitial;
import br.com.joaobarbosadev.professorhub.core.models.entities.Dropbox;

public interface DropboxMapper {

    Dropbox toDropbox(DropboxInitial dropbox);
    DropboxInitial toDropboxInitial(Dropbox dropbox);

}
