package br.com.joaobarbosadev.professorhub.api.dropbox.dtos;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DropboxInitial {

    @NotNull
    @NotBlank
    @Size(min = 10, max = 500)
    private String apiKey;
    @NotNull
    @NotBlank
    @Size(min = 10, max = 5000)
    private String apiSecret;
    @NotNull
    @NotBlank
    @Size(min = 10, max = 5000)
    private String accessToken;

}
