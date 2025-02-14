package br.com.joaobarbosadev.professorhub.core.services.token.providers.jjwt;

import br.com.joaobarbosadev.professorhub.core.models.entities.InvalidToken;
import br.com.joaobarbosadev.professorhub.core.repositories.InvalidTokenRepository;
import br.com.joaobarbosadev.professorhub.core.services.token.TokenService;
import br.com.joaobarbosadev.professorhub.core.services.token.exceptions.TokenServiceException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class JjwtTokenService implements TokenService {

    private final JjwtConfigProperties configProperties;
    private final InvalidTokenRepository invalidTokenRepository;

    @Override
    public String generateAcessToken(String subject) {
        return generateToken(
                subject,
                configProperties.getAccessTokenExpirationInSeconds(),
                configProperties.getAccessTokenSigningKey()
        );
    }

    @Override
    public String getSubjectFromAcessToken(String token) {
        return getClaims(token, configProperties.getAccessTokenSigningKey()).getSubject();
    }

    @Override
    public String generateRefreshToken(String subject) {
        return generateToken(subject, configProperties.getRefreshTokenExpirationInSeconds(), configProperties.getRefreshTokenSigningKey());
    }

    @Override
    public String getSubjectFromRefreshToken(String refreshToken) {
        return getClaims(refreshToken, configProperties.getRefreshTokenSigningKey()).getSubject();
    }

    @Override
    public void inlidateAcessToken(String... tokens) {
        var invalidTokens = Stream.of(tokens)
                .map(token -> InvalidToken.builder().token(token).build())
                .toList();

        invalidTokenRepository.saveAll(invalidTokens);

    }


    private Claims getClaims(String token, String signingKey) {
        try {
            return tryGetClaims(token, signingKey);
        } catch (JwtException e) {
            throw new TokenServiceException(e.getLocalizedMessage());
        }
    }

    private Claims tryGetClaims(String token, String signingKey) {
        if(invalidTokenRepository.existsByToken(token)) {
            throw new TokenServiceException("Token '" + token + "' está invalido");
        }
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(signingKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private String generateToken(String subject, Long expirationInSeconds, String signingKey) {
        var today = Instant.now();
        var expirationDate = today.plusSeconds(expirationInSeconds);
        return Jwts.builder()
                .setClaims(new HashMap<String, Object>())
                .setSubject(subject)
                .setIssuedAt(Date.from(today))
                .setExpiration(Date.from(expirationDate))
                .signWith(Keys.hmacShaKeyFor(signingKey.getBytes()))
                .compact();

    }
}
