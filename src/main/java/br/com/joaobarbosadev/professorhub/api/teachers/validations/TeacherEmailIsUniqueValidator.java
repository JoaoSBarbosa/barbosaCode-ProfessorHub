package br.com.joaobarbosadev.professorhub.api.teachers.validations;

import br.com.joaobarbosadev.professorhub.core.repositories.TeacherRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TeacherEmailIsUniqueValidator implements ConstraintValidator<TeacherEmailIsUnique, String> {

    private final TeacherRepository teacherRepository;

    @Override
    public void initialize(TeacherEmailIsUnique constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(value == null || value.isEmpty()) {
            return true;
        }
        return !teacherRepository.existsByEmail(value);
    }
}
