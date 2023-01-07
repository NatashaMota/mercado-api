package br.com.newgo.mercado.services;

import br.com.newgo.mercado.models.ListaCompra;
import br.com.newgo.mercado.models.Usuario;
import br.com.newgo.mercado.repository.ListaCompraRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ListaCompraService {
    private final ListaCompraRepository listaCompraRepository;

    public ListaCompraService(ListaCompraRepository listaCompraRepository) {
        this.listaCompraRepository = listaCompraRepository;
    }

    public ListaCompra salvar(ListaCompra listaCompra){
        return listaCompraRepository.save(listaCompra);
    }

    public List<ListaCompra> acharPorUsuario(Usuario usuario){
        return listaCompraRepository.findByUsuario(usuario);
    }

    public ListaCompra acharPorNome(String nome){
        return listaCompraRepository.findByNome(nome);

    }

    public Optional<ListaCompra> acharPorID(UUID id){
        return listaCompraRepository.findById(id);
    }

    public boolean existePorId(UUID id){
        return listaCompraRepository.existsById(id);
    }

    public void deletarPorId(UUID id){
        listaCompraRepository.deleteById(id);
    }
}
