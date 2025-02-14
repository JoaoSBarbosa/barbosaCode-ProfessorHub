package br.com.joaobarbosadev.professorhub.core.repositories;

import br.com.joaobarbosadev.professorhub.core.models.entities.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidTokenRepository extends JpaRepository<InvalidToken, Long> {
    boolean existsByToken(String token);
}
