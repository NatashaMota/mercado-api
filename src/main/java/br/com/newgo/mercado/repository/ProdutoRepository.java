package br.com.newgo.mercado.repository;

import br.com.newgo.mercado.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, UUID> {

    List<Produto> findAllByNomeContainingIgnoreCase(String nome);

    List<Produto> findByDescricaoContainingIgnoreCase(String descricao);

    List<Produto> findByCategoria_NomeContainingIgnoreCase(String conteudo);

    List<Produto> findByPrecoBetween(Float min, Float max);
}
