package br.com.joaobarbosadev.professorhub.core.repositories;
import br.com.joaobarbosadev.professorhub.core.models.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    @Query("SELECT t FROM Teacher t WHERE t.descricao LIKE %:description%")
    List<Teacher> findByDescription(@Param("description") String description);

    @Query("SELECT t FROM Teacher t WHERE t.email = :email")
    Optional<Teacher> findByTeacherByEmail(@Param("email") String email);
    boolean existsByEmail(String email);
}
