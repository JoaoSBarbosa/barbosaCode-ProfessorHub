package br.com.joaobarbosadev.professorhub.api.auth.services;

import br.com.joaobarbosadev.professorhub.api.auth.dtos.LoginRequest;
import br.com.joaobarbosadev.professorhub.api.auth.dtos.LoginResponse;
import br.com.joaobarbosadev.professorhub.api.auth.dtos.RefreshToken;

public interface AuthService {

    LoginResponse login(LoginRequest loginRequest);
    LoginResponse refreshToken(RefreshToken refreshToken);
}
