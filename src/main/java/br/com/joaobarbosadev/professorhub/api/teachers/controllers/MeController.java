package br.com.joaobarbosadev.professorhub.api.teachers.controllers;

import br.com.joaobarbosadev.professorhub.api.teachers.dtos.TeacherResponse;
import br.com.joaobarbosadev.professorhub.api.teachers.services.MeService;
import br.com.joaobarbosadev.professorhub.core.models.entities.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static br.com.joaobarbosadev.professorhub.api.common.routes.APIRoutes.PROFESSORS_LOGGED;

@RestController
@RequiredArgsConstructor
public class MeController {

    private final MeService meService;


    @GetMapping(PROFESSORS_LOGGED)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TeacherResponse> getTeacherLogged() {
        var logged = meService.getLoggedInTeacher();
        return ResponseEntity.ok().body(logged);
    }
}
