package br.com.newgo.mercado.services;

import br.com.newgo.mercado.models.Usuario;
import br.com.newgo.mercado.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {

    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Usuario buscarPorEmail(String email){
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = buscarPorEmail(email);
        return new User(usuario.getEmail(), usuario.getSenha(),
                AuthorityUtils.createAuthorityList(usuario.getPerfil().getDescricao()));
    }

    public List<Usuario> listarTodos(){
        return usuarioRepository.findAll();
    }

    public Usuario salvar(Usuario usuario){
        return usuarioRepository.save(usuario);
    }
}