package br.com.joaobarbosadev.professorhub.api.teachers.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)  // Aplica-se a campos
@Retention(RetentionPolicy.RUNTIME)  // Mantida em tempo de execução
@Constraint(validatedBy = TeacherEmailIsUniqueValidator.class)  // Define a classe de validação
public @interface TeacherEmailIsUnique {

    String message() default "E-mail já está em uso";  // Mensagem de erro padrão

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
