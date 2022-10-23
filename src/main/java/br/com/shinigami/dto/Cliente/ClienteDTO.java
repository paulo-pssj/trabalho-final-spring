package br.com.shinigami.dto.Cliente;

import br.com.shinigami.model.TipoCliente;
import lombok.Data;
@Data
public class ClienteDTO extends ClienteCreateDTO {
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private int idCliente;
    private boolean ativo;
    private TipoCliente tipoCliente;
}
