package br.com.shinigami.controller;

import br.com.shinigami.controller.controllerInterface.ClienteControllerInterface;
import br.com.shinigami.dto.cliente.ClienteCreateDTO;
import br.com.shinigami.dto.cliente.ClienteDTO;
import br.com.shinigami.exceptions.RegraDeNegocioException;
import br.com.shinigami.service.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
@RequestMapping("/cliente")
public class ClienteController implements ClienteControllerInterface {

    private final ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> list() throws RegraDeNegocioException {
        log.info("Listando clientes...");
        return new ResponseEntity<>(clienteService.list(), HttpStatus.OK);
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<ClienteDTO> buscarCliente(@PathVariable("idCliente") Integer idCliente) throws RegraDeNegocioException {
        log.info("Buscando cliente...");
        return new ResponseEntity<>(clienteService.buscarCliente(idCliente), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> create(@RequestBody @Valid ClienteCreateDTO cliente) throws RegraDeNegocioException {
        log.info("Criando cliente");
        ClienteDTO clienteDTO = clienteService.create(cliente);
        log.info("Cliente criado com sucesso!");
        return new ResponseEntity<>(clienteDTO, HttpStatus.OK);
    }

    @PutMapping("/{idCliente}")
    public ResponseEntity<ClienteDTO> update(@PathVariable("idCliente") Integer idCliente,
                                             @RequestBody @Valid ClienteCreateDTO clienteAtualizar) throws RegraDeNegocioException {
        log.info("Atualizando cliente...");
        ClienteDTO clienteAtualizado = clienteService.update(idCliente, clienteAtualizar);
        log.info("Cliente atualizado com sucesso!");
        return new ResponseEntity<>(clienteAtualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{idCliente}")
    public ResponseEntity<Void> delete(@PathVariable("idCliente") Integer idCliente) throws RegraDeNegocioException {
        log.info("Deletando cliente...");
        clienteService.delete(idCliente);
        log.info("Cliente deletado com sucesso!");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
