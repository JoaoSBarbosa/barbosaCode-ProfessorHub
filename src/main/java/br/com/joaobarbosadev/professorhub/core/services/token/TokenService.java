package br.com.joaobarbosadev.professorhub.core.services.token;

public interface TokenService {

    // Gera o token
    String generateAcessToken(String subject);
    String getSubjectFromAcessToken(String token); // pega o subject de um token
    String generateRefreshToken(String subject); // gera o refress token
    String getSubjectFromRefreshToken(String refreshToken); // pega o subject do refress token

    void inlidateAcessToken(String... tokens); // String... significa que pode receber um ou varios argumentos do tipo string
}
