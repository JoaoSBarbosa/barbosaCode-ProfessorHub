package br.com.joaobarbosadev.professorhub.api.common.filters;

import br.com.joaobarbosadev.professorhub.api.common.Utils.Util;
import br.com.joaobarbosadev.professorhub.api.common.exceptions.responses.StandardError;
import br.com.joaobarbosadev.professorhub.core.services.token.TokenService;
import br.com.joaobarbosadev.professorhub.core.services.token.exceptions.TokenServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static br.com.joaobarbosadev.professorhub.api.common.Utils.JwtBearerDefaults.AUTHORIZATION_HEADER;
import static br.com.joaobarbosadev.professorhub.api.common.Utils.JwtBearerDefaults.TOKEN_TYPE;

@Component
@RequiredArgsConstructor
public class AccessTokenRequestFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException
    {
       try {
           tryDolFilterInternal(request, response, filterChain);
       }catch (TokenServiceException e){
           var status = HttpStatus.UNAUTHORIZED;
           var body = StandardError.builder()
                   .status(status.value())
                   .timestamp(Util.getFormattedInstante())
                   .error(status.getReasonPhrase())
                   .details(e.getClass().getSimpleName())
                   .message(e.getLocalizedMessage())
                   .build();
           var json = objectMapper.writeValueAsString(body);
           response.setStatus(status.value());
           response.setContentType("application/json");
           response.setCharacterEncoding("UTF-8");
           response.getWriter().write(json);


       }
    }

    private void tryDolFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        var token = "";
        var email = "";
        var authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (isIsPresentToken(authorizationHeader)) {
            token = authorizationHeader.substring(TOKEN_TYPE.length());
            email = tokenService.getSubjectFromAccessToken(token);
        }
        // verifica se o email já não esta no context do spring security
        if (isIsEmailNotInContent(email)) {
            setAuthentication(request, email);
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(HttpServletRequest request, String email) {
        var userDetails = userDetailsService.loadUserByUsername(email);
        var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        // pega as informações da propria requisção e acerscenta detalhes - ex IP
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // adicionando no contexto
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private static boolean isIsEmailNotInContent(String email) {
        return email != null &&
                !email.isEmpty() &&
                SecurityContextHolder.getContext().getAuthentication() == null;
    }

    // verifica se token esta presente
    private static boolean isIsPresentToken(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.startsWith(TOKEN_TYPE);
    }


}
