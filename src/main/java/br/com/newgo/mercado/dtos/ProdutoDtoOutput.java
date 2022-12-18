package br.com.newgo.mercado.dtos;

import java.util.UUID;

public class ProdutoDtoOutput extends ProdutoDto{
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
