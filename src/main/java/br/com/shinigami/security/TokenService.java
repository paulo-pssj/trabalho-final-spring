package br.com.shinigami.security;

import br.com.shinigami.entity.CargoEntity;
import br.com.shinigami.entity.FuncionarioEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class TokenService {

    private static final String CARGO = "CARGO";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private String expiration;

    public String getToken(FuncionarioEntity funcionarioEntity) {

        LocalDate now = LocalDate.now();
        Date hoje = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date expira = Date.from(now.plusDays(Long.parseLong(expiration)).atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<String> cargosDoFuncionario = funcionarioEntity.getCargos()
                .stream()
                .map(CargoEntity::getAuthority)
                .toList();

        String token = Jwts.builder()
                .setIssuer("pessoa-api")
                .claim(Claims.ID, String.valueOf(funcionarioEntity.getIdFuncionario()))
                .claim(CARGO, cargosDoFuncionario)
                .setIssuedAt(hoje)
                .setExpiration(expira).signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        return token;
    }

    public UsernamePasswordAuthenticationToken isValid(String token) {
        if (token == null) {
            return null;
        }

        token = token.replace("Bearer ", "");

        Claims chaves = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        String idFuncionario = chaves.get(Claims.ID, String.class);

        List<String> cargoFuncionario = chaves.get(CARGO, List.class);

        List<SimpleGrantedAuthority> listaDeCargos = cargoFuncionario.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        UsernamePasswordAuthenticationToken userPassAuthToken =
                new UsernamePasswordAuthenticationToken(idFuncionario, null, listaDeCargos);

        return userPassAuthToken;
    }
}
