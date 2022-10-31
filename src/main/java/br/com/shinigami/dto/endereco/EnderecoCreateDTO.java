package br.com.shinigami.dto.endereco;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class EnderecoCreateDTO {

    @NotBlank
    @Size(max=50)
    @Schema(description = "Rua",defaultValue = "Rua das missões")
    private String rua;
    @NotBlank
    @Size(max=50)
    @Schema(description = "Cidade",defaultValue = "Blumenau")
    private String cidade;
    @NotBlank
    @Size(max=50)
    @Schema(description = "Estado",defaultValue = "Santa Catarina")
    private String estado;
    @NotBlank
    @Size(max=50)
    @Schema(description = "País",defaultValue = "Brasil")
    private String pais;
    @NotBlank
    @Size(max=9)
    @Schema(description = "Cep",defaultValue = "89000-080")
    private String cep;
    @Size(max=50)
    @Schema(description = "Complemento",defaultValue = "")
    private String complemento;
    @NotNull
    @Schema(description = "Numero",defaultValue = "455")
    private Integer numero;
}
