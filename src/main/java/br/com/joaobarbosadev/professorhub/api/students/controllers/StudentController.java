package br.com.joaobarbosadev.professorhub.api.students.controllers;

import br.com.joaobarbosadev.professorhub.api.students.dtos.StudentRequest;
import br.com.joaobarbosadev.professorhub.api.students.dtos.StudentResponse;
import br.com.joaobarbosadev.professorhub.api.students.services.StudentServiceImpl;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static br.com.joaobarbosadev.professorhub.api.common.routes.APIRoutes.ROUTE_POST_STUDENTS_TEACHERS;
import static br.com.joaobarbosadev.professorhub.api.common.routes.APIRoutes.ROUTE_STUDENTS;

@RestController
@RequestMapping(ROUTE_STUDENTS)
public class StudentController {

    private final StudentServiceImpl studentService;

    public StudentController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    @PostMapping(ROUTE_POST_STUDENTS_TEACHERS)
    public ResponseEntity<StudentResponse> saveStudent(
            @RequestBody @Valid StudentRequest studentRequest,
            @PathVariable Long professorId) {
        var response = studentService.saveStudent(studentRequest, professorId);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getStudentId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<StudentResponse>> findAllStudents(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Long teacherId,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "studentId") String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder
    ) {
        Page<StudentResponse> responses = studentService.findAllPage(name,email,teacherId,size,page,sortField,sortOrder);
        return ResponseEntity.ok().body(responses);

    }
}
