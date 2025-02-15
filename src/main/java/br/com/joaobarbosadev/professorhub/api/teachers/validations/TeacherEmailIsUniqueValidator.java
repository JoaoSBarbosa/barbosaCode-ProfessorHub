package br.com.joaobarbosadev.professorhub.api.teachers.validations;

import br.com.joaobarbosadev.professorhub.core.repositories.TeacherRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Validador para verificar se o e-mail do professor é único no banco de dados.
 *
 * - Permite que um usuário autenticado edite seu próprio e-mail sem disparar erro de duplicidade.
 * - Retorna true se o campo for nulo ou vazio (validação de obrigatoriedade deve ser tratada separadamente).
 * - Retorna false se o e-mail já existir e não pertencer ao usuário autenticado.
 */
@Component
@RequiredArgsConstructor
public class TeacherEmailIsUniqueValidator implements ConstraintValidator<TeacherEmailIsUnique, String> {

    private final TeacherRepository teacherRepository;

    @Override
    public void initialize(TeacherEmailIsUnique constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * Valida se o e-mail informado é único no sistema.
     *
     * @param value E-mail do professor a ser validado.
     * @param constraintValidatorContext Contexto da validação.
     * @return true se o e-mail puder ser utilizado, false caso contrário.
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(value == null || value.isEmpty()) {
            // Se o e-mail estiver vazio, não aplicamos essa validação.
            return true;
        }
        var authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        // Verifica se há um usuário autenticado e se ele não é um usuário anônimo.
        var isAuthenticated = authenticatedUser != null && !(authenticatedUser instanceof AnonymousAuthenticationToken) && authenticatedUser.isAuthenticated();

        if(isAuthenticated) {
            var teacher = teacherRepository.findByTeacherByEmail(value);
            // Se não encontrar um professor com esse e-mail, então pode ser usado.
            // Se o e-mail pertencer ao usuário autenticado, também permitimos (evita erro ao editar o próprio perfil).
            return teacher.isEmpty() || teacher.get().getEmail().equals(authenticatedUser.getName());
        }
        // Se não houver usuário autenticado, apenas verificamos se o e-mail já existe no banco.
        return !teacherRepository.existsByEmail(value);
    }
}
