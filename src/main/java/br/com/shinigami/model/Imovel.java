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
public class Imovel {

    private int idImovel;
    private int qntdQuartos;
    private int qntdBanheiros;
    private double valorMensal;
    private double condominio;
    private TipoImovel tipoImovel;
    private Tipo alugado;
    private Tipo areaDeLazer;
    private Tipo garagem;
    private Tipo permiteAnimais;
    private Tipo salaoDeFesta;
    private Tipo ativo;
    private int numeroDeVagas;
    private int idEndereco;
    private int idDono;


}
