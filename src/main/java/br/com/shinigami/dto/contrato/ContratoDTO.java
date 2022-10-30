package br.com.shinigami.dto.contrato;

import br.com.shinigami.dto.cliente.ClienteDTO;
import br.com.shinigami.dto.imovel.ImovelDTO;
import br.com.shinigami.model.Cliente;
import br.com.shinigami.model.Imovel;
import br.com.shinigami.model.Tipo;
import lombok.Data;

import java.time.LocalDate;
@Data
public class ContratoDTO {

    private int Contrato;
    private double valorAluguel;
    private LocalDate dataEntrada;
    private LocalDate dataVencimento;
    private Tipo ativo;
    private ImovelDTO imovel;
    private ClienteDTO Locador;
    private ClienteDTO locatario;

}
