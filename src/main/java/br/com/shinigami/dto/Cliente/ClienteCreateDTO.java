package br.com.shinigami.dto.Cliente;

import br.com.shinigami.model.TipoCliente;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class ClienteCreateDTO {
    @NotBlank
    @Size(max=100)
    private String nome;
    @NotBlank
    @Size(max=11)
    private String cpf;
    @NotBlank
    @Size(max=100)
    private String email;
    @NotBlank
    @Size(max=14)
    private String telefone;
    @NotEmpty
    private int idCliente;
    @NotBlank
    @Size(max=1)
    private boolean ativo;
    @NotBlank
    private TipoCliente tipoCliente;
}
