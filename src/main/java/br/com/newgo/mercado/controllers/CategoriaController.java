package br.com.newgo.mercado.controllers;

import br.com.newgo.mercado.dtos.CategoriaDto;
import br.com.newgo.mercado.dtos.CategoriaDtoOutput;
import br.com.newgo.mercado.models.Categoria;
import br.com.newgo.mercado.services.CategoriaService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final ModelMapper modelMapper;


    public CategoriaController(CategoriaService categoriaService, ModelMapper modelMapper) {
        this.categoriaService = categoriaService;
        this.modelMapper = modelMapper;
    }

    @PostMapping({"", "/"})
    public ResponseEntity<Object> salvar(@RequestBody CategoriaDto categoriaDto) {
        if (categoriaService.existePorNome(categoriaDto.getNome())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Esta categoria já existe.");
        }
        Categoria novaCategoria = modelMapper.map(categoriaDto, Categoria.class);
        categoriaService.salvar(novaCategoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(novaCategoria, CategoriaDtoOutput.class));
    }

}
