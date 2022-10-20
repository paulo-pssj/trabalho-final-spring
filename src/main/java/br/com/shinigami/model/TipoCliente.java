package br.com.shinigami.model;
public enum TipoCliente {
    LOCADOR("Locador"),
    LOCATARIO("Locatario");

    private final String descricao;

    TipoCliente(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
