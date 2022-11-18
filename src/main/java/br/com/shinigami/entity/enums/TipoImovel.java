package br.com.shinigami.entity.enums;

public enum TipoImovel {
    CASA("Casa"),
    APARTAMENTO("Apartamento");

    private final String descricao;

    TipoImovel(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
