package br.com.shinigami.dto.funcionario;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AlterarSenhaDTO {

    @NotBlank
    private String senha;
}
