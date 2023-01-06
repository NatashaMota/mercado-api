package br.com.newgo.mercado.controllers;

import br.com.newgo.mercado.dtos.ListaCompraDto;
import br.com.newgo.mercado.dtos.ListaCompraDtoOutput;
import br.com.newgo.mercado.models.ListaCompra;
import br.com.newgo.mercado.models.Usuario;
import br.com.newgo.mercado.services.ListaCompraService;
import br.com.newgo.mercado.services.UsuarioService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/listaCompra")
public class ListaCompraController {

    private final ListaCompraService listaCompraService;
    private final UsuarioService usuarioService;
    private final ModelMapper modelMapper;
    public ListaCompraController(ListaCompraService listaCompraService, ModelMapper modelMapper,
                                 UsuarioService usuarioService) {
        this.listaCompraService = listaCompraService;
        this.modelMapper = modelMapper;
        this.usuarioService = usuarioService;
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
}