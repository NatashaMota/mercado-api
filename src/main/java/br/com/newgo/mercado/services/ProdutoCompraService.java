package br.com.newgo.mercado.services;

import br.com.newgo.mercado.models.ProdutoCompra;
import br.com.newgo.mercado.repository.ProdutoCompraRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public List<ProdutoCompra> acharPorListaId(UUID listaCompraId){
        return produtoCompraRepository.findByListaCompra_Id(listaCompraId);
    }

    public Optional<ProdutoCompra> acharPorId(UUID id){
        return produtoCompraRepository.findById(id);
    }

    public boolean existePorId(UUID id) {
        return produtoCompraRepository.existsById(id);
    }

    public void deletarPorId(UUID id) {
        produtoCompraRepository.deleteById(id);
    }
}
