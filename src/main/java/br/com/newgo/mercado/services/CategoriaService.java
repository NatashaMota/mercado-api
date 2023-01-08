package br.com.newgo.mercado.services;

import br.com.newgo.mercado.models.Categoria;
import br.com.newgo.mercado.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public boolean existePorNome(String nome) {
        return categoriaRepository.existsByNome(nome);
    }

    public Categoria salvar(Categoria novaCategoria) {
        return categoriaRepository.save(novaCategoria);
    }
}
