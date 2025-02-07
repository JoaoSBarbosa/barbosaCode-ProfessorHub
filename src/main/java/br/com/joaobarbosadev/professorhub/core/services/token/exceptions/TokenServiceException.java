package br.com.joaobarbosadev.professorhub.core.services.token.exceptions;

public class TokenServiceException extends RuntimeException {
    public TokenServiceException(String message) {
        super(message);
    }
}
