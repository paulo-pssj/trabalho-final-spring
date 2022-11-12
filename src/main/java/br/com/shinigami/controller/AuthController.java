package br.com.shinigami.controller;

import br.com.shinigami.dto.funcionario.AlterarSenhaDTO;
import br.com.shinigami.dto.funcionario.FuncionarioCreateDTO;
import br.com.shinigami.dto.funcionario.FuncionarioDTO;
import br.com.shinigami.dto.funcionario.LoginDTO;
import br.com.shinigami.exceptions.RegraDeNegocioException;
import br.com.shinigami.service.FuncionarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping("/auth")
public class AuthController {

    private final FuncionarioService funcionarioService;


    @GetMapping("funcionario-logado")
    public ResponseEntity<FuncionarioDTO> retornaFuncionarioLogado() throws RegraDeNegocioException {
        log.info("buscando usuario logado...");
        FuncionarioDTO funcionario = funcionarioService.getLoggedUser();
        log.info("Busca de funcionario realizada com sucesso.");
        return new ResponseEntity<>(funcionario, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> autenticar(@RequestBody @Valid LoginDTO loginDTO) {
        log.info("Logando funcionario....");

        String token = funcionarioService.retornaTokenFuncionario(loginDTO);

        log.info("Token Gerado com sucesso.");

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/cadastrar-funcionario")
    public ResponseEntity<FuncionarioDTO> create(@RequestBody @Valid FuncionarioCreateDTO funcionario) {
        log.info("Criando funcionario....");
        FuncionarioDTO funcionarioDTO = funcionarioService.create(funcionario);
        log.info("Criando funcionario....");
        return new ResponseEntity<>(funcionarioDTO, HttpStatus.OK);
    }

    @PostMapping("/recuperar-senha")
    public ResponseEntity<String> recuperarSenha(String email) throws RegraDeNegocioException {
        return new ResponseEntity<>(funcionarioService.tokenTrocaDeSenha(email), HttpStatus.OK);
    }

    @PutMapping("/desativar-funcionario")
    public ResponseEntity<Void> desativarFuncionario(String email) throws RegraDeNegocioException {
        log.info("desativando funcionario...");
        funcionarioService.desativarFuncionario(email);
        log.info("Funcionario desativado com sucesso.");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/alterar-senha")
    public ResponseEntity<String> alterarSenha(AlterarSenhaDTO senha) throws RegraDeNegocioException {
        log.info("Alterando senha de funcionario...");
        String info = funcionarioService.alterarSenha(senha);
        log.info("Senha alterada com sucesso.");
        return new ResponseEntity<>(info, HttpStatus.OK);
    }
}
