package br.com.shinigami.entity.enums;

public enum TipoCliente {
    LOCADOR("LOCADOR"),
    LOCATARIO("LOCATARIO");

    private final String descricao;

    TipoCliente(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
