package br.com.joaobarbosadev.professorhub.api.teachers.dtos;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TeacherResponse {

    private Long id;
    private String name;
    private String email;
    private int age;
    private String description;
    private BigDecimal hourlyRate;
    private String urlPhoto;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
