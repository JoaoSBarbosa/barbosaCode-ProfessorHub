package br.com.joaobarbosadev.professorhub.api.common.exceptions.custom;

public class CustomEntityNotFoundException extends RuntimeException {
    public CustomEntityNotFoundException(String message) {
        super(message);
    }
}
