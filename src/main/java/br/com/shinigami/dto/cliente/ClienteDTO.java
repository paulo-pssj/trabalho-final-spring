package br.com.shinigami.dto.cliente;

import br.com.shinigami.dto.endereco.EnderecoDTO;
import br.com.shinigami.model.TipoCliente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class ClienteDTO extends ClienteCreateDTO {
    private int idCliente;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private TipoCliente tipoCliente;

}
