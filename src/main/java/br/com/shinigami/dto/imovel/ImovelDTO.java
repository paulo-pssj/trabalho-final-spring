package br.com.shinigami.dto.imovel;

import br.com.shinigami.dto.cliente.ClienteDTO;
import br.com.shinigami.dto.endereco.EnderecoDTO;
import br.com.shinigami.model.Cliente;
import br.com.shinigami.model.Endereco;
import br.com.shinigami.model.Tipo;
import br.com.shinigami.model.TipoImovel;
import lombok.Data;

@Data
public class ImovelDTO  {

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
    private int idEndereco;
    private int idDono;
}
