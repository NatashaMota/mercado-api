package br.com.newgo.mercado.services;

import br.com.newgo.mercado.models.ListaDeCompras;
import br.com.newgo.mercado.repository.ListaDeComprasRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ListaDeComprasService {
    private final ListaDeComprasRepository listaDeComprasRepository;

    public ListaDeComprasService(ListaDeComprasRepository listaDeComprasRepository) {
        this.listaDeComprasRepository = listaDeComprasRepository;
    }

    public Optional<ListaDeCompras> acharPorUsuarioId(UUID id){

        return listaDeComprasRepository.findByUsuario(id);
    }

}
