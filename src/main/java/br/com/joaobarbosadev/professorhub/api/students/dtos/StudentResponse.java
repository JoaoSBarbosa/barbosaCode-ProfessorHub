package br.com.joaobarbosadev.professorhub.api.students.dtos;
import br.com.joaobarbosadev.professorhub.api.teachers.dtos.TeacherResponse;
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
    private Long studentId;
    private String name;
    private String email;
    private LocalDateTime classDate;
    private TeacherResponse teacher;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
