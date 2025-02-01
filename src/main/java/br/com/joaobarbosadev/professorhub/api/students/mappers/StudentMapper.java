package br.com.joaobarbosadev.professorhub.api.students.mappers;

import br.com.joaobarbosadev.professorhub.api.students.dtos.StudentRequest;
import br.com.joaobarbosadev.professorhub.api.students.dtos.StudentResponse;
import br.com.joaobarbosadev.professorhub.core.models.entities.Student;

public interface StudentMapper {

    StudentResponse toStudentResponse(Student student);

    Student toStudentByRequest(StudentRequest studentRequest);

    Student toStudent(StudentResponse studentResponse);
}
