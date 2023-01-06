package br.com.newgo.mercado.services;

import br.com.newgo.mercado.models.ListaCompra;
import br.com.newgo.mercado.models.Usuario;
import br.com.newgo.mercado.repository.ListaCompraRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
