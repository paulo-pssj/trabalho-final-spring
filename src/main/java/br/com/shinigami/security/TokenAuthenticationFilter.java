package br.com.shinigami.security;

import br.com.shinigami.entity.FuncionarioEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class TokenAuthenticationFilter  extends OncePerRequestFilter {

    private final TokenService tokenService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String headerAuthorization = request.getHeader("Authorization");
        UsernamePasswordAuthenticationToken isValid = tokenService.isValid(headerAuthorization);

        if (isValid.isAuthenticated()) {

            FuncionarioEntity funcionarioEntity = isValid.getCredentials();
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(funcionarioEntity.getLogin(), funcionarioEntity.getSenha());
            SecurityContextHolder.getContext().setAuthentication(token);
        }else {

            SecurityContextHolder.getContext().setAuthentication(null);

        }
        filterChain.doFilter(request,response);
    }
}
