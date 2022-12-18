package br.com.newgo.mercado.dtos;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ListaDeComprasDto {


    private Map<UUID, Integer> lista = new HashMap<>();

    public ListaDeComprasDto() {
    }

    public ListaDeComprasDto(Map<UUID, Integer> lista) {
        this.lista = lista;
    }

    public Map<UUID, Integer> getLista() {
        return lista;
    }

    public void setLista(Map<UUID, Integer> lista) {
        this.lista = lista;
    }
}
