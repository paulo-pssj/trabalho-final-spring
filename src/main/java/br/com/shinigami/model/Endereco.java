package br.com.shinigami.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Endereco implements Impressao {

    private String rua;
    private String cidade;
    private String estado;
    private String pais;
    private String cep;
    private String complemento;
    private int idEndereco;
    private int numero;

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
