package br.com.shinigami.dto.endereco;

import lombok.Data;

@Data
public class EnderecoDTO extends EnderecoCreateDTO {
    private String rua;
    private String cidade;
    private String estado;
    private String pais;
    private String cep;
    private String complemento;
    private Integer idEndereco;
    private int numero;
}
