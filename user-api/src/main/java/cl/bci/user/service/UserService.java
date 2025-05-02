package cl.bci.user.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.bci.user.dto.UsuarioPatchDTO;
import cl.bci.user.dto.UsuarioRequestDTO;
import cl.bci.user.dto.UsuarioResponseDTO;
import cl.bci.user.exception.UserException;
import cl.bci.user.model.Telefono;
import cl.bci.user.model.Usuario;
import cl.bci.user.repository.UsuarioRepository;
import cl.bci.user.utils.JwtUtil;
import cl.bci.user.utils.UserExceptionUtil;

/**
 * The Class UserService.
 */
@Service
public class UserService {

	/** The usuario repository. */
	@Autowired
	private UsuarioRepository usuarioRepository;

	/** The jwt util. */
	@Autowired
	private JwtUtil jwtUtil;

	/** The password regex. */
	@Value("${password.regex}")
	private String passwordRegex;

	/**
	 * Crear usuario.
	 *
	 * @param request the request
	 * @return the usuario response DTO
	 */
	public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO request) {
		try {
			if (!request.getContraseña().matches(passwordRegex)) {
				throw UserExceptionUtil.INVALID_PASSWORD_FORMAT;
			}

			// Validar si correo ya existe
			usuarioRepository.findByCorreo(request.getCorreo())
					.ifPresent(usuario -> {
						throw UserExceptionUtil.USER_FOUND;
					});

			// Construir usuario
			Usuario usuario = new Usuario();
			usuario.setNombre(request.getNombre());
			usuario.setCorreo(request.getCorreo());
			usuario.setContraseña(request.getContraseña());

			LocalDateTime now = LocalDateTime.now();
			usuario.setCreado(now);
			usuario.setModificado(now);
			usuario.setUltimoLogin(now);
			usuario.setActivo(true);

			String token = jwtUtil.generarToken(request.getCorreo());
			usuario.setToken(token);

			// Mapear teléfonos
			List<Telefono> telefonos = request.getTelefonos().stream().map(p -> {
				Telefono telefono = new Telefono();
				telefono.setNumero(p.getNumero());
				telefono.setCodigoCiudad(p.getCodigoCiudad());
				telefono.setCodigoPais(p.getCodigoPais());
				telefono.setUsuario(usuario);
				return telefono;
			}).toList();

			usuario.setTelefonos(telefonos);

			// Guardar en BD
			Usuario guardado = usuarioRepository.save(usuario);

			// Crear respuesta
			UsuarioResponseDTO response = new UsuarioResponseDTO();
			response.setId(guardado.getId());
			response.setCreado(guardado.getCreado());
			response.setModificado(guardado.getModificado());
			response.setUltimoLogin(guardado.getUltimoLogin());
			response.setToken(guardado.getToken());
			response.setActivo(guardado.isActivo());

			return response;
		} catch (UserException e) {
			throw e;
		} catch (Exception e) {
			throw new UserException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear usuario: " + e.getMessage());
		}
	}

	/**
	 * Listar usuarios.
	 *
	 * @param correo the correo
	 * @return the list
	 */
	@Transactional(readOnly = true)
	public List<UsuarioResponseDTO> listarUsuarios(String correo) {
		try {

			if (correo != null && !"".equals(correo.trim())) {
				Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);
				if (usuarioOpt.isEmpty()) {
					return new ArrayList<>();
				}

				Usuario usuario = usuarioOpt.get();
				UsuarioResponseDTO response = new UsuarioResponseDTO();
				response.setId(usuario.getId());
				response.setCreado(usuario.getCreado());
				response.setModificado(usuario.getModificado());
				response.setUltimoLogin(usuario.getUltimoLogin());
				response.setToken(usuario.getToken());
				response.setActivo(usuario.isActivo());
				// Se agrega para identificar al usuario de mejor manera, solo para efectos de prueba
				response.setCorreo(usuario.getCorreo());

				return Arrays.asList((response));
			} else {
				List<Usuario> usuarios = usuarioRepository.findAll();
				return usuarios.stream().map(usuario -> {
					UsuarioResponseDTO response = new UsuarioResponseDTO();
					response.setId(usuario.getId());
					response.setCreado(usuario.getCreado());
					response.setModificado(usuario.getModificado());
					response.setUltimoLogin(usuario.getUltimoLogin());
					response.setToken(usuario.getToken());
					response.setActivo(usuario.isActivo());
					// Se agrega para identificar al usuario de mejor manera, solo para efectos de prueba
					response.setCorreo(usuario.getCorreo());
					return response;
				}).toList();
			}

		} catch (UserException e) {
			throw e;
		} catch (Exception e) {
			throw new UserException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al listar usuarios: " + e.getMessage());
		}
	}

	/**
	 * Actualizar usuario.
	 *
	 * @param id the id
	 * @param request the request
	 * @return the usuario response DTO
	 */
	public UsuarioResponseDTO actualizarUsuario(UUID id, UsuarioRequestDTO request) {
		try {
			if (request.getContraseña() != null && !"".equals(request.getContraseña()) && !request.getContraseña().matches(passwordRegex)) {
				throw UserExceptionUtil.INVALID_PASSWORD_FORMAT;
			}

			Usuario usuario = usuarioRepository.findById(id)
					.orElseThrow(() -> UserExceptionUtil.USER_NOT_FOUND);

			usuario.setNombre(request.getNombre());
			usuario.setCorreo(request.getCorreo());
			usuario.setContraseña(request.getContraseña());
			usuario.setModificado(LocalDateTime.now());

			List<Telefono> telefonos = request.getTelefonos().stream().map(p -> {
				Telefono telefono = new Telefono();
				telefono.setNumero(p.getNumero());
				telefono.setCodigoCiudad(p.getCodigoCiudad());
				telefono.setCodigoPais(p.getCodigoPais());
				telefono.setUsuario(usuario);
				return telefono;
			}).toList();

			usuario.getTelefonos().clear();
			usuario.getTelefonos().addAll(telefonos);

			Usuario actualizado = usuarioRepository.save(usuario);

			UsuarioResponseDTO response = new UsuarioResponseDTO();
			response.setId(actualizado.getId());
			response.setCreado(actualizado.getCreado());
			response.setModificado(actualizado.getModificado());
			response.setUltimoLogin(actualizado.getUltimoLogin());
			response.setToken(actualizado.getToken());
			response.setActivo(actualizado.isActivo());
			response.setCorreo(actualizado.getCorreo());

			return response;
		} catch (UserException e) {
			throw e;
		} catch (Exception e) {
			throw new UserException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar usuario: " + e.getMessage());
		}
	}

	/**
	 * Actualizar parcial.
	 *
	 * @param id the id
	 * @param patch the patch
	 * @return the usuario response DTO
	 */
	public UsuarioResponseDTO actualizarParcial(UUID id, UsuarioPatchDTO patch) {
		try {
			Usuario usuario = usuarioRepository.findById(id)
					.orElseThrow(() -> UserExceptionUtil.USER_NOT_FOUND);

			if (patch.getNombre() != null) {
				usuario.setNombre(patch.getNombre());
			}

			if (patch.getContraseña() != null) {
				usuario.setContraseña(patch.getContraseña());
			}

			if (patch.getTelefonos() != null && !patch.getTelefonos().isEmpty()) {
				List<Telefono> telefonos = patch.getTelefonos().stream().map(p -> {
					Telefono telefono = new Telefono();
					telefono.setNumero(p.getNumero());
					telefono.setCodigoCiudad(p.getCodigoCiudad());
					telefono.setCodigoPais(p.getCodigoPais());
					telefono.setUsuario(usuario);
					return telefono;
				}).toList();

				usuario.getTelefonos().clear();
				usuario.getTelefonos().addAll(telefonos);
			}

			usuario.setModificado(LocalDateTime.now());
			Usuario actualizado = usuarioRepository.save(usuario);

			UsuarioResponseDTO response = new UsuarioResponseDTO();
			response.setId(actualizado.getId());
			response.setCreado(actualizado.getCreado());
			response.setModificado(actualizado.getModificado());
			response.setUltimoLogin(actualizado.getUltimoLogin());
			response.setToken(actualizado.getToken());
			response.setActivo(actualizado.isActivo());

			return response;
		} catch (UserException e) {
			throw e;
		} catch (Exception e) {
			throw new UserException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar parcialmente al usuario: " + e.getMessage());
		}
	}

	/**
	 * Eliminar usuario.
	 *
	 * @param id the id
	 */
	public void eliminarUsuario(UUID id) {
		try {
			Usuario usuario = usuarioRepository.findById(id)
					.orElseThrow(() -> UserExceptionUtil.USER_NOT_FOUND);
			usuarioRepository.delete(usuario);
		} catch (UserException e) {
			throw e;
		} catch (Exception e) {
			throw new UserException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar al usuario: " + e.getMessage());
		}
	}

}
