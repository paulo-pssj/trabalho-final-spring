package br.com.shinigami.dto.imovel;

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
    private double valorMensal;
    private double condominio;
    private Tipo alugado;
    private TipoImovel tipoImovel;
    private Endereco endereco;
    private Tipo areaDeLazer;
    private Tipo garagem;
    private Tipo permiteAnimais;
    private Tipo salaoDeFesta;
    private int numeroDeVagas;
    private Cliente dono;
}
