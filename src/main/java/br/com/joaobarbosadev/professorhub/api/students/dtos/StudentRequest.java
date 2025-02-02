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

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 100)
    private String nome;

    @Email
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 255)
    private String email;

    @Future
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) // yyyy-MM-dd HH:mm:ss
    @JsonProperty(namespace = "data_aula")
    private LocalDateTime dataAula;


}
