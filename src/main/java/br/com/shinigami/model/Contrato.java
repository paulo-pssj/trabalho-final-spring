package br.com.shinigami.model;

import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Contrato implements Impressao {

    private Cliente locador;
    private Cliente locatario;
    private double valorAluguel;
    private LocalDate dataEntrada;
    private LocalDate dataVencimento;
    private Imovel imovel;
    private int idContrato;

    private boolean ativo;

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
