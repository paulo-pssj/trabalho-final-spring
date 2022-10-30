package br.com.shinigami.dto.imovel;

import br.com.shinigami.model.Cliente;
import br.com.shinigami.model.Endereco;
import br.com.shinigami.model.Tipo;
import br.com.shinigami.model.TipoImovel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ImovelCreateDTO {
    @NotNull
    private Integer idEndereco;
    @NotNull
    private Integer idDono;
    @NotNull
    private int qntdQuartos;
    @NotNull
    private int qntdBanheiros;
    @NotNull
    private double valorMensal;
    @NotNull
    private double condominio;
    @NotNull
    private TipoImovel tipoImovel;
    @NotNull
    private Tipo alugado;
    private Tipo areaDeLazer;
    private Tipo garagem;
    private Tipo permiteAnimais;
    private Tipo salaoDeFesta;
    private int numeroDeVagas;

}
