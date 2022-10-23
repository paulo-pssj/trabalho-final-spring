package br.com.shinigami.dto;

import lombok.Data;

@Data
public class EnderecoDTO extends EnderecoCreateDTO {
    private String rua;
    private String cidade;
    private String estado;
    private String pais;
    private String cep;
    private String complemento;
    private int idEndereco;
    private int numero;
}
