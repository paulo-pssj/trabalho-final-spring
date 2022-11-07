package br.com.shinigami.dto;

import br.com.shinigami.entity.TipoImovel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RelatorioImovelEnderecoDTO {

    private int idCliente;
    private String nome;
    private String email;
    private int idImovel;
    private TipoImovel tipoImovel;
    private double valorMensal;
    private String cidade;
    private String estado;
    private String pais;

}
