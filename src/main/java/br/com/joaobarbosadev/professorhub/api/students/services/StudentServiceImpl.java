package br.com.joaobarbosadev.professorhub.api.students.services;

import br.com.joaobarbosadev.professorhub.api.students.dtos.StudentRequest;
import br.com.joaobarbosadev.professorhub.api.students.dtos.StudentResponse;
import br.com.joaobarbosadev.professorhub.api.students.helpers.StudentSpecifications;
import br.com.joaobarbosadev.professorhub.api.students.mappers.StudentMapperImpl;
import br.com.joaobarbosadev.professorhub.api.common.exceptions.custom.CustomEntityNotFoundException;
import br.com.joaobarbosadev.professorhub.core.models.entities.Student;
import br.com.joaobarbosadev.professorhub.core.repositories.StudentRepository;
import br.com.joaobarbosadev.professorhub.core.repositories.TeacherRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentMapperImpl studentMapper;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public StudentServiceImpl(StudentMapperImpl studentMapper, StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.studentMapper = studentMapper;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }


    @Override
    public StudentResponse saveStudent(StudentRequest student, Long teacherId) {
        try {
            var teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new CustomEntityNotFoundException("Professor não encontrado com o id " + teacherId));
            var students = studentMapper.toStudentByRequest(student);
            students.setTeacher(teacher);
            var savedStudent = studentRepository.save(students);
            return studentMapper.toStudentResponse(savedStudent);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar dados do aluno", e);
        }
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
