package br.com.newgo.mercado.services;

import br.com.newgo.mercado.models.Categoria;
import br.com.newgo.mercado.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public boolean existePorNome(String nome) {
        return categoriaRepository.existsByNomeIgnoreCase(nome);
    }

    public Categoria salvar(Categoria novaCategoria) {
        return categoriaRepository.save(novaCategoria);
    }

    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> listarPorId(UUID id){
        return categoriaRepository.findById(id);
    }

    public Optional<Categoria> listarPorNome(String nome){
        return categoriaRepository.findCategoriaByNomeIgnoreCase(nome);
    }
}
