package br.com.joaobarbosadev.professorhub.core.services.token.providers.jjwt;

import br.com.joaobarbosadev.professorhub.core.services.token.TokenService;

public class JjwtTokenService implements TokenService {
    @Override
    public String generateAcessToken(String subject) {
        return "";
    }

    @Override
    public String getSubjectFromAcessToken(String token) {
        return "";
    }

    @Override
    public String generateRefreshToken(String subject) {
        return "";
    }

    @Override
    public String getSubjectFromRefreshToken(String refreshToken) {
        return "";
    }

    @Override
    public void validateAcessToken(String... token) {

    }
}
