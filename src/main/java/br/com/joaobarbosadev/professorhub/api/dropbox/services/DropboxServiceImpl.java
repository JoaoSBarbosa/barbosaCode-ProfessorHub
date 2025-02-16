package br.com.joaobarbosadev.professorhub.api.dropbox.services;

import br.com.joaobarbosadev.professorhub.api.dropbox.dtos.DropboxInitial;
import br.com.joaobarbosadev.professorhub.api.dropbox.mappers.DropboxMapper;
import br.com.joaobarbosadev.professorhub.core.models.entities.Dropbox;
import br.com.joaobarbosadev.professorhub.core.repositories.DropboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DropboxServiceImpl implements DropboxService {
    private final DropboxRepository dropboxRepository;
    private final DropboxMapper dropboxMapper;

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
}
