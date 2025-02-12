package br.com.joaobarbosadev.professorhub.api.common.exceptions.custom;

public class CustomInvalidInputException extends RuntimeException {
    public CustomInvalidInputException(String message) {
        super(message);
    }
}
