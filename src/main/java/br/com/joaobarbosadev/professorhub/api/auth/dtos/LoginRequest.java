package br.com.joaobarbosadev.professorhub.api.auth.dtos;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LoginRequest {

    @Email(message = "O campo E-mail deve conter um endereço de e-mail válido.")
    @NotNull(message = "O campo E-mail é obrigatório para realizar o login.")
    @Size(min = 3,max = 255)
    @NotBlank(message = "O campo E-mail não pode estar vazio ou conter apenas espaços em branco.")
    private String email;

    @NotNull(message = "O campo Senha é obrigatório.")
    @NotBlank(message = "O campo Senha não pode estar vazio ou conter apenas espaços em branco.")
    @Size(min = 6, max = 255, message = "O campo Senha deve ter entre 6 e 255 caracteres.")
    private String password;
}
