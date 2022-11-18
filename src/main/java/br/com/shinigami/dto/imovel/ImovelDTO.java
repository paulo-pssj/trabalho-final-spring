package br.com.shinigami.dto.imovel;

import br.com.shinigami.dto.cliente.ClienteDTO;
import br.com.shinigami.dto.endereco.EnderecoDTO;
import br.com.shinigami.entity.enums.Tipo;
import br.com.shinigami.entity.enums.TipoImovel;
import lombok.Data;

@Data
public class ImovelDTO {

    private int idImovel;
    private int qntdQuartos;
    private int qntdBanheiros;
    private int numeroDeVagas;
    private double valorMensal;
    private double condominio;
    private TipoImovel tipoImovel;
    private Tipo areaDeLazer;
    private Tipo garagem;
    private Tipo permiteAnimais;
    private Tipo salaoDeFesta;
    private Tipo alugado;
    private EnderecoDTO endereco;
    private ClienteDTO dono;
}
