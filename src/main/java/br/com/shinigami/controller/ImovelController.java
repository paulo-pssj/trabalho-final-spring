package br.com.shinigami.controller;


import br.com.shinigami.controller.controllerInterface.ImovelControllerInterface;
import br.com.shinigami.dto.imovel.ImovelCreateDTO;
import br.com.shinigami.dto.imovel.ImovelDTO;
import br.com.shinigami.exceptions.RegraDeNegocioException;
import br.com.shinigami.service.ImovelService;
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
@RequestMapping("/imovel")
public class ImovelController implements ImovelControllerInterface {

    private final ImovelService imovelService;

    @GetMapping
    public ResponseEntity<List<ImovelDTO>> list() throws RegraDeNegocioException {
        return new ResponseEntity<>(imovelService.list(), HttpStatus.OK);
    }

    @GetMapping("/listar-disponiveis")
    public ResponseEntity<List<ImovelDTO>> listarDisponiveis() throws RegraDeNegocioException{
        return new ResponseEntity<>(imovelService.listarImoveisDisponiveis(), HttpStatus.OK);
    }

    @GetMapping("/{idImovel}")
    public ResponseEntity<ImovelDTO> findById(@PathVariable("idImovel") Integer idEndereco) throws RegraDeNegocioException{
        return new ResponseEntity<>(imovelService.buscarImovel(idEndereco), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ImovelDTO> create(@RequestBody @Valid ImovelCreateDTO imovel) throws RegraDeNegocioException{
        return new ResponseEntity<>(imovelService.create(imovel), HttpStatus.OK);
    }

    @PutMapping("/{idImovel}")
    public ResponseEntity<ImovelDTO> update(@PathVariable("idImovel") Integer idImovel,
                                            @RequestBody @Valid ImovelCreateDTO imovelAtualizar) throws RegraDeNegocioException{
        return new ResponseEntity<>(imovelService.update(idImovel, imovelAtualizar), HttpStatus.OK);
    }

    @DeleteMapping("/{idImovel}")
    public ResponseEntity<Void> delete(@PathVariable("idImovel") Integer idImovel) throws RegraDeNegocioException{
        imovelService.delete(idImovel);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
