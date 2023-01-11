package br.com.newgo.mercado.services;

import br.com.newgo.mercado.models.Produto;
import br.com.newgo.mercado.repository.CategoriaRepository;
import br.com.newgo.mercado.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoService(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public Page<Produto> listarTodos(Pageable pageable){
        return produtoRepository.findAll(pageable);
    }

    public List<Produto> listarPorNome(String nome){
        return produtoRepository.findAllByNomeContainingIgnoreCase(nome);
    }

    public List<Produto> listarPorDescricao(String descricao){
        return produtoRepository.findByDescricaoContainingIgnoreCase(descricao);
    }

    @Transactional
    public Produto salvar(Produto produto){
        return produtoRepository.save(produto);
    }

    public void deletar(Produto produto){
        produtoRepository.delete(produto);
    }

    public Optional<Produto> findById(UUID id){
        return produtoRepository.findById(id);
    }

}