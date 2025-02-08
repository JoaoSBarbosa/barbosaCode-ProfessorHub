package br.com.joaobarbosadev.professorhub.core.services.token.providers.jjwt;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "com.joaobarbosadev.professorhub.core.services.token.jjwt")
public class JjwtConfigProperties {

    private String accessTokenSigningKey;
    private Long accessTokenExpirationInSeconds;
    private String refreshTokenSigningKey;
    private Long refreshTokenExpirationInSeconds;
}
