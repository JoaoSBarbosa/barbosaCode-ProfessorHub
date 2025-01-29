package br.com.joaobarbosadev.professorhub.api.teachers.controllers;

import br.com.joaobarbosadev.professorhub.api.common.routes.APIRoutes;
import br.com.joaobarbosadev.professorhub.api.teachers.dtos.TeacherResponse;
import br.com.joaobarbosadev.professorhub.api.teachers.services.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(APIRoutes.ROUTE_TEACHERS)
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
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

}
