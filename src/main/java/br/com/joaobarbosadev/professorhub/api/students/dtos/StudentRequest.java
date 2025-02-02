package br.com.joaobarbosadev.professorhub.api.students.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class StudentRequest {

    @NotNull(message = "O campo Nome é obrigatório.")
    @NotBlank(message = "O campo Nome não pode estar vazio ou conter apenas espaços em branco.")
    @Size(min = 3, max = 100, message = "O campo Nome deve ter entre 3 e 100 caracteres.")
    private String nome;

    @Email(message = "O campo E-mail deve conter um endereço de e-mail válido.")
    @NotNull(message = "O campo E-mail é obrigatório.")
    @NotBlank(message = "O campo E-mail não pode estar vazio ou conter apenas espaços em branco.")
    @Size(min = 3, max = 255, message = "O campo E-mail deve ter entre 3 e 255 caracteres.")
    private String email;

    @Future(message = "A Data da Aula deve ser uma data futura.")
    @NotNull(message = "O campo Data da Aula é obrigatório.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) // yyyy-MM-dd HH:mm:ss
    @JsonProperty("data_aula")
    private LocalDateTime dataAula;


}
