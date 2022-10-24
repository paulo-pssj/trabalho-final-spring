package br.com.shinigami.model;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente implements Impressao {

    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private Integer idCliente;

    private boolean ativo;
    private TipoCliente tipoCliente;
    @Override
    public void imprimir() {
        System.out.println("Id: " + idCliente + " - " +
                "Nome: " + nome + " - " +
                "CPF: " + cpf + " - " +
                "Email: " + email + " - " +
                "Telefone: " + telefone.trim() + " - " +
                "Tipo Cliente: " + tipoCliente.toString());
    }

}
