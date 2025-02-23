package br.com.joaobarbosadev.professorhub.api.common.exceptions.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class StandardError {

    private String title;
    private String message;
    private Integer status;
    private String timestamp;
    private String path;
    private String details;
    private String error;



}
