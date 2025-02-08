package br.com.joaobarbosadev.professorhub.api.common.handlers;

import br.com.joaobarbosadev.professorhub.api.common.Utils.Util;
import br.com.joaobarbosadev.professorhub.core.exceptions.responses.StandardError;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.ErrorResponse;

import java.io.IOException;

public class TokenAuthenticationEntryPointer implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {

        var status = HttpServletResponse.SC_UNAUTHORIZED;
        var body = StandardError.builder()
                .status(status)
                .title("Não foi possivel autenticar o token")
                .message(authException.getLocalizedMessage())
                .timestamp(Util.getFormattedInstante())
                .details(authException.getClass().getSimpleName())
                .build();
    }
}
