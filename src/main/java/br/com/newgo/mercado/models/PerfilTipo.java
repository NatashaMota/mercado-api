package br.com.newgo.mercado.models;

public enum PerfilTipo {
    ADMIN(1, "ADMIN"), COMUM(2, "COMUM");

    private int codigo;
    private String descricao;

    PerfilTipo(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo(){
        return this.codigo;
    }

    public String getDescricao(){
        return this.descricao;
    }

}
