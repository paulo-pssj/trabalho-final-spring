package br.com.shinigami.controller;

import br.com.shinigami.dto.cliente.ClienteCreateDTO;
import br.com.shinigami.dto.cliente.ClienteDTO;
import br.com.shinigami.exceptions.BancoDeDadosException;
import br.com.shinigami.exceptions.RegraDeNegocioException;
import br.com.shinigami.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
public class ClienteController {

    private final ClienteService clienteService;

    @Operation(summary = "listar clientes", description = "Lista todas os clientes do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de clientes"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> list() throws RegraDeNegocioException, BancoDeDadosException {
        return new ResponseEntity<>( clienteService.list(), HttpStatus.OK);
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<ClienteDTO> buscarCliente(@PathVariable("idCliente") Integer idCliente) throws RegraDeNegocioException, BancoDeDadosException{
        return new ResponseEntity<>(clienteService.buscarCliente(idCliente), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> create(@RequestBody @Valid ClienteCreateDTO cliente)throws RegraDeNegocioException, BancoDeDadosException{
        ClienteDTO clienteDTO = clienteService.create(cliente);
        return  new ResponseEntity<>(clienteDTO, HttpStatus.OK);
    }

    @PutMapping("/{idCliente}")
    public ResponseEntity<ClienteDTO> update(@PathVariable("idCliente") Integer idCliente,
                                             @RequestBody @Valid ClienteCreateDTO clienteAtualizar)throws RegraDeNegocioException, BancoDeDadosException{
        ClienteDTO clienteAtualizado = clienteService.update(idCliente,clienteAtualizar);
        return new ResponseEntity<>(clienteAtualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{idCliente}")
    public ResponseEntity<Void> delete(@PathVariable("idCliente") Integer idCliente)throws RegraDeNegocioException, BancoDeDadosException{
        clienteService.delete(idCliente);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
