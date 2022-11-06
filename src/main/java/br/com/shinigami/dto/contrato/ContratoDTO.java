package br.com.shinigami.dto.contrato;

import br.com.shinigami.dto.cliente.ClienteDTO;
import br.com.shinigami.dto.imovel.ImovelDTO;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ContratoDTO {

    private int idContrato;
    private double valorAluguel;
    private LocalDate dataEntrada;
    private LocalDate dataVencimento;
    private ImovelDTO imovel;
    private ClienteDTO Locador;
    private ClienteDTO locatario;

}
