package br.com.newgo.mercado.controllers;

import br.com.newgo.mercado.dtos.UsuarioDto;
import br.com.newgo.mercado.models.Perfil;
import br.com.newgo.mercado.models.PerfilTipo;
import br.com.newgo.mercado.models.Usuario;
import br.com.newgo.mercado.services.UsuarioService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping({"/", ""})
    public ResponseEntity<Object> listarTodos(){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.listarTodos());
    }

    @PostMapping({"/", ""})
    public ResponseEntity<Object> adicionar(@RequestBody UsuarioDto usuarioDto){
        Usuario novoUsuario = new Usuario();
        BeanUtils.copyProperties(usuarioDto, novoUsuario, "senha");
        String crypt = new BCryptPasswordEncoder().encode(usuarioDto.getSenha());
        novoUsuario.setSenha(crypt);
        novoUsuario.setPerfil(new Perfil(PerfilTipo.COMUM.getDescricao()));
        this.usuarioService.salvar(novoUsuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

}
