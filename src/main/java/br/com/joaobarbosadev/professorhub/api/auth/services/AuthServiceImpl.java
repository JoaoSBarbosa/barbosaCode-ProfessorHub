package br.com.joaobarbosadev.professorhub.api.auth.services;

import br.com.joaobarbosadev.professorhub.api.auth.dtos.LoginRequest;
import br.com.joaobarbosadev.professorhub.api.auth.dtos.LoginResponse;
import br.com.joaobarbosadev.professorhub.api.auth.dtos.RefreshToken;
import br.com.joaobarbosadev.professorhub.api.common.exceptions.custom.CustomEntityNotFoundException;
import br.com.joaobarbosadev.professorhub.core.repositories.TeacherRepository;
import br.com.joaobarbosadev.professorhub.core.services.authentication.model.AuthenticatedUser;
import br.com.joaobarbosadev.professorhub.core.services.token.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final TokenService tokenService;
    private final TeacherRepository teacherRepository;
    private final AuthenticationManager authenticationManager;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        var userNamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        var authentication = authenticationManager.authenticate(userNamePasswordAuthenticationToken);

        var teacher = ((AuthenticatedUser) authentication.getPrincipal()).getTeacher();

        return LoginResponse
                .builder()
                .token(tokenService.generateAcessToken(teacher.getEmail()))
                .refreshToken(tokenService.generateRefreshToken(teacher.getEmail()))
                .build();
    }

    @Override
    public LoginResponse refreshToken(RefreshToken refreshToken) {
        var subject = tokenService.getSubjectFromRefreshToken(refreshToken.getRefreshToken());

        if (!teacherRepository.existsByEmail(subject)) {
            throw new CustomEntityNotFoundException("Não existe registro de professor com o email: " + subject);
        }

        return LoginResponse.builder()
                .token(tokenService.generateAcessToken(subject))
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }
}
