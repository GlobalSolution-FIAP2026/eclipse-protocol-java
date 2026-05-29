package br.com.fiap.eclipseprotocol.service;

import br.com.fiap.eclipseprotocol.model.Usuario;
import br.com.fiap.eclipseprotocol.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<Usuario> listarTodos() {
        return repository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public Usuario salvar(Usuario usuario) {
        return repository.save(usuario);
    }

    public Usuario atualizar(Long id, Usuario usuarioAtualizado) {
        Usuario usuario = buscarPorId(id);

        usuario.setNome(usuarioAtualizado.getNome());
        usuario.setEmail(usuarioAtualizado.getEmail());
        usuario.setSenha(usuarioAtualizado.getSenha());
        usuario.setAtivo(usuarioAtualizado.getAtivo());

        return repository.save(usuario);
    }

    public void deletar(Long id) {
        Usuario usuario = buscarPorId(id);
        repository.delete(usuario);
    }
}
