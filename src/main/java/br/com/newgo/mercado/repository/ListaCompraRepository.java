package br.com.newgo.mercado.repository;

import br.com.newgo.mercado.models.ListaCompra;
import br.com.newgo.mercado.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ListaCompraRepository extends JpaRepository<ListaCompra, UUID> {
    List<ListaCompra> findByUsuario(Usuario usuario);

    ListaCompra findByNome(String nome);
    Optional<ListaCompra> findById(UUID id);

    boolean existsById(UUID id);

    void deleteById(UUID id);

}
