package br.com.shinigami.controller;


import br.com.shinigami.controller.controllerInterface.ImovelControllerInterface;
import br.com.shinigami.dto.RelatorioImovelEnderecoDTO;
import br.com.shinigami.dto.imovel.ImovelCreateDTO;
import br.com.shinigami.dto.imovel.ImovelDTO;
import br.com.shinigami.dto.page.PageDTO;
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

    @GetMapping("/imovel-paginado")
    public ResponseEntity<PageDTO<ImovelDTO>> list(@RequestParam("page") Integer page) throws RegraDeNegocioException {
        log.info("Listando imóveis...");
        PageDTO<ImovelDTO> listaImoveis = imovelService.list(page);
        log.info("Imoveis listados!");

        return new ResponseEntity<>(listaImoveis, HttpStatus.OK);
    }

    @GetMapping("/{idImovel}")
    public ResponseEntity<ImovelDTO> findById(@PathVariable("idImovel") Integer idEndereco) throws RegraDeNegocioException {
        log.info("Buscando imovel...");
        ImovelDTO imovel = imovelService.findByIdImovel(idEndereco);
        log.info("Imovel encontrado!!");
        return new ResponseEntity<>(imovel, HttpStatus.OK);
    }

    @GetMapping("/listar-disponiveis")
    public ResponseEntity<List<ImovelDTO>> findByDisponiveis() throws RegraDeNegocioException {
        log.info("Listando imóveis disponíveis...");
        List<ImovelDTO> listaImoveis = imovelService.listarImoveisDisponiveis();
        log.info("Imoveis listados!");
        return new ResponseEntity<>(listaImoveis, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ImovelDTO> create(@RequestBody @Valid ImovelCreateDTO imovelCreate) throws RegraDeNegocioException {
        log.info("Criando Imovel...");
        ImovelDTO imovel = imovelService.create(imovelCreate);
        log.info("Imovel Criado!!");
        return new ResponseEntity<>(imovel, HttpStatus.OK);
    }

    @PutMapping("/{idImovel}")
    public ResponseEntity<ImovelDTO> update(@PathVariable("idImovel") Integer idImovel,
                                            @RequestBody @Valid ImovelCreateDTO imovelAtualizar) throws RegraDeNegocioException {
        log.info("Atualizando Imovel...");
        ImovelDTO imovel = imovelService.update(idImovel, imovelAtualizar);
        log.info("Imóvel atualizado com sucesso!");
        return new ResponseEntity<>(imovel, HttpStatus.OK);
    }

    @DeleteMapping("/{idImovel}")
    public ResponseEntity<Void> delete(@PathVariable("idImovel") Integer idImovel) throws RegraDeNegocioException {
        log.info("Deletando Imovel...");
        imovelService.delete(idImovel);
        log.info("Imovel Deletado!!");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/relatorio-imovel-endereco")
    public ResponseEntity<List<RelatorioImovelEnderecoDTO>> relatorioImovelEndereco(@RequestParam(required = false, name = "idImovel") Integer idImovel) {
        log.info("Gerando Relatorio...");
        List<RelatorioImovelEnderecoDTO> lista = imovelService.relatorioImovelEndereco(idImovel);
        log.info("Relatorio Gerado com sucesso.");
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }
}
