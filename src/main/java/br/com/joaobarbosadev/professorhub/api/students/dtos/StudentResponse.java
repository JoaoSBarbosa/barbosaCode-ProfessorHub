package br.com.joaobarbosadev.professorhub.api.students.dtos;
import br.com.joaobarbosadev.professorhub.api.teachers.dtos.TeacherResponse;
import br.com.joaobarbosadev.professorhub.core.models.entities.Teacher;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class StudentResponse {
    @JsonProperty(namespace = "id_estudante")
    private Long studentId;
    @JsonProperty(namespace = "nome_estudante")
    private String name;
    private String email;
    @JsonProperty(namespace = "data_aula")
    private LocalDateTime classDate;
    @JsonProperty(namespace = "professor")
    private Teacher teacher;
    @JsonProperty(namespace = "criado_em")
    private LocalDateTime createdAt;
    @JsonProperty(namespace = "atualizado_em")
    private LocalDateTime updatedAt;

}
