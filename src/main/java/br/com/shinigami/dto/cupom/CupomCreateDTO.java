package br.com.shinigami.dto.cupom;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class CupomCreateDTO {

    @Email
    private String email;

    @NotNull
    private boolean ativo;

    @NotNull
    private Double desconto;

    @Past
    @NotNull
    private LocalDate dataCriacao;

    @PastOrPresent
    @NotNull
    private LocalDate dataVencimento;
}
