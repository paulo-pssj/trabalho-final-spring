package br.com.shinigami.dto.cupom;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CupomDTO {

    private String email;

    private String idMongo;

    private boolean ativo;

    private Double desconto;

    private LocalDate dataCriacao;

    private LocalDate dataVencimento;

}
