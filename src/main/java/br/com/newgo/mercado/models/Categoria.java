package br.com.newgo.mercado.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CATEGORIAS")
public class Categoria extends AbstractEntity{

    @NotNull
    private String nome;

    @OneToMany(mappedBy = "categoria")
    private Set<Produto> produtos = new HashSet<>();

}
