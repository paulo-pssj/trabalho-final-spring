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
public abstract class Imovel implements Impressao {

    private Endereco endereco;
    @NotEmpty
    private int qntdQuartos;
    @NotEmpty
    private int qntdBanheiros;
    @NotEmpty
    private int idImovel;
    @NotEmpty
    private double valorMensal;
    @NotEmpty
    private double condominio;
    @NotBlank
    @Size(max=1)
    private boolean alugado;
    @NotBlank
    @Size(max=1)
    private boolean ativo;
    private TipoImovel tipoImovel;
    @NotEmpty
    private int idEndereco;
    private Cliente dono;

    public Imovel(Endereco endereco, int qntdQuartos, int qntdBanheiros, double valorMensal, double condominio, TipoImovel tipoImovel) {
        this.endereco = endereco;
        this.qntdQuartos = qntdQuartos;
        this.qntdBanheiros = qntdBanheiros;
        this.valorMensal = valorMensal;
        this.condominio = condominio;
        this.tipoImovel = tipoImovel;
        alugado = false;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

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
