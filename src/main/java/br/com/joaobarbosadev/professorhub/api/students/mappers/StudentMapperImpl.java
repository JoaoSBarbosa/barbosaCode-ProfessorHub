package br.com.joaobarbosadev.professorhub.api.students.mappers;
import br.com.joaobarbosadev.professorhub.api.students.dtos.StudentRequest;
import br.com.joaobarbosadev.professorhub.api.students.dtos.StudentResponse;
import br.com.joaobarbosadev.professorhub.core.models.entities.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapperImpl implements StudentMapper {


    @Override
    public StudentResponse toStudentResponse(Student student) {
        if (student == null) return null;

        return StudentResponse
                .builder()
                .studentId(student.getStudentId())
                .name(student.getName())
                .email(student.getEmail())
                .teacher(student.getTeacher())
                .createdAt(student.getCreatedAt())
                .updatedAt(student.getUpdatedAt())
                .classDate(student.getClassDate())
                .build();
    }

    @Override
    public Student toStudentByRequest(StudentRequest studentRequest) {
        if (studentRequest == null) return null;
        return Student
                .builder()
                .name(studentRequest.getNome())
                .email(studentRequest.getEmail())
                .classDate(studentRequest.getDataAula())
                .build();
    }

    @Override
    public Student toStudent(StudentResponse studentResponse) {
        if (studentResponse == null) return null;
        return Student
                .builder()
                .classDate(studentResponse.getClassDate())
                .studentId(studentResponse.getStudentId())
                .teacher(studentResponse.getTeacher())
                .email(studentResponse.getEmail())
                .build();
    }
}
