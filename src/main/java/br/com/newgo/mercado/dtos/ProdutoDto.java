package br.com.newgo.mercado.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDto {

    @NotBlank
    private String nome;
    @NotBlank
    private String descricao;
    @NotNull
    private String categoriaNome;
    @NotNull
    @Positive
    private Float preco;
}
