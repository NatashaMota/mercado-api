package br.com.newgo.mercado.repository;

import br.com.newgo.mercado.models.ProdutoCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface ProdutoCompraRepository extends JpaRepository<ProdutoCompra, UUID> {
    boolean existsByListaCompra_IdAndProduto_Id(UUID listaCompraID, UUID produtoID);

    List<ProdutoCompra> findByListaCompra_Id(UUID id);

    boolean existsById(UUID id);

    void deleteById(UUID id);
}
