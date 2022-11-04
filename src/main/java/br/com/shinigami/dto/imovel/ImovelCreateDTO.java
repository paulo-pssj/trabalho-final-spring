package br.com.shinigami.dto.imovel;

import br.com.shinigami.model.Tipo;
import br.com.shinigami.model.TipoImovel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class ImovelCreateDTO {
    @NotNull
    @Schema(description = "ID do endereço", defaultValue = "0")
    private Integer idEndereco;
    @NotNull
    @Schema(description = "ID do dono", defaultValue = "0")
    private Integer idDono;
    @NotNull
    @Min(0)
    @Schema(description = "Quantidade de quartos", defaultValue = "0")
    private int qntdQuartos;
    @NotNull
    @Min(0)
    @Schema(description = "Quantidade de banheiros", defaultValue = "0")
    private int qntdBanheiros;
    @NotNull
    @Min(0)
    @Schema(description = "Valor mensal", defaultValue = "100")
    private double valorMensal;
    @NotNull
    @Schema(description = "Valor do condomínio", defaultValue = "0")
    private double condominio;
    @NotNull
    @Schema(description = "Tipo do imóvel (CASA/APARTAMENTO)", defaultValue = "APARTAMENTO")
    private TipoImovel tipoImovel;
    @NotNull
    @Schema(description = "Alugado S/N", defaultValue = "N")
    private Tipo alugado;
    @NotNull
    @Schema(description = "Tem area de lazer S/N", defaultValue = "S")
    private Tipo areaDeLazer;
    @NotNull
    @Schema(description = "Contem Garagem S/N", defaultValue = "S")
    private Tipo garagem;
    @NotNull
    @Schema(description = "Permite animais S/N", defaultValue = "N")
    private Tipo permiteAnimais;
    @NotNull
    @Schema(description = "Contem salão de festa S/N", defaultValue = "N")
    private Tipo salaoDeFesta;
    @Min(0)
    @NotNull
    @Schema(description = "Numero de Vagas", defaultValue = "0")
    private int numeroDeVagas;

}
