package br.com.shinigami.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Endereco{

    private Integer idEndereco;
    private String rua;
    private String cidade;
    private String estado;
    private String pais;
    private String cep;
    private String complemento;
    private Integer numero;

}
