package br.com.newgo.mercado.controllers;

import br.com.newgo.mercado.dtos.ProdutoDto;
import br.com.newgo.mercado.dtos.ProdutoDtoOutput;
import br.com.newgo.mercado.models.Produto;
import br.com.newgo.mercado.repository.ProdutoRepository;
import br.com.newgo.mercado.services.ProdutoService;
import br.com.newgo.mercado.storage.Disco;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/produto")
public class ProdutoController {

    private final ProdutoService produtoService;
    private final Disco disco;
    private final ModelMapper modelMapper;
    private final ProdutoRepository produtoRepository;

    public ProdutoController(ProdutoService produtoService, Disco disco, ModelMapper modelMapper,
                             ProdutoRepository produtoRepository) {
        this.produtoService = produtoService;
        this.disco = disco;
        this.modelMapper = modelMapper;
        this.produtoRepository = produtoRepository;
    }

    @GetMapping({"","/" })
    public ResponseEntity<Object> listarTodos(){
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.listarTodos());
    }

    @GetMapping("/descricao/{descricao}")
    public ResponseEntity<Object> listarDescricao(@PathVariable(name = "descricao") String descricao){
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.listarPorDescricao(descricao));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Object> listarNome(@PathVariable(name = "nome") String nome){
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.listarPorNome(nome));
    }

    @PostMapping(value = {"", "/"})
    public ResponseEntity<Object> adicionar(@RequestBody @Valid ProdutoDto produtoDto){
        Produto produto = new Produto();
        BeanUtils.copyProperties(produtoDto, produto);
        produtoService.salvar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produto);
    }

    @PostMapping(value = {"/{id}/imagem"})
    public ResponseEntity<Object> adicionarImagem(@PathVariable(value = "id") UUID id,
                                                             @RequestPart @Valid MultipartFile imagem){
        Optional<Produto> produtoOptional = produtoService.findById(id);
        if(!produtoEncontrado(produtoOptional)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }
        Produto produto = produtoOptional.get();
        produto.setImagem(disco.salvar(imagem));
        produtoService.salvar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produto);
    }

    @PutMapping(value = { "/{id}"},
            consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Object> alterar(@PathVariable(value = "id") UUID id,
                                          @RequestPart("produto") @Valid ProdutoDto produtoDto,
                                          @RequestPart("foto") MultipartFile foto){
        Optional<Produto> produtoOptional = produtoService.findById(id);
        if(produtoOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }

        Produto produto = modelMapper.map(produtoOptional.get(), Produto.class);
        produto.setImagem(disco.alterar(foto, produtoOptional.get().getImagem()));
        BeanUtils.copyProperties(produtoDto, produto);
        produto.setId(produtoOptional.get().getId());
        produtoService.salvar(produto);
        return ResponseEntity.status(HttpStatus.OK).body(produto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> alterarStataus(@PathVariable(value = "id") UUID id){
        Optional<Produto> produtoOptional = produtoService.findById(id);
        if(produtoOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }

        Produto produtoAlterado = produtoOptional.get();
        produtoAlterado.setAtivo(!produtoAlterado.isAtivo());
        produtoService.salvar(produtoAlterado);
        return ResponseEntity.status(HttpStatus.OK).body(produtoAlterado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable UUID id){
        Optional<Produto> produtoOptional = produtoService.findById(id);
        if(produtoOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }
        disco.remover(produtoOptional.get().getImagem());
        produtoService.deletar(produtoOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Produto deletado com sucesso.");
    }

    private ProdutoDto produtoParaDtoOutput(Produto produto){
        return modelMapper.map(produto, ProdutoDtoOutput.class);
    }

    private boolean produtoEncontrado(Optional optional){
        return optional.isPresent();
    }
}