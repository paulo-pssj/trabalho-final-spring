package br.com.shinigami.entity.enums;

public enum Tipo {
    S("S"),
    N("N");

    private final String descricao;

    Tipo(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
