package br.com.shinigami.dto.cliente;

import br.com.shinigami.entity.TipoCliente;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ClienteCreateDTO {
    @NotBlank
    @Size(max = 100)
    @Schema(description = "Nome do cliente", defaultValue = "Example")
    private String nome;
    @CPF()
    @Schema(description = "CPF do cliente", defaultValue = "111.222.333-45")
    private String cpf;
    @NotBlank
    @Email
    @Schema(description = "E-mail do cliente", defaultValue = "example@gmail.com")
    private String email;
    @NotBlank
    @Size(max = 14)
    @Schema(description = "Telefone do cliente", defaultValue = "(DDD)940028922")
    private String telefone;
    @NotNull
    @Schema(description = "Tipo de cliente (LOCADOR/LOCATARIO)", defaultValue = "LOCADOR")
    private TipoCliente tipoCliente;
}
