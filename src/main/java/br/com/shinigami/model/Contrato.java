package br.com.shinigami.model;

import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Contrato{

    private int idContrato;
    private double valorAluguel;
    private LocalDate dataEntrada;
    private LocalDate dataVencimento;
    private Tipo ativo;
    private int idLocador;
    private int idLocatario;
    private int idImovel;

}
