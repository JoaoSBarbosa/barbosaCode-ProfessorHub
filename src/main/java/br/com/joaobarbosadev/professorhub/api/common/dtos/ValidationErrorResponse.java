package br.com.joaobarbosadev.professorhub.api.common.dtos;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ValidationErrorResponse {

    private int status;
    private String message;
    private String error;
    private String cause;
    private String causeMessage;
    private LocalDateTime timestamp;
    private Map<String, List<String>> errors;
}
