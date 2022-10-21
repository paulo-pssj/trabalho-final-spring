package br.com.shinigami.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class Cliente implements Impressao {

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

    public Cliente(int idCliente, String nome, String cpf, String email, String telefone, TipoCliente tipoCliente) {
        this.idCliente = idCliente;
        this.telefone = telefone;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.tipoCliente = tipoCliente;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

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
