package br.com.shinigami.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Imovel {

    private Endereco endereco;
    private int qntdQuartos;
    private int qntdBanheiros;
    private int idImovel;
    private double valorMensal;
    private double condominio;
    private boolean alugado;
    private boolean ativo;
    private TipoImovel tipoImovel;
    private int idEndereco;
    private Cliente dono;


}