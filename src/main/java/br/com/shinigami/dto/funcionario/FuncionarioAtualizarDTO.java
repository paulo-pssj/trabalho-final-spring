package br.com.shinigami.dto.funcionario;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class FuncionarioAtualizarDTO {
    @Email
    private String email;
    @NotBlank
    private String login;
    @NotNull
    private Integer idCargo;
}

