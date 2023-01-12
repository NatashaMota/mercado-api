package br.com.newgo.mercado.controllers;

import br.com.newgo.mercado.dtos.ProdutoDto;
import br.com.newgo.mercado.dtos.ProdutoDtoOutput;
import br.com.newgo.mercado.models.Categoria;
import br.com.newgo.mercado.models.Produto;
import br.com.newgo.mercado.repository.CategoriaRepository;
import br.com.newgo.mercado.repository.ProdutoCompraRepository;
import br.com.newgo.mercado.repository.ProdutoRepository;
import br.com.newgo.mercado.services.CategoriaService;
import br.com.newgo.mercado.services.ProdutoService;
import br.com.newgo.mercado.storage.Disco;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/produto")
public class ProdutoController {

    private final ProdutoService produtoService;
    private final Disco disco;
    private final ModelMapper modelMapper;

    private final CategoriaService categoriaService;
    private final CategoriaRepository categoriaRepository;
    private final ProdutoRepository produtoRepository;
    private final ProdutoCompraRepository produtoCompraRepository;

    public ProdutoController(ProdutoService produtoService, Disco disco, ModelMapper modelMapper, CategoriaService categoriaService,
                             CategoriaRepository categoriaRepository,
                             ProdutoRepository produtoRepository,
                             ProdutoCompraRepository produtoCompraRepository) {
        this.produtoService = produtoService;
        this.disco = disco;
        this.modelMapper = modelMapper;
        this.categoriaService = categoriaService;
        this.categoriaRepository = categoriaRepository;
        this.produtoRepository = produtoRepository;
        this.produtoCompraRepository = produtoCompraRepository;
    }

    @GetMapping({"","/" })
    public ResponseEntity<Page> listarTodos(Pageable pageable){
        Page<Produto> produtos = produtoService.listarTodos(pageable);
        List<ProdutoDtoOutput> produtosList = new ArrayList<>();

        for(Produto p: produtos){
            ProdutoDtoOutput p2 = modelMapper.map(p, ProdutoDtoOutput.class);
            produtosList.add(p2);
        }
        Page<ProdutoDtoOutput> produtoDtoOutputPage = new PageImpl<>(produtosList);
        return ResponseEntity.status(HttpStatus.OK).body(produtoDtoOutputPage);
    }

    @GetMapping("/descricao/{descricao}")
    public ResponseEntity<Object> listarDescricao(@PathVariable(name = "descricao") String descricao){
        List<ProdutoDtoOutput> produtos = produtosParaProdutoDtoOutput(produtoService.listarPorDescricao(descricao));
        return ResponseEntity.status(HttpStatus.OK).body(produtos);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Object> listarNome(@PathVariable(name = "nome") String nome){
        List<ProdutoDtoOutput> produtos = produtosParaProdutoDtoOutput(produtoService.listarPorNome(nome));
        return ResponseEntity.status(HttpStatus.OK).body(produtos);
    }

    @GetMapping("/contendo/{conteudo}")
    public ResponseEntity<Object> listarContendo(@PathVariable(name = "conteudo") String conteudo){
        List<ProdutoDtoOutput> produtos = produtoService.listarContendo(conteudo);
        if(produtos.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum produto foi encontrado.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(produtos);
    }

    @GetMapping("/faixaPreco/{min}/{max}")
    public ResponseEntity<Object> listarPorFaixaPreco(@PathVariable(name = "min") Float minimo,
                                                      @PathVariable(name = "max") Float maximo){
        List<ProdutoDtoOutput> produtos = produtoService.listarPorFaixaPreco(minimo, maximo);
        if (produtos.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum produto foi encontrado.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(produtos);
    }

    @PostMapping(value = {"", "/"})
    public ResponseEntity<Object> adicionar(@RequestBody @Valid ProdutoDto produtoDto){
        if(!categoriaService.existePorNome(produtoDto.getCategoriaNome())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não existe.");
        }

        Optional<Categoria> categoria = categoriaService.listarPorNome(produtoDto.getCategoriaNome());
        Produto produto = modelMapper.map(produtoDto, Produto.class);
        produto.setCategoria(categoria.get());
        produtoService.salvar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(produto, ProdutoDtoOutput.class));
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
        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(produto, ProdutoDtoOutput.class));
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

        Optional<Categoria> categoria = categoriaService.listarPorNome(produtoDto.getCategoriaNome());
        if (categoria.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não existe.");
        }

        produto.setId(produtoOptional.get().getId());
        produto.setCategoria(categoria.get());
        produtoService.salvar(produto);
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(produto, ProdutoDtoOutput.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> alterarStataus(@PathVariable(value = "id") UUID id){
        Optional<Produto> produtoOptional = produtoService.findById(id);
        if(produtoOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }
        Produto produto = produtoOptional.get();

        if(produto.isAtivo()){
            produto.setAtivo(false);
        } else {
            produto.setAtivo(true);
        }

        produtoService.salvar(produto);

        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(produto, ProdutoDtoOutput.class));
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

    private List<ProdutoDtoOutput> produtosParaProdutoDtoOutput(List<Produto> produtos){
        List<ProdutoDtoOutput> produtosDtoOutputList = new ArrayList<>();
        for(Produto produto: produtos){
            produtosDtoOutputList.add(modelMapper.map(produto, ProdutoDtoOutput.class));
        }
        return produtosDtoOutputList;
    }

    private boolean produtoEncontrado(Optional optional){
        return optional.isPresent();
    }
}