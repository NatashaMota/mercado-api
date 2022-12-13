package br.com.newgo.mercado.services;

import br.com.newgo.mercado.models.Produto;
import br.com.newgo.mercado.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public Produto salvar(Produto produto){
        return produtoRepository.save(produto);
    }
}