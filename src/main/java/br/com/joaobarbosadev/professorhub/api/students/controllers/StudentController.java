package br.com.joaobarbosadev.professorhub.api.students.controllers;

import br.com.joaobarbosadev.professorhub.api.students.dtos.StudentResponse;
import br.com.joaobarbosadev.professorhub.api.students.services.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static br.com.joaobarbosadev.professorhub.api.common.routes.APIRoutes.ROUTE_STUDENTS;

@RestController
@RequestMapping(ROUTE_STUDENTS)
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<StudentResponse> insertStudents(@RequestBody StudentResponse studentResponse) {
        studentResponse = studentService.save(studentResponse);
        return ResponseEntity.ok(studentResponse);
    }
}
