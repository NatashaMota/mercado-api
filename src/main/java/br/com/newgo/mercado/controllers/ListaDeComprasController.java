package br.com.newgo.mercado.controllers;

import br.com.newgo.mercado.dtos.ListaDeComprasDto;
import br.com.newgo.mercado.models.Usuario;
import br.com.newgo.mercado.repository.UsuarioRepository;
import br.com.newgo.mercado.services.ListaDeComprasService;
import br.com.newgo.mercado.services.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping("/listaCompra")
public class ListaDeComprasController {

    private final ListaDeComprasService listaDeComprasService;
    private final UsuarioService usuarioService;
    private final ModelMapper modelMapper;

    public ListaDeComprasController(ListaDeComprasService listaDeComprasService, ModelMapper modelMapper,
                                    UsuarioService usuarioService,
                                    UsuarioRepository usuarioRepository) {
        this.listaDeComprasService = listaDeComprasService;
        this.modelMapper = modelMapper;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<Object> listarPorId(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(listaDeComprasService.acharPorUsuarioId(id));
    }

    @PostMapping()
    public ResponseEntity<Object> adicionar(@RequestBody @Valid ListaDeComprasDto listaDeComprasDto,
                                            Authentication authentication){
        for(UUID id: listaDeComprasDto.getLista().keySet()){
            Usuario usuario = usuarioService.buscarPorEmail(authentication.getName());
        }

        return ResponseEntity.status(HttpStatus.OK).body(listaDeComprasDto);
    }
}