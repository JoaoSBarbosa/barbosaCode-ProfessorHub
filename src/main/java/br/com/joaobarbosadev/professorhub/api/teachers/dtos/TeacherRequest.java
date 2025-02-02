package br.com.joaobarbosadev.professorhub.api.teachers.dtos;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TeacherRequest {

    @NotNull(message = "O campo Nome é obrigatório.")
    @NotBlank(message = "O campo Nome não pode estar vazio ou conter apenas espaços em branco.")
    @Size(min = 3, max = 100, message = "O campo Nome deve ter entre 3 e 100 caracteres.")
    private String nome;

    @NotNull(message = "O campo E-mail é obrigatório.")
    @NotBlank(message = "O campo E-mail não pode estar vazio ou conter apenas espaços em branco.")
    @Size(min = 3, max = 255, message = "O campo E-mail deve ter entre 3 e 255 caracteres.")
    @Email(message = "O campo E-mail deve conter um endereço de e-mail válido.")
    private String email;

    @NotNull(message = "O campo Idade é obrigatório.")
    @Min(value = 18, message = "A Idade deve ser no mínimo 18 anos.")
    @Max(value = 100, message = "A Idade deve ser no máximo 100 anos.")
    private int idade;

    @NotNull(message = "O campo Descrição é obrigatório.")
    @NotBlank(message = "O campo Descrição não pode estar vazio ou conter apenas espaços em branco.")
    @Size(min = 3, max = 500, message = "O campo Descrição deve ter entre 3 e 500 caracteres.")
    private String descricao;

    @NotNull(message = "O campo Senha é obrigatório.")
    @NotBlank(message = "O campo Senha não pode estar vazio ou conter apenas espaços em branco.")
    @Size(min = 6, max = 255, message = "O campo Senha deve ter entre 6 e 255 caracteres.")
    private String senha;

    @NotNull(message = "O campo Confirmar Senha é obrigatório.")
    @NotBlank(message = "O campo Confirmar Senha não pode estar vazio ou conter apenas espaços em branco.")
    @Size(min = 6, max = 255, message = "O campo Confirmar Senha deve ter entre 6 e 255 caracteres.")
    private String confirmarSenha;

    @NotNull(message = "O campo Valor por Hora é obrigatório.")
    @DecimalMin(value = "10.0", inclusive = false, message = "O Valor por Hora deve ser igual ou maior que R$ 10,00")
    @DecimalMax(value = "650.0", message = "O Valor por Hora deve ser no máximo R$ 650,00")
    private BigDecimal valorHora;


}
