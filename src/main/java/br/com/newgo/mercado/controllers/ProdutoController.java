package br.com.newgo.mercado.controllers;

import br.com.newgo.mercado.dtos.ProdutoDto;
import br.com.newgo.mercado.dtos.ProdutoDtoOutput;
import br.com.newgo.mercado.models.Produto;
import br.com.newgo.mercado.services.ProdutoService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/produto")
public class ProdutoController {

    private final ProdutoService produtoService;
    private final ModelMapper modelMapper;

    public ProdutoController(ProdutoService produtoService, ModelMapper modelMapper) {
        this.produtoService = produtoService;
        this.modelMapper = modelMapper;
    }

    @GetMapping({"","/" })
    public ResponseEntity<Object> listarTodos(){
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.listarTodos());
    }

    @PostMapping({"", "/"})
    public ResponseEntity<Object> adicionar(@RequestBody @Valid ProdutoDto produtoDto){
        Produto produto = new Produto();
        BeanUtils.copyProperties(produtoDto, produto);
        produtoService.salvar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                produtoParaDtoOutput(produto)
        );
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<Object> alterar(@PathVariable(value = "id") UUID id,
                                          @RequestBody @Valid ProdutoDto produtoDto){
        Optional<Produto> produtoOptional = produtoService.findById(id);
        if(produtoOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }

        Produto produto = modelMapper.map(produtoOptional.get(), Produto.class);
        BeanUtils.copyProperties(produtoDto, produto);
        produto.setId(produtoOptional.get().getId());
        produtoService.salvar(produto);
        return ResponseEntity.status(HttpStatus.OK).body(produtoParaDtoOutput(produto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable UUID id){
        Optional<Produto> produtoOptional = produtoService.findById(id);
        if(produtoOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }
        produtoService.deletar(produtoOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Produto deletado com sucesso.");
    }

    public ProdutoDto produtoParaDtoOutput(Produto produto){
        return modelMapper.map(produto, ProdutoDtoOutput.class);
    }
}