package br.com.joaobarbosadev.professorhub.api.dropbox.mappers;

import br.com.joaobarbosadev.professorhub.api.dropbox.dtos.DropboxInitial;
import br.com.joaobarbosadev.professorhub.core.models.entities.Dropbox;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class DropboxMapperImpl implements DropboxMapper{
    @Override
    public Dropbox toDropbox(DropboxInitial dropbox) {
        if(dropbox == null) return null;
        return Dropbox.builder()
                .accessToken(dropbox.getAccessToken())
                .apiKey(dropbox.getApiKey())
                .apiSecret(dropbox.getApiSecret())
                .build();
    }

    @Override
    public DropboxInitial toDropboxInitial(Dropbox dropbox) {
        if(dropbox == null) return null;
        return DropboxInitial.builder().accessToken(dropbox.getAccessToken()).apiKey(dropbox.getApiKey()).apiSecret(dropbox.getApiSecret()) .build();
    }
}
