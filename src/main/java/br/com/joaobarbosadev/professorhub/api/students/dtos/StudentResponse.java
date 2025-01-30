package br.com.joaobarbosadev.professorhub.api.students.dtos;
import br.com.joaobarbosadev.professorhub.core.models.entities.Teacher;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class StudentResponse {

    private Long studentId;
    private String name;
    private String email;
    private LocalDateTime classDate;
    private Teacher teacher;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public StudentResponse(){}

    public StudentResponse(Long studentId, Teacher teacher, LocalDateTime classDate, String email, String name) {
        this.studentId = studentId;
        this.teacher = teacher;
        this.classDate = classDate;
        this.email = email;
        this.name = name;
    }
}
