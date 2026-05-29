package br.com.fiap.eclipseprotocol.repository;

import br.com.fiap.eclipseprotocol.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
