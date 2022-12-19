package br.com.newgo.mercado.controllers;

import br.com.newgo.mercado.models.AutenticacaoRequest;
import br.com.newgo.mercado.models.AutenticacaoResposta;
import br.com.newgo.mercado.models.Usuario;
import br.com.newgo.mercado.repository.UsuarioRepository;
import br.com.newgo.mercado.security.JwtTokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/user")
@RestController
public class AutenticacaoController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UsuarioRepository usuarioRepository;

    public AutenticacaoController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil,
                                  UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> logar(@RequestBody @Valid AutenticacaoRequest autenticacaoRequest){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        autenticacaoRequest.getEmail(), autenticacaoRequest.getSenha()));


        Usuario usuario = (Usuario) authentication.getPrincipal();
        String token = jwtTokenUtil.generateAccessToken(usuario);

        AutenticacaoResposta autenticacaoResposta = new AutenticacaoResposta(autenticacaoRequest.getEmail(), token);

        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        return ResponseEntity.status(HttpStatus.OK).body(autenticacaoResposta);

    }



}
