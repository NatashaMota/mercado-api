package br.com.newgo.mercado.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoCompraDtoOutput {

    private UUID id;
    private UUID produtoId;
    private UUID listaCompraId;
    private Integer quantidade;

}
