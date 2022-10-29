package br.com.shinigami.controller;


import br.com.shinigami.dto.contrato.ContratoCreateDTO;
import br.com.shinigami.dto.contrato.ContratoDTO;
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
public class ContratoController {

    private final ContratoService contratoService;

    @GetMapping
    public ResponseEntity<List<ContratoDTO>> list() throws RegraDeNegocioException {
        return new ResponseEntity<>(contratoService.list(), HttpStatus.OK);
    }

    @GetMapping("/{idContrato}")
    public ResponseEntity<ContratoDTO> buscarContrato(@PathVariable("idContrato") Integer idContrato) throws RegraDeNegocioException {
        return new ResponseEntity<>(contratoService.buscarContrato(idContrato), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ContratoDTO> create(@Valid @RequestBody ContratoCreateDTO contratoCreate) throws RegraDeNegocioException {
        ContratoDTO contratoDTO = contratoService.create(contratoCreate);
        return new ResponseEntity<>(contratoDTO, HttpStatus.OK);
    }

    @PutMapping("/{idContrato}")
    public ResponseEntity<ContratoDTO> update(@PathVariable("idContrato") Integer idContrato,
                                              @Valid @RequestBody ContratoCreateDTO contratoUpdate) throws RegraDeNegocioException {
        ContratoDTO contratoUpdated = contratoService.update(idContrato, contratoUpdate);
        return new ResponseEntity<>(contratoUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/{idContrato}")
    public ResponseEntity<Void> delete(@PathVariable("idContrato") Integer idContrato) throws RegraDeNegocioException {
        contratoService.delete(idContrato);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
