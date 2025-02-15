package br.com.joaobarbosadev.professorhub.api.auth.services;
import br.com.joaobarbosadev.professorhub.api.auth.dtos.LoginRequest;
import br.com.joaobarbosadev.professorhub.api.auth.dtos.LoginResponse;
import br.com.joaobarbosadev.professorhub.api.auth.dtos.RefreshRequest;
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
                .token(tokenService.generateAccessToken(teacher.getEmail()))
                .refreshToken(tokenService.generateRefreshToken(teacher.getEmail()))
                .build();
    }

    @Override
    public LoginResponse refreshToken(RefreshRequest refreshRequest) {
        var subject = tokenService.getSubjectFromRefreshToken(refreshRequest.getRefreshToken());

        if (!teacherRepository.existsByEmail(subject)) {
            throw new CustomEntityNotFoundException("Não existe registro de professor com o email: " + subject);
        }
        tokenService.inlidateAcessToken(refreshRequest.getRefreshToken());
        return LoginResponse.builder()
                .token(tokenService.generateAccessToken(subject))
                .refreshToken(refreshRequest.getRefreshToken())
                .build();
    }

    @Override
    public void logout(String token, RefreshRequest refreshRequest) {
        tokenService.inlidateAcessToken(token, refreshRequest.getRefreshToken());
    }
}
