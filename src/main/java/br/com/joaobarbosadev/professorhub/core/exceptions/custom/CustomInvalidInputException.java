package br.com.joaobarbosadev.professorhub.core.exceptions.custom;

public class CustomInvalidInputException extends RuntimeException {
    public CustomInvalidInputException(String message) {
        super(message);
    }
}
