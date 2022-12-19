package br.com.newgo.mercado.services;

import br.com.newgo.mercado.models.Produto;
import br.com.newgo.mercado.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> listarTodos(){
        return produtoRepository.findAll();
    }

    public List<Produto> listarPorNome(String nome){
        return produtoRepository.findAllByNome(nome);
    }

    public List<Produto> listarPorDescricao(String descricao){
        return produtoRepository.findByDescricaoContaining(descricao);
    }

    //@Transactional
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