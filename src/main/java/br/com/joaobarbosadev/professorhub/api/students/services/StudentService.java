package br.com.joaobarbosadev.professorhub.api.students.services;
import br.com.joaobarbosadev.professorhub.api.students.dtos.StudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface StudentService {
    Page<StudentResponse> findAllPage(String name,String email, Long teacherId, int size, int page, String sortField, String sortOrder);
    List<StudentResponse> findAll();
    StudentResponse findById(Long id);
    StudentResponse save(StudentResponse student);
    StudentResponse update(StudentResponse student);
    void deleteById(Long id);
}
