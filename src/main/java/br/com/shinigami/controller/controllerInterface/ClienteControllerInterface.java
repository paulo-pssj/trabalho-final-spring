package br.com.shinigami.controller.controllerInterface;

import br.com.shinigami.dto.cliente.ClienteCreateDTO;
import br.com.shinigami.dto.cliente.ClienteDTO;
import br.com.shinigami.exceptions.RegraDeNegocioException;
import br.com.shinigami.model.TipoCliente;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

public interface ClienteControllerInterface {

    @Operation(summary = "listar clientes", description = "Listar todos os clientes do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de clientes"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    ResponseEntity<List<ClienteDTO>> list() throws RegraDeNegocioException;

    @Operation(summary = "Buscar Cliente", description = "Busca o cliente pelo id do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o cliente"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    ResponseEntity<ClienteDTO> buscarCliente(@PathVariable("idCliente") Integer idCliente) throws RegraDeNegocioException;

    @Operation(summary = "Criar Cliente", description = "Cria o Cliente no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cliente Criado com Sucesso."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    ResponseEntity<ClienteDTO> create(@RequestBody @Valid ClienteCreateDTO cliente) throws RegraDeNegocioException;

    @Operation(summary = "Atualizar Cliente", description = "Atualiza o cliente do banco de dados com base no id e o body informado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cliente Atualizado com sucesso."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    ResponseEntity<ClienteDTO> update(@PathVariable("idCliente") Integer idCliente,
                                      @RequestBody @Valid ClienteCreateDTO clienteAtualizar) throws RegraDeNegocioException;

    @Operation(summary = "Deletar cliente", description = "Deleta o cliente do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cliente Deletado!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    ResponseEntity<Void> delete(@PathVariable("idCliente") Integer idCliente) throws RegraDeNegocioException;

    @Operation(summary = "Lista por tipo", description = "Lista por tipo cliente")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Listado com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    ResponseEntity<List<ClienteDTO>> listByLocadorLocatario(@RequestParam("tipo") TipoCliente tipo) throws RegraDeNegocioException;
}
