package br.com.shinigami.model;

import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Contrato{

    private Cliente locador;
    private Cliente locatario;
    private double valorAluguel;
    private LocalDate dataEntrada;
    private LocalDate dataVencimento;
    private Imovel imovel;
    private int idContrato;
    private boolean ativo;

}
