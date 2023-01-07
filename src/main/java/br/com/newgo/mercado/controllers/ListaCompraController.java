package br.com.newgo.mercado.controllers;

import br.com.newgo.mercado.dtos.*;
import br.com.newgo.mercado.models.ListaCompra;
import br.com.newgo.mercado.models.Produto;
import br.com.newgo.mercado.models.ProdutoCompra;
import br.com.newgo.mercado.models.Usuario;
import br.com.newgo.mercado.services.ListaCompraService;
import br.com.newgo.mercado.services.ProdutoCompraService;
import br.com.newgo.mercado.services.ProdutoService;
import br.com.newgo.mercado.services.UsuarioService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/listaCompra")
public class ListaCompraController {

    private final ListaCompraService listaCompraService;
    private final UsuarioService usuarioService;
    private final ModelMapper modelMapper;

    private final ProdutoService produtoService;
    private final ProdutoCompraService produtoCompraService;

    public ListaCompraController(ListaCompraService listaCompraService, ModelMapper modelMapper,
                                 UsuarioService usuarioService,
                                 ProdutoService produtoService, ProdutoCompraService produtoCompraService) {
        this.listaCompraService = listaCompraService;
        this.modelMapper = modelMapper;
        this.usuarioService = usuarioService;
        this.produtoService = produtoService;
        this.produtoCompraService = produtoCompraService;
    }

    @GetMapping({"", "/"})
    public ResponseEntity<Object> listarPorUsuario(){

        Usuario usuario = usuarioService.buscarPorEmail("adm@adm.com");

        List<ListaCompraDtoOutput> lista = new ArrayList<>();
        for (ListaCompra listaCompra: listaCompraService.acharPorUsuario(usuario) ){
            lista.add(modelMapper.map(listaCompra, ListaCompraDtoOutput.class));
        }

        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @PostMapping({"", "/"})
    public ResponseEntity<Object> adicionar(@RequestBody @Valid ListaCompraDto listaCompraDto){

        Usuario usuario = usuarioService.buscarPorEmail("adm@adm.com");

        ListaCompra novaListaCompra = new ListaCompra();
        novaListaCompra.setNome(listaCompraDto.getNome());
        novaListaCompra.setUsuario(usuario);
        listaCompraService.salvar(novaListaCompra);

        return ResponseEntity.status(HttpStatus.OK).body(listaCompraDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> remover(@PathVariable UUID id){
        if (!listaCompraService.existePorId(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lista não existe.");
        }

        //TODO: verificar se a lista pertence ao usuario

        listaCompraService.deletarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }


    @PostMapping({"/{id}/produtos"})
    public ResponseEntity<Object> adicionarProdutoCompra(@PathVariable UUID id,
            @RequestBody @Valid ProdutoCompraDto produtoCompraDto){

        Optional<ListaCompra> listaCompraOptional = listaCompraService.acharPorID(id);
        if (listaCompraOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lista não encontrada.");

        }
        ListaCompra listaCompra = listaCompraOptional.get();

        Optional<Produto> produtoOptional = produtoService.findById(produtoCompraDto.getIdProduto());
        if(produtoOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }

        Produto produto = produtoOptional.get();

        if(produtoCompraService.existePorListaCompraIdAndProdutoId(listaCompra.getId(), produto.getId())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Produto já existe.");
        }

        Usuario usuario = usuarioService.buscarPorEmail("adm@adm.com");
        if (listaCompra.getUsuario() != usuario){
            //TODO: Usuario nao tem permissao pra add
        }

        ProdutoCompra produtoCompra = new ProdutoCompra();
        produtoCompra.setQuantidade(produtoCompraDto.getQuantidade());
        produtoCompra.setProduto(produto);
        produtoCompra.setListaCompra(listaCompra);

        produtoCompraService.salvar(produtoCompra);

        return ResponseEntity.status(HttpStatus.OK).body(
                modelMapper.map(produtoCompra, ProdutoCompraDtoOutput.class));
    }

    @GetMapping({"/{id}/produtos"})
    public ResponseEntity<Object> listarProdutosListaCompra(@PathVariable UUID id){
        List<ProdutoCompraDtoOutput> lista = new ArrayList<>();
        for(ProdutoCompra produtoCompra: produtoCompraService.acharPorListaId(id)){
            lista.add(modelMapper.map(produtoCompra, ProdutoCompraDtoOutput.class));
        }
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @PutMapping({"/produtos/{id}"})
    public ResponseEntity<Object> alterarProdutosListaCompra(@PathVariable UUID id,
                                                             @RequestBody @Valid ProdutoCompraDtoAlterar produtoCompraDto){
        Optional<ProdutoCompra> produtoCompraOptional = produtoCompraService.acharPorId(id);
        if(produtoCompraOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto-Compra não encontrado na lista.");
        }

        ProdutoCompra novoProdutoCompra = produtoCompraOptional.get();
        novoProdutoCompra.setQuantidade(produtoCompraDto.getQuantidade());
        produtoCompraService.salvar(novoProdutoCompra);

        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(novoProdutoCompra, ProdutoCompraDtoOutput.class));
    }

}