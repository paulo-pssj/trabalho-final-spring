package br.com.shinigami.controller;


import br.com.shinigami.controller.controllerInterface.EnderecoControllerInterface;
import br.com.shinigami.dto.endereco.EnderecoCreateDTO;
import br.com.shinigami.dto.endereco.EnderecoDTO;
import br.com.shinigami.exceptions.RegraDeNegocioException;
import br.com.shinigami.EnderecoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.errors.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@Validated
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/endereco")
public class EnderecoController implements EnderecoControllerInterface {

    private final EnderecoService enderecoService;
    private final ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity<EnderecoDTO> create(@RequestBody @Valid EnderecoCreateDTO endereco) throws RegraDeNegocioException, IOException, InterruptedException, ApiException {
        log.info("Criando Endereco...");
        EnderecoDTO e = enderecoService.create(endereco);
        log.info("Endereco Criado!!");
        return new ResponseEntity<>(e, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<EnderecoDTO>> list() throws RegraDeNegocioException {
        log.info("Buscando Enderecos...");
        List<EnderecoDTO> lista = enderecoService.list();
        log.info("Lista de endereços encontrada!");
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{idEndereco}")
    public ResponseEntity<EnderecoDTO> findByIdEndereco(@PathVariable("idEndereco") Integer id) throws RegraDeNegocioException {
        log.info("Buscando endereço...");
        EnderecoDTO enderecoDTO = objectMapper.convertValue(enderecoService.findById(id), EnderecoDTO.class);
        log.info("Endereço encontrado!");
        return new ResponseEntity<>(enderecoDTO, HttpStatus.OK);
    }

    @PutMapping("/{idEndereco}")
    public ResponseEntity<EnderecoDTO> update(@PathVariable("idEndereco") Integer id,
                                              @Valid @RequestBody EnderecoCreateDTO enderecoAtualizar) throws RegraDeNegocioException, IOException, InterruptedException, ApiException {
        log.info("Atualizando Endereco...");
        EnderecoDTO endereco = enderecoService.update(id, enderecoAtualizar);
        log.info("Endereco Atualizado!!");

        return new ResponseEntity<>(endereco, HttpStatus.OK);
    }

    @DeleteMapping("/{idEndereco}")
    public ResponseEntity<Void> delete(@PathVariable("idEndereco") Integer id) throws RegraDeNegocioException {
        log.info("deletando Endereco...");
        enderecoService.delete(id);
        log.info("Endereco Deletado!!");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
