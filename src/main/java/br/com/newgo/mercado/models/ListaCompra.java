package br.com.newgo.mercado.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "LISTA_COMPRA")
public class ListaCompra extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @ManyToOne
    private Usuario usuario;


    @OneToMany(mappedBy = "listaCompra", cascade = CascadeType.ALL)
    private Set<ProdutoCompra> produtoCompras = new HashSet<>();
}
