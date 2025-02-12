package br.com.joaobarbosadev.professorhub.api.common.exceptions.handlers;

import br.com.joaobarbosadev.professorhub.api.common.Utils.Util;
import br.com.joaobarbosadev.professorhub.api.common.exceptions.responses.StandardError;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TokenAuthenticationEntryPointer implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {

        var status = HttpStatus.UNAUTHORIZED;
        var body = StandardError.builder()
                .status(status.value())
                .title("Não foi possivel autenticar o token")
                .message(authException.getLocalizedMessage())
                .timestamp(Util.getFormattedInstante())
                .details(authException.getClass().getSimpleName())
                .path(request.getRequestURI())
                .error(status.getReasonPhrase())
                .build();

        var json = objectMapper.writeValueAsString(body);
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
