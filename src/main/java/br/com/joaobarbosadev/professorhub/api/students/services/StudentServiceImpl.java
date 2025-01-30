package br.com.joaobarbosadev.professorhub.api.students.services;

import br.com.joaobarbosadev.professorhub.api.students.dtos.StudentResponse;
import br.com.joaobarbosadev.professorhub.api.students.helpers.StudentSpecifications;
import br.com.joaobarbosadev.professorhub.api.students.mappers.StudentMapper;
import br.com.joaobarbosadev.professorhub.core.models.entities.Student;
import br.com.joaobarbosadev.professorhub.core.repositories.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class StudentServiceImpl implements StudentService {

    private final StudentMapper studentMapper;
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentMapper studentMapper, StudentRepository studentRepository) {
        this.studentMapper = studentMapper;
        this.studentRepository = studentRepository;
    }




    @Override
    public Page<StudentResponse> findAllPage(
            String name,
            String email,
            Long teacherId,
            int size,
            int page,
            String sortField,
            String sortOrder
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortField));

        Specification<Student> specification = Specification
                .where(StudentSpecifications.byName(name))
                .and(StudentSpecifications.byEmail(email))
                .and(StudentSpecifications.byTeacherId(teacherId));
        Page<Student> students = studentRepository.findAll(specification, pageable);
        return students.map(studentMapper::toStudentResponse);
    }

    @Override
    public List<StudentResponse> findAll() {
        return List.of();
    }

    @Override
    public StudentResponse findById(Long id) {
        return null;
    }

    @Override
    public StudentResponse save(StudentResponse student) {
        return null;
    }

    @Override
    public StudentResponse update(StudentResponse student) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
