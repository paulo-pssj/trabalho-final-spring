package br.com.shinigami.controller;


import br.com.shinigami.dto.endereco.EnderecoCreateDTO;
import br.com.shinigami.dto.endereco.EnderecoDTO;
import br.com.shinigami.exceptions.BancoDeDadosException;
import br.com.shinigami.exceptions.RegraDeNegocioException;
import br.com.shinigami.service.EnderecoService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@RequestMapping("/endereco")
public class EnderecoController {

    private final EnderecoService enderecoService;
    private final ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity<EnderecoDTO> create(@RequestBody @Valid EnderecoCreateDTO endereco) throws RegraDeNegocioException, BancoDeDadosException {
        log.info("Criando Endereco...");
        EnderecoDTO e = enderecoService.create(endereco);
        log.info("Endereco Criado!!");
        return new ResponseEntity<>(e, HttpStatus.OK);
    }

    @GetMapping
    public List<EnderecoDTO> list() throws BancoDeDadosException {
        return enderecoService.list();
    }

    @GetMapping("/{idEndereco}")
         public EnderecoDTO findByIdEndereco(@PathVariable("idEndereco") Integer id) throws BancoDeDadosException{
             return objectMapper.convertValue(enderecoService.findById(id), EnderecoDTO.class);
         }

    @PutMapping("/{idEndereco}")
    public ResponseEntity<EnderecoDTO> update(@PathVariable("idEndereco") Integer id,
                                              @Valid @RequestBody EnderecoCreateDTO enderecoAtualizar) throws BancoDeDadosException, RegraDeNegocioException {
        log.info("Atualizando Endereco...");
        EnderecoDTO endereco = enderecoService.update(id, enderecoAtualizar);
        log.info("Endereco Atualizado!!");

        return new ResponseEntity<>(endereco,HttpStatus.OK);
    }

    @DeleteMapping("/{idEndereco}")
    public void delete(@PathVariable("idEndereco") Integer id) throws BancoDeDadosException, RegraDeNegocioException {
        log.info("deletando Endereco...");
        enderecoService.delete(id);
        log.info("Endereco Deletado!!");

        new ResponseEntity<>(ResponseEntity.noContent().build(),HttpStatus.OK);
    }
}
