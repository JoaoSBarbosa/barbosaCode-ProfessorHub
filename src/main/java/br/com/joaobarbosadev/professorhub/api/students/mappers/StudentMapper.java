package br.com.joaobarbosadev.professorhub.api.students.mappers;

import br.com.joaobarbosadev.professorhub.api.students.dtos.StudentResponse;
import br.com.joaobarbosadev.professorhub.core.models.entities.Student;

public interface StudentMapper {

    StudentResponse toStudentResponse(Student student);
    Student toStudent(StudentResponse studentResponse);
}
