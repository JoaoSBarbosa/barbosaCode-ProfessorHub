package br.com.joaobarbosadev.professorhub.api.teachers.controllers;

import br.com.joaobarbosadev.professorhub.api.common.routes.APIRoutes;
import br.com.joaobarbosadev.professorhub.api.teachers.dtos.TeacherRequest;
import br.com.joaobarbosadev.professorhub.api.teachers.dtos.TeacherResponse;
import br.com.joaobarbosadev.professorhub.api.teachers.services.TeacherService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(APIRoutes.ROUTE_TEACHERS)
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }


    @PostMapping
    public ResponseEntity<TeacherResponse> saveTeacher(@RequestBody @Valid TeacherRequest teacherRequest) {
        var teacherResponse = teacherService.saveTeacher(teacherRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(teacherResponse.getId()).toUri();
        return ResponseEntity.created(uri).body(teacherResponse);
    }
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }

    @GetMapping(APIRoutes.DESCRIPTION)
    public ResponseEntity<List<TeacherResponse>> findByDescription(@RequestParam(name = "desc", required = false, defaultValue = "") String description) {
        return ResponseEntity.ok(teacherService.findByDescription(description));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(teacherService.findById(id));
    }

    @PutMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TeacherResponse> updateTeacher(@RequestBody @Valid TeacherRequest teacherRequest) {
        var updateTeacher = teacherService.updateTeacher(teacherRequest);
        return ResponseEntity.ok(updateTeacher);
    }
}
