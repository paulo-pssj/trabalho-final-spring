package br.com.shinigami.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Casa extends Imovel {
    private boolean areaDeLazer;
    private boolean garagem;
}
