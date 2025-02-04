package br.com.joaobarbosadev.professorhub.core.repositories;
import br.com.joaobarbosadev.professorhub.core.models.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    @Query("SELECT t FROM Teacher t WHERE t.description LIKE %:description%")
    List<Teacher> findByDescription(@Param("description") String description);

    boolean existsByEmail(String email);
}
