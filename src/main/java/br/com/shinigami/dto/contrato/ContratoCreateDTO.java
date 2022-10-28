package br.com.shinigami.dto.contrato;

import br.com.shinigami.model.Cliente;
import br.com.shinigami.model.Imovel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class ContratoCreateDTO {

    @NotEmpty
    private Cliente locador;
    @NotEmpty
    private Cliente locatario;
    @NotEmpty
    private double valorAluguel;
    @NotBlank
    private LocalDate dataEntrada;
    @NotBlank
    private LocalDate dataVencimento;
    @NotEmpty
    private Imovel imovel;
    @NotBlank
    @Size(max=1)
    private boolean ativo;

}
