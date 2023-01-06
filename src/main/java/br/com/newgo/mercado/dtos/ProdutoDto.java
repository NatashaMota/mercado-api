package br.com.newgo.mercado.dtos;

import jakarta.validation.constraints.NotBlank;


public class ProdutoDto {

    @NotBlank
    private String nome;
    @NotBlank
    private String descricao;

    public ProdutoDto(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public ProdutoDto() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
