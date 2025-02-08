package br.com.joaobarbosadev.professorhub.api.auth.controllers;

import br.com.joaobarbosadev.professorhub.api.auth.dtos.LoginRequest;
import br.com.joaobarbosadev.professorhub.api.auth.dtos.LoginResponse;
import br.com.joaobarbosadev.professorhub.api.auth.services.AuthService;
import br.com.joaobarbosadev.professorhub.api.common.routes.APIRoutes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(APIRoutes.LOGIN)
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
}
