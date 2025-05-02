package cl.bci.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.bci.user.model.Usuario;

/**
 * The Interface UsuarioRepository.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

	/**
	 * Find by correo.
	 *
	 * @param correo the correo
	 * @return the optional
	 */
	Optional<Usuario> findByCorreo(String correo);
}
