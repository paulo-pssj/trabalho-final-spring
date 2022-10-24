package br.com.shinigami.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Imovel implements Impressao {

    private Endereco endereco;
    private int qntdQuartos;
    private int qntdBanheiros;
    private int idImovel;
    private double valorMensal;
    private double condominio;
    private boolean alugado;
    private boolean ativo;
    private TipoImovel tipoImovel;
    private int idEndereco;
    private Cliente dono;

    @Override
    public void imprimir() {
        System.out.println("" +
                "CEP do endereco: " + endereco.getCep() + " - " +
                "Quantidade de Quartos: " + qntdQuartos + " - " +
                "Quantidade de Banheiros: " + qntdBanheiros + " - " +
                "Valor Mensal Aluguel:" + valorMensal + " - " +
                "Taxa do Condominio: " + condominio + " - " +
                "Alugado: " + alugado + " - " +
                "Tipo do Imovel: " + tipoImovel.toString() + " - " + "Dono: " + dono.getNome());
    }

}
