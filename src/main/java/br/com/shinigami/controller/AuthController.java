package br.com.shinigami.controller;

import br.com.shinigami.dto.LoginDTO;
import br.com.shinigami.entity.FuncionarioEntity;
import br.com.shinigami.security.TokenService;
import br.com.shinigami.service.FuncionarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/auth")
public class AuthController {

    private final FuncionarioService funcionarioService;

    private final TokenService tokenService;

    @PostMapping
    public String autenticar(@RequestBody @Valid LoginDTO loginDTO) {
        Optional<FuncionarioEntity> byLoginAndSenha = funcionarioService.findByLogin(loginDTO.getLogin());

        if (byLoginAndSenha.isPresent()) {
            return tokenService.getToken(byLoginAndSenha.get());
        }
//        throw new RegraDeNegocioException("Usuario ou senha invalidos")

        return null;
    }
}
