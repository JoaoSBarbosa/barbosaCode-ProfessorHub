package br.com.joaobarbosadev.professorhub.core.exceptions.handlers;

import br.com.joaobarbosadev.professorhub.api.common.Utils.Util;
import br.com.joaobarbosadev.professorhub.api.common.dtos.ValidationErrorResponse;
import br.com.joaobarbosadev.professorhub.core.exceptions.custom.CustomEntityNotFoundException;
import br.com.joaobarbosadev.professorhub.core.exceptions.responses.StandardError;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    private final ObjectMapper objectMapper;

    public GlobalExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

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

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request
    ) {
        var status = (HttpStatus) statusCode;
        var errors = new HashMap<String, List<String>>();

        // Converter os nomes dos campos para snake_case
        ex.getBindingResult().getFieldErrors().forEach(error -> {

            var fieldName = error.getField();
            var snakeCaseFieldName = objectMapper.getPropertyNamingStrategy().nameForField(null,null,fieldName);
            var errorMessage = error.getDefaultMessage();

            errors.computeIfAbsent(snakeCaseFieldName, k -> new ArrayList<>()).add(errorMessage);

//            if(errors.containsKey(fieldName)) {
//                errors.get(fieldName).add(errorMessage);
//            }else {
//                errors.put(fieldName, new ArrayList<String>(Collections.singletonList(errorMessage)));
//            }
        });
        var body = ValidationErrorResponse
                .builder()
                .status(status.value())
                .error(status.getReasonPhrase())
                .message("Erro de validação")
                .cause(ex.getClass().getSimpleName())
                .timestamp(LocalDateTime.now())
                .errors(errors)
                .build();


        return new ResponseEntity<>(body, headers, status);
    }
}
