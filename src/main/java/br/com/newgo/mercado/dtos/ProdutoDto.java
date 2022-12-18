package br.com.newgo.mercado.dtos;

import jakarta.validation.constraints.NotBlank;

public class ProdutoDto {

    @NotBlank
    private String nome;
    @NotBlank
    private String descricao;
    @NotBlank
    private String imagem;

    public ProdutoDto(String nome, String descricao, String imagem) {
        this.nome = nome;
        this.descricao = descricao;
        this.imagem = imagem;
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

    public String getImagemCaminho() {
        return imagem;
    }

    public void setImagemCaminho(String imagemCaminho) {
        this.imagem = imagemCaminho;
    }
}
