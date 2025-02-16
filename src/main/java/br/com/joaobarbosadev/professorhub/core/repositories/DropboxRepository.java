package br.com.joaobarbosadev.professorhub.core.repositories;

import br.com.joaobarbosadev.professorhub.core.models.entities.Dropbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DropboxRepository extends JpaRepository<Dropbox, Long> {
}
