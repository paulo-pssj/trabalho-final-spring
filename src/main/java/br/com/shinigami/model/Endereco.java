package br.com.shinigami.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class Endereco implements Impressao {
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
    @NotBlank
    @Size(max=50)
    private String complemento;
    @NotEmpty
    private int idEndereco;
    @NotEmpty
    private int numero;

    public Endereco(int idEndereco, String rua, String cidade, String estado, String pais, String cep, int numero, String complemento) {
        this.idEndereco = idEndereco;
        this.rua = rua;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
        this.cep = cep;
        this.numero = numero;
        this.complemento = complemento;
    }
    @Override
    public void imprimir() {
        System.out.println("País: " + pais + " - " +
                "Estado: " + estado + " - " +
                "Cidade: " + cidade + " - " +
                "Rua: " + rua + " - " +
                "Número: " + numero + " - " +
                "Complemento: " + complemento + " - " +
                "CEP: " + cep);
    }
}
