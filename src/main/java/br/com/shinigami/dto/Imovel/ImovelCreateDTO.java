package br.com.shinigami.dto.Imovel;

import br.com.shinigami.model.Cliente;
import br.com.shinigami.model.Endereco;
import br.com.shinigami.model.TipoImovel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class ImovelCreateDTO {

    private Endereco endereco;

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

    @NotEmpty
    private int idEndereco;
    private Cliente dono;
}
