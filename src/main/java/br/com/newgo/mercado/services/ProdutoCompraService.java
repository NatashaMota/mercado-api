package br.com.newgo.mercado.services;

import br.com.newgo.mercado.models.ProdutoCompra;
import br.com.newgo.mercado.repository.ProdutoCompraRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class ProdutoCompraService {

    private final ProdutoCompraRepository produtoCompraRepository;


    public ProdutoCompraService(ProdutoCompraRepository produtoCompraRepository) {
        this.produtoCompraRepository = produtoCompraRepository;
    }

    public ProdutoCompra salvar(ProdutoCompra produtoCompra){
        return produtoCompraRepository.save(produtoCompra);
    }

    public boolean existePorListaCompraIdAndProdutoId(UUID listaCompraId, UUID produtoId){
        return produtoCompraRepository.existsByListaCompra_IdAndProduto_Id(listaCompraId, produtoId);
    }

}
