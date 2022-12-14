package br.com.newgo.mercado.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private UUID idProduto;
    @NotNull
    @Min(1)
    private Integer quantidade;

}
