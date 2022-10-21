package br.com.shinigami.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
@NoArgsConstructor
@Getter
@Setter
public class Contrato implements Impressao {
    @NotEmpty
    private Cliente locador;
    @NotEmpty
    private Cliente locatario;
    @NotEmpty
    private double valorAluguel;
    @NotBlank
    private LocalDate dataEntrada;
    @NotBlank
    private LocalDate dataVencimento;
    @NotEmpty
    private Imovel imovel;
    @NotEmpty
    private int idContrato;
    @NotBlank
    @Size(max=1)
    private boolean ativo;
    public Contrato(int idContrato, Cliente locador, Cliente locatario, double valorAluguel, LocalDate dataEntrada, LocalDate dataVencimento, Imovel imovel) {
        this.locador = locador;
        this.locatario = locatario;
        this.valorAluguel = valorAluguel;
        this.dataEntrada = dataEntrada;
        this.dataVencimento = dataVencimento;
        this.imovel = imovel;
        this.idContrato = idContrato;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public void imprimir() {
        System.out.print("\n- Informações do contrato selecionado -\n" +
                "Numero Do Contrato: " + idContrato + " \n " +
                "Locador: " + "Nome Locador: " + locador.getNome() +
                " - CPF Locador: " + locador.getCpf() + " \n " +
                "Locatario: " + "Nome Locatario: " + locatario.getNome() +
                " - CPF Locatario: " + locatario.getCpf() + " \n " +
                "Valor do Aluguel: " + valorAluguel + " - " +
                "Data de Entrada: " + dataEntrada + " - " +
                "Data de Vencimento: " + dataVencimento + " - \n" +
                " Endereço: ");
        imovel.getEndereco().imprimir();
    }
}
