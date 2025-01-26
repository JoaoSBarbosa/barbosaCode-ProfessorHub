package br.com.treinaweb.hyperprof.api.teachers.dtos;

import br.com.treinaweb.hyperprof.core.models.Teacher;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
