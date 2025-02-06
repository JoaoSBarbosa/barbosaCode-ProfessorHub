package br.com.joaobarbosadev.professorhub.core.validations;

import com.fasterxml.jackson.databind.util.BeanUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class FieldsAreEqualsValidator implements ConstraintValidator<FieldsAreEquals, Object> {

    private String field;
    private String fieldMatch;


    @Override
    public void initialize(FieldsAreEquals constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
        validatedParameters();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(constraintValidatorContext.getDefaultConstraintMessageTemplate()).addPropertyNode(fieldMatch).addConstraintViolation();
        var fieldPropertiesDescription = BeanUtils.getPropertyDescriptor(value.getClass(), field);
        var fieldMatchPropertiesDescription = BeanUtils.getPropertyDescriptor(value.getClass(), fieldMatch);
        if (fieldPropertiesDescription == null || fieldMatchPropertiesDescription == null) throw new IllegalArgumentException("Os campos informados não foram localizados na classe " + value.getClass());

        try {
            var fieldValue = fieldPropertiesDescription.getReadMethod().invoke(value);
            var fieldMatchValue = fieldMatchPropertiesDescription.getReadMethod().invoke(value);
            return Objects.equals(fieldValue, fieldMatchValue);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException("Erro ao acessar os campos" + e);
        }

    }


    private void validatedParameters() {
        if (field == null || field.isEmpty()) throw new IllegalArgumentException("O campo 'field' não pode ser nulo ou vazio");
        if (fieldMatch == null || fieldMatch.isEmpty()) throw new IllegalArgumentException("O campo 'fieldMatch' não pode ser nulo ou vazio");
    }
}
