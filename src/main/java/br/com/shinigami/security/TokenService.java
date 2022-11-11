package br.com.shinigami.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private String expiration;

    public String getToken(FuncionarioEntity funcionarioEntity){
        LocalDate now = LocalDate.now();
        Date hoje = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date expira = Date.from(now.plusDays(Long.parseLong(expiration)).atStartOfDay(ZoneId.systemDefault()).toInstant());
        String token = Jwts.builder()
                .setIssuer("pessoa-api")
                .claim(Claims.ID, funcionarioEntity.getIdFuncionario().toString())
                .setIssuedAt(hoje)
                .setExpiration(expira).signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        return token;
    }

    public UsernamePasswordAuthenticationToken isValid(String token){
        if(token == null){
            return null;
        }

        token = token.replace("Bearer ", "");

        Claims chaves = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        String idFuncionario = chaves.get(Claims.ID, String.class);
        UsernamePasswordAuthenticationToken userPassAuthToken =
                new UsernamePasswordAuthenticationToken(idFuncionario, null, Collections.emptyList());

        return userPassAuthToken;
    }
}
