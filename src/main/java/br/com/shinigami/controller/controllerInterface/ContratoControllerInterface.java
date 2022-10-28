package br.com.shinigami.controller.controllerInterface;

import br.com.shinigami.dto.cliente.ClienteCreateDTO;
import br.com.shinigami.dto.cliente.ClienteDTO;
import br.com.shinigami.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public interface ContratoControllerInterface {

    @Operation(summary = "listar contratos", description = "Listar todos os contratos do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de contratos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    ResponseEntity<List<ClienteDTO>> list() throws RegraDeNegocioException;

    @Operation(summary = "Buscar contrato", description = "Busca o contrato pelo id do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o contrato"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    ResponseEntity<ClienteDTO> buscarContrato(@PathVariable("idContrato") Integer idContrato) throws RegraDeNegocioException;

    @Operation(summary = "Criar contrato", description = "Cria o contrato no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Contrato criado com Sucesso."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    ResponseEntity<ClienteDTO> create(@RequestBody @Valid ClienteCreateDTO contrato) throws RegraDeNegocioException;

    @Operation(summary = "Atualizar contrato", description = "Atualiza o contrato do banco de dados com base no id e o body informado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Contrato atualizado com sucesso."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    ResponseEntity<ClienteDTO> update(@PathVariable("idContrato") Integer idContrato,
                                      @RequestBody @Valid ClienteCreateDTO contratoCreate) throws RegraDeNegocioException;

    @Operation(summary = "Deletar contrato", description = "Deleta o contrato do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Contrato deletado!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    ResponseEntity<Void> delete(@PathVariable("idContrato") Integer idContrato) throws RegraDeNegocioException;

}
