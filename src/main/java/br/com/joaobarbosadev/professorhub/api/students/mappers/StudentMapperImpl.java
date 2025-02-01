package br.com.joaobarbosadev.professorhub.api.students.mappers;

import br.com.joaobarbosadev.professorhub.api.students.dtos.StudentResponse;
import br.com.joaobarbosadev.professorhub.api.teachers.mappers.TeacherMapper;
import br.com.joaobarbosadev.professorhub.core.models.entities.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapperImpl implements StudentMapper {

    private final TeacherMapper teacherMapper;

    public StudentMapperImpl(TeacherMapper teacherMapper) {
        this.teacherMapper = teacherMapper;
    }

    @Override
    public StudentResponse toStudentResponse(Student student) {
        if(student == null) return null;

        return StudentResponse
                .builder()
                .studentId( student.getStudentId() )
                .name( student.getName() )
                .email( student.getEmail() )
                .teacher(  teacherMapper.toTeacherResponse(student.getTeacher()) )
                .createdAt( student.getCreatedAt() )
                .updatedAt( student.getUpdatedAt() )
                .classDate( student.getClassDate() )
                .build();
    }

    @Override
    public Student toStudent(StudentResponse studentResponse) {
        return null;
    }
}
