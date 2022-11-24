package br.com.shinigami.controller.documentinterface;

import br.com.shinigami.dto.RelatorioContratoClienteDTO;
import br.com.shinigami.dto.contrato.ContratoCreateDTO;
import br.com.shinigami.dto.contrato.ContratoDTO;
import br.com.shinigami.dto.page.PageDTO;
import br.com.shinigami.exceptions.RegraDeNegocioException;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

public interface ContratoControllerInterface {

    @Operation(summary = "Listar contratos paginados", description = "listar contratos paginados do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Contratos listados em páginas!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    ResponseEntity<PageDTO<ContratoDTO>> list(@RequestParam("page") Integer page) throws RegraDeNegocioException;

    @Operation(summary = "Buscar contrato", description = "Busca o contrato pelo id do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o contrato"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    ResponseEntity<ContratoDTO> buscarContrato(@PathVariable("idContrato") Integer idContrato) throws RegraDeNegocioException;

    @Operation(summary = "Criar contrato", description = "Cria o contrato no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Contrato criado com Sucesso."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    ResponseEntity<ContratoDTO> create(@RequestBody @Valid ContratoCreateDTO contrato) throws RegraDeNegocioException, JsonProcessingException;

    @Operation(summary = "Atualizar contrato", description = "Atualiza o contrato do banco de dados com base no id e o body informado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Contrato atualizado com sucesso."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    ResponseEntity<ContratoDTO> update(@PathVariable("idContrato") Integer idContrato,
                                       @RequestBody @Valid ContratoCreateDTO contratoCreate) throws RegraDeNegocioException;

    @Operation(summary = "Deletar contrato", description = "Deleta o contrato do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Contrato deletado!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    ResponseEntity<Void> delete(@PathVariable("idContrato") Integer idContrato) throws RegraDeNegocioException;

    @Operation(summary = "Relatório de contrato", description = "Relatório do contrato")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Relatório do contrato emitido com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    ResponseEntity<List<RelatorioContratoClienteDTO>> relatorioContratoCliente(@RequestParam(required = false, name = "idContrato") Integer idContrato);
}
