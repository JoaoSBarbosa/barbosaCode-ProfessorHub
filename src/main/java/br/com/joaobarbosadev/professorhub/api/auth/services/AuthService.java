package br.com.joaobarbosadev.professorhub.api.auth.services;

import br.com.joaobarbosadev.professorhub.api.auth.dtos.LoginRequest;
import br.com.joaobarbosadev.professorhub.api.auth.dtos.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest loginRequest);
}
