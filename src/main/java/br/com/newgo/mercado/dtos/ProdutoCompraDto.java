package br.com.newgo.mercado.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProdutoCompraDto {

    private UUID idProduto;
    private Integer quantidade;

}
