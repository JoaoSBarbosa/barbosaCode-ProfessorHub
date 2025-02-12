package br.com.joaobarbosadev.professorhub.api.teachers.services;
import br.com.joaobarbosadev.professorhub.api.teachers.dtos.TeacherResponse;
import br.com.joaobarbosadev.professorhub.api.teachers.mappers.TeacherMapper;
import br.com.joaobarbosadev.professorhub.core.services.authentication.model.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeServiceImpl implements MeService {
    private final TeacherMapper teacherMapper;

    @Override
    public TeacherResponse getLoggedInTeacher() {
        // peagr usuario logado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var teacher = ((AuthenticatedUser) authentication.getPrincipal()).getTeacher();

        return teacherMapper.toTeacherResponse(teacher);
    }
}
