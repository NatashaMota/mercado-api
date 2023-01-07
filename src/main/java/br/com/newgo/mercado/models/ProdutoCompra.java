package br.com.newgo.mercado.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "PRODUTO_COMPRA")
public class ProdutoCompra extends AbstractEntity {

    @Column(nullable = false)
    private Integer quantidade;

    @ManyToOne
    private Produto produto;

    @ManyToOne
    private ListaCompra listaCompra;

}
