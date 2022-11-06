package br.com.shinigami.dto;

import br.com.shinigami.model.TipoCliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RelatorioContratoClienteDTO {

    private Integer idContrato;
    private LocalDate dataEntrada;
    private LocalDate dataVencimento;
    private double valorAluguel;
    private String nomeLocador;
    private String cpfLocador;
    private String emailLocador;
    private TipoCliente tipoLocador;
    private String nomeLocatario;
    private String cpfLocatario;
    private String emailLocatario;
    private TipoCliente tipoLocatario;
}
