package br.com.shinigami.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Apartamento extends Imovel {

    private boolean permiteAnimais;
    private boolean salaoDeFesta;
    private int numeroDeVagas;

}
