package br.com.joaobarbosadev.professorhub.core.exceptions.handlers;

import br.com.joaobarbosadev.professorhub.api.common.Utils.Util;
import br.com.joaobarbosadev.professorhub.core.exceptions.custom.CustomEntityNotFoundException;
import br.com.joaobarbosadev.professorhub.core.exceptions.responses.StandardError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(CustomEntityNotFoundException.class)
    public ResponseEntity<StandardError> handleEntityNotFound(CustomEntityNotFoundException exception, HttpServletRequest request) {
        StandardError standardError = new StandardError();
        HttpStatus status = HttpStatus.NOT_FOUND;
        standardError.setStatus(status.value());
        standardError.setMessage(exception.getMessage());
        standardError.setTimestamp(Util.getFormattedInstante());
        standardError.setPath(request.getRequestURI());
        standardError.setTitle("Entidade não encontrada");
        return ResponseEntity.status(status).body(standardError);
    }
}
