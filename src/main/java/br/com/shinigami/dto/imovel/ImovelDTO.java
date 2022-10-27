package br.com.shinigami.dto.imovel;

import br.com.shinigami.model.Cliente;
import br.com.shinigami.model.Endereco;
import br.com.shinigami.model.TipoImovel;
import lombok.Data;

@Data
public class ImovelDTO extends ImovelCreateDTO {
    private int idImovel;
    private int idEndereco;
    private int qntdQuartos;
    private int qntdBanheiros;
    private double valorMensal;
    private double condominio;
    private boolean alugado;
    private boolean ativo;
    private TipoImovel tipoImovel;
    private Endereco endereco;
    private boolean areaDeLazer;
    private boolean garagem;
    private boolean permiteAnimais;
    private boolean salaoDeFesta;
    private int numeroDeVagas;
    private Cliente dono;
}
