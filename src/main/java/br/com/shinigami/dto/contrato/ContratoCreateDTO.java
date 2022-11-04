package br.com.shinigami.dto.contrato;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class ContratoCreateDTO {

    @NotNull
    @Schema(description = "Id do locatario",defaultValue = "1")
    private Integer idLocatario;
    @NotNull
    @Schema(description = "Id do imovel",defaultValue = "1")
    private Integer idImovel;
    @NotNull
    @Schema(description = "Data de entrada",defaultValue = "10/10/2022")
    private LocalDate dataEntrada;
    @NotNull
    @Schema(description = "Data de vencimento do contrato.",defaultValue = "10/10/2023")
    private LocalDate dataVencimento;

}
