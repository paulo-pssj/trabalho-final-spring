package br.com.shinigami.dto.relatorio;

import br.com.shinigami.model.TipoImovel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RelatorioImovelEnderecoDTO {

    private Integer idCliente;
    private String nome;
    private String email;
    private Integer idImovel;
    private TipoImovel tipoImovel;
    private Integer valorMensal;
    private String cidade;
    private String estado;
    private String pais;

}
