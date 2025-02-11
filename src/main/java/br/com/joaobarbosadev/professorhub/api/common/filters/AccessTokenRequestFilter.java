package br.com.joaobarbosadev.professorhub.api.common.filters;

import br.com.joaobarbosadev.professorhub.core.services.token.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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

    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException
    {
        var token = "";
        var email = "";
        var authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

        if (isIsPresentToken(authorizationHeader)) {
            token = authorizationHeader.substring(TOKEN_TYPE.length());
            email = tokenService.getSubjectFromAcessToken(token);
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

    private static boolean isIsPresentToken(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.startsWith(TOKEN_TYPE);
    }


}
