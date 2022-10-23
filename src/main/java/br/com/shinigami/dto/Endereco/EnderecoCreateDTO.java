package br.com.shinigami.dto.Endereco;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class EnderecoCreateDTO {

    @NotBlank
    @Size(max=50)
    private String rua;

    @NotBlank
    @Size(max=50)
    private String cidade;

    @NotBlank
    @Size(max=50)
    private String estado;

    @NotBlank
    @Size(max=50)
    private String pais;

    @NotBlank
    @Size(max=9)
    private String cep;

    @Size(max=50)
    private String complemento;

    @NotEmpty
    private int numero;
}
