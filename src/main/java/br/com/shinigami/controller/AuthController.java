package br.com.shinigami.controller;

import br.com.shinigami.dto.funcionario.FuncionarioCreateDTO;
import br.com.shinigami.dto.funcionario.FuncionarioDTO;
import br.com.shinigami.dto.funcionario.LoginDTO;
import br.com.shinigami.security.TokenService;
import br.com.shinigami.service.FuncionarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping("/auth")
public class AuthController {

    private final FuncionarioService funcionarioService;


    @PostMapping
    public ResponseEntity<String> autenticar(@RequestBody @Valid LoginDTO loginDTO) {
        log.info("Logando funcionario....");

        String token = funcionarioService.retornaTokenFuncionario(loginDTO);

        log.info("Token Gerado com sucesso.");

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/Cadastrar")
    public ResponseEntity<FuncionarioDTO> create(@RequestBody @Valid FuncionarioCreateDTO funcionario){
        log.info("Criando funcionario....");
        FuncionarioDTO funcionarioDTO = funcionarioService.create(funcionario);
        log.info("Criando funcionario....");
        return new ResponseEntity<>(funcionarioDTO,HttpStatus.OK);
    }
}
