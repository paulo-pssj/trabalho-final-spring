package br.com.shinigami.model;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente{

    private Integer idCliente;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private Tipo ativo;
    private TipoCliente tipoCliente;

}
