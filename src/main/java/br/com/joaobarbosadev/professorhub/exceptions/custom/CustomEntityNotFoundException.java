package br.com.joaobarbosadev.professorhub.exceptions.custom;

public class CustomEntityNotFoundException extends RuntimeException {
    public CustomEntityNotFoundException(String message) {
        super(message);
    }
}
