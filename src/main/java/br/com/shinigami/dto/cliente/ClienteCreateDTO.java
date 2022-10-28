package br.com.shinigami.dto.cliente;

import br.com.shinigami.model.Tipo;
import br.com.shinigami.model.TipoCliente;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.*;

@Data
public class ClienteCreateDTO {
    @NotBlank
    @Size(max=100)
    private String nome;
    @CPF
    private String cpf;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(max=14)
    private String telefone;
    @NotNull
    private TipoCliente tipoCliente;
}
