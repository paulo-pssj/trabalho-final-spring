package br.com.shinigami.controller;


import br.com.shinigami.controller.controllerInterface.ContratoControllerInterface;
import br.com.shinigami.dto.RelatorioContratoClienteDTO;
import br.com.shinigami.dto.contrato.ContratoCreateDTO;
import br.com.shinigami.dto.contrato.ContratoDTO;
import br.com.shinigami.dto.page.PageDTO;
import br.com.shinigami.exceptions.RegraDeNegocioException;
import br.com.shinigami.service.ContratoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/contrato")
public class ContratoController implements ContratoControllerInterface {

    private final ContratoService contratoService;

    @GetMapping("/contrato-paginado")
    public ResponseEntity<PageDTO<ContratoDTO>> list(@RequestParam("page") Integer page) throws RegraDeNegocioException {
        log.info("Listando contratos...");
        PageDTO<ContratoDTO> lista = contratoService.list(page);
        log.info("Lista de contratos encontrada!");
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{idContrato}")
    public ResponseEntity<ContratoDTO> buscarContrato(@PathVariable("idContrato") Integer idContrato) throws RegraDeNegocioException {
        log.info("Buscando contrato...");
        ContratoDTO contrato = contratoService.findByIdContrato(idContrato);
        log.info("Contrato Encontrado!");
        return new ResponseEntity<>(contrato, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ContratoDTO> create(@Valid @RequestBody ContratoCreateDTO contratoCreate) throws RegraDeNegocioException {
        log.info("Criando contrato...");
        ContratoDTO contratoDTO = contratoService.create(contratoCreate);
        log.info("Contrato criado com sucesso!");
        return new ResponseEntity<>(contratoDTO, HttpStatus.OK);
    }

    @PutMapping("/{idContrato}")
    public ResponseEntity<ContratoDTO> update(@PathVariable("idContrato") Integer idContrato, @Valid @RequestBody ContratoCreateDTO contratoUpdate) throws RegraDeNegocioException {
        log.info("Atualizando contrato...");
        ContratoDTO contratoUpdated = contratoService.update(idContrato, contratoUpdate);
        log.info("Contrato atualizado com sucesso!");
        return new ResponseEntity<>(contratoUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/{idContrato}")
    public ResponseEntity<Void> delete(@PathVariable("idContrato") Integer idContrato) throws RegraDeNegocioException {
        log.info("Deletando contrato...");
        contratoService.delete(idContrato);
        log.info("Contrato deletado com sucesso!");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/relatorio-contrato-cliente")
    public ResponseEntity<List<RelatorioContratoClienteDTO>> relatorioContratoCliente(@RequestParam(required = false, name = "idContrato") Integer idContrato) {
        log.info("Gerando relatorio...");
        List<RelatorioContratoClienteDTO> lista = contratoService.relatorioContratoCliente(idContrato);
        log.info("Relatorio gerado  com sucesso.");
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

}
