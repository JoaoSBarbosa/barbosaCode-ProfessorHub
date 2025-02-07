package br.com.joaobarbosadev.professorhub.core.services.authentication.services;

import br.com.joaobarbosadev.professorhub.core.repositories.TeacherRepository;
import br.com.joaobarbosadev.professorhub.core.services.authentication.model.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final TeacherRepository teacherRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return teacherRepository.findByTeacherByEmail(username).map(AuthenticatedUser::new).orElseThrow(() -> new UsernameNotFoundException("Não foi localizado usuário com o email informado: "+username));
    }
}
