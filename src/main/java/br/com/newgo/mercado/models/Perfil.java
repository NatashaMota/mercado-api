package br.com.newgo.mercado.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "perfis")
public class Perfil extends AbstractEntity{

    @Column(nullable = false, unique = true)
    private String descricao;

    public Perfil() {
    }

    public Perfil(String descricao) {
        super();
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
