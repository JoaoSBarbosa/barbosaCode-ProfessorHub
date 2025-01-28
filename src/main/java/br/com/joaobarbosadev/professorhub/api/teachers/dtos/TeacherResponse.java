package br.com.joaobarbosadev.professorhub.api.teachers.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class TeacherResponse {

    private Long id;
    private String name;
    private String email;
    private int age;
    private String description;
    @JsonProperty("hourly_rate")
    private BigDecimal hourlyRate;
    @JsonProperty("url_photo")
    private String urlPhoto;
    private String password;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;


}
