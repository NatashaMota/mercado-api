package br.com.newgo.mercado.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "produtos")
public class Produto extends AbstractEntity {

    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String descricao;
    private String imagem;

    private boolean ativo = false;

    @NotNull
    @Positive
    private Float preco;

    //@OneToMany(mappedBy = "produto")
    //private Set<ProdutoCompra> produtoCompras = new HashSet<>();
    @ManyToOne
    private Categoria categoria;

}