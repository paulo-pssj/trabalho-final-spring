package br.com.shinigami.controller.documentinterface;

import br.com.shinigami.dto.context.ContextDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface ContextControllerInterface {

    @Operation(summary = "listar latitudes e longitudes", description = "Lista latitudes e longitudes dos endereços armazenados para ser consumido em uma API externa que fixa pontos em um mapa estático referentes a localização")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de latitudes e longitudes"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<List<ContextDTO>> listar();
}
