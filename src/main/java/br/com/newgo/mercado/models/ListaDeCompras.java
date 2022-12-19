package br.com.newgo.mercado.models;

//import jakarta.persistence.*;

import javax.persistence.*;

@Entity
@Table(name = "listasCompras")
public class ListaDeCompras extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(nullable = false)
    private int quantidade;

    public ListaDeCompras() {
        super();
    }

    public ListaDeCompras(Produto produto, Usuario usuario, int quantidade) {
        this.produto = produto;
        this.usuario = usuario;
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
