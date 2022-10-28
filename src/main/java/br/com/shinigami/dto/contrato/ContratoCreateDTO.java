package br.com.shinigami.dto.contrato;

import br.com.shinigami.model.Cliente;
import br.com.shinigami.model.Imovel;
import br.com.shinigami.model.Tipo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class ContratoCreateDTO {

    @NotNull
    private Integer idLocador;
    @NotNull
    private Integer idlocatario;
    @NotNull
    private double valorAluguel;
    @NotBlank
    private LocalDate dataEntrada;
    @NotBlank
    private LocalDate dataVencimento;
    @NotNull
    private Integer idImovel;

}
