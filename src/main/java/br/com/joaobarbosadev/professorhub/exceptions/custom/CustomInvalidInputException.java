package br.com.joaobarbosadev.professorhub.exceptions.custom;

public class CustomInvalidInputException extends RuntimeException {
    public CustomInvalidInputException(String message) {
        super(message);
    }
}
