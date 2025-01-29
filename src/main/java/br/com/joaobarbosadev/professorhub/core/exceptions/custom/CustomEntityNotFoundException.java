package br.com.joaobarbosadev.professorhub.core.exceptions.custom;

public class CustomEntityNotFoundException extends RuntimeException {
    public CustomEntityNotFoundException(String message) {
        super(message);
    }
}
