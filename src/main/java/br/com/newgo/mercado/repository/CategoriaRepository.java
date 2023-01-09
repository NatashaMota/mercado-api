package br.com.newgo.mercado.repository;

import br.com.newgo.mercado.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {
    boolean existsByNomeIgnoreCase(String nome);
    Optional<Categoria> findCategoriaByNomeIgnoreCase(String nome);
}
