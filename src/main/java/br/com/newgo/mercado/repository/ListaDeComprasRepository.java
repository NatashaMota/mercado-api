package br.com.newgo.mercado.repository;

import br.com.newgo.mercado.models.ListaDeCompras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ListaDeComprasRepository extends JpaRepository<ListaDeCompras, UUID> {

    Optional<ListaDeCompras> findByUsuario(UUID id);
}
