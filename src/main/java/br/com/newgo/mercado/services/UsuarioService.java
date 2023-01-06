package br.com.newgo.mercado.services;

import br.com.newgo.mercado.models.Usuario;
import br.com.newgo.mercado.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Usuario buscarPorEmail(String email){
        return usuarioRepository.findByEmail(email);
    }

    public List<Usuario> listarTodos(){
        return usuarioRepository.findAll();
    }

    public Usuario salvar(Usuario usuario){
        return usuarioRepository.save(usuario);
    }
}