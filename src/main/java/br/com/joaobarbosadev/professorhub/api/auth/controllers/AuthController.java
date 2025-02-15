package br.com.joaobarbosadev.professorhub.api.auth.controllers;
import br.com.joaobarbosadev.professorhub.api.auth.dtos.LoginRequest;
import br.com.joaobarbosadev.professorhub.api.auth.dtos.LoginResponse;
import br.com.joaobarbosadev.professorhub.api.auth.dtos.RefreshRequest;
import br.com.joaobarbosadev.professorhub.api.auth.services.AuthService;
import br.com.joaobarbosadev.professorhub.api.common.Utils.JwtBearerDefaults;
import br.com.joaobarbosadev.professorhub.api.common.routes.APIRoutes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(APIRoutes.AUTH)
public class AuthController {

    private final AuthService authService;

    @PostMapping(APIRoutes.LOGIN)
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping(APIRoutes.REFRESH)
    public LoginResponse refreshToken(@RequestBody @Valid RefreshRequest refreshRequest) {
        return authService.refreshToken(refreshRequest);
    }

    @PostMapping(APIRoutes.LOGOUT)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> logout(@RequestHeader String authorization, @RequestBody @Valid RefreshRequest refreshRequest) {
        var token = authorization.substring(JwtBearerDefaults.TOKEN_TYPE.length());
        authService.logout(token, refreshRequest);
        return ResponseEntity.status(HttpStatus.RESET_CONTENT).build();
    }
}
