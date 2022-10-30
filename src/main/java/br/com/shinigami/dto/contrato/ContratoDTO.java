package br.com.shinigami.dto.contrato;

import br.com.shinigami.model.Cliente;
import br.com.shinigami.model.Imovel;
import br.com.shinigami.model.Tipo;
import lombok.Data;

import java.time.LocalDate;
@Data
public class ContratoDTO {

    private int idContrato;
    private int idLocador;
    private int idlocatario;
    private double valorAluguel;
    private LocalDate dataEntrada;
    private LocalDate dataVencimento;
    private Imovel imovel;
    private Tipo ativo;

}
