package br.com.shinigami.dto.imovel;

import br.com.shinigami.model.Cliente;
import br.com.shinigami.model.Endereco;
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
    @NotEmpty
    private int qntdQuartos;
    @NotEmpty
    private int qntdBanheiros;
    @NotEmpty
    private double valorMensal;
    @NotEmpty
    private double condominio;
    @NotBlank
    @Size(max=1)
    private boolean alugado;
    @NotBlank
    private TipoImovel tipoImovel;
    private boolean areaDeLazer;
    private boolean garagem;
    private boolean permiteAnimais;
    private boolean salaoDeFesta;
    private int numeroDeVagas;

}
