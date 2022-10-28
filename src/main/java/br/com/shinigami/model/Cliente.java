package br.com.shinigami.model;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente{

    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private Integer idCliente;
    private Tipo ativo;
    private TipoCliente tipoCliente;

}
