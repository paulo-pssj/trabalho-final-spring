package br.com.shinigami.controller.controllerInterface;

import br.com.shinigami.dto.endereco.EnderecoCreateDTO;
import br.com.shinigami.dto.endereco.EnderecoDTO;
import br.com.shinigami.exceptions.RegraDeNegocioException;
import com.google.maps.errors.ApiException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

public interface EnderecoControllerInterface {
    @Operation(summary = "listar endereços", description = "Listar todos os endereços do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de endereços"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    ResponseEntity<List<EnderecoDTO>> list() throws RegraDeNegocioException;

    @Operation(summary = "Buscar endereço", description = "Busca o endereço pelo id do banco de dados de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o endereço"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    public ResponseEntity<EnderecoDTO> findByIdEndereco(@PathVariable("idEndereco") Integer id) throws RegraDeNegocioException;

    @Operation(summary = "Criar endereco", description = "Cria o endereço no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Endereço criado com sucesso."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    public ResponseEntity<EnderecoDTO> create(@RequestBody @Valid EnderecoCreateDTO endereco) throws RegraDeNegocioException, IOException, InterruptedException, ApiException;

    @Operation(summary = "Atualizar endereço", description = "Atualiza o endereço do banco de dados com base no id e o body informado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    public ResponseEntity<EnderecoDTO> update(@PathVariable("idEndereco") Integer id,
                                              @Valid @RequestBody EnderecoCreateDTO enderecoAtualizar) throws RegraDeNegocioException, IOException, InterruptedException, ApiException;

    @Operation(summary = "Deletar endereço", description = "Deleta o endereço do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Endereço deletado!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    ResponseEntity<Void> delete(@PathVariable("idEndereco") Integer id) throws RegraDeNegocioException;
}
