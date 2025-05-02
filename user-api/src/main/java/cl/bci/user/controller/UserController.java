package cl.bci.user.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.bci.user.dto.UsuarioPatchDTO;
import cl.bci.user.dto.UsuarioRequestDTO;
import cl.bci.user.dto.UsuarioResponseDTO;
import cl.bci.user.service.UserService;
import jakarta.validation.Valid;

/**
 * The Class UserController.
 */
@RestController
@RequestMapping("/usuarios")
public class UserController {

	/** The user service. */
	@Autowired
	private UserService userService;

	/**
	 * Crear usuario.
	 *
	 * @param request the request
	 * @return the response entity
	 */
	@PostMapping
	public ResponseEntity<UsuarioResponseDTO> crearUsuario(@Valid @RequestBody UsuarioRequestDTO request) {
		UsuarioResponseDTO response = userService.crearUsuario(request);
		return ResponseEntity.status(201).body(response);
	}

	/**
	 * Listar usuarios.
	 *
	 * @param correo the correo
	 * @return the response entity
	 */
	@GetMapping
	public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios(@RequestParam(required = false) String correo) {
		List<UsuarioResponseDTO> usuarios = userService.listarUsuarios(correo);
		return ResponseEntity.ok(usuarios);
	}

	/**
	 * Actualizar usuario.
	 *
	 * @param id the id
	 * @param request the request
	 * @return the response entity
	 */
	@PutMapping("/{id}")
	public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(@PathVariable UUID id, @Valid @RequestBody UsuarioRequestDTO request) {
		UsuarioResponseDTO response = userService.actualizarUsuario(id, request);
		return ResponseEntity.ok(response);
	}

	/**
	 * Actualizar parcial usuario.
	 *
	 * @param id the id
	 * @param patch the patch
	 * @return the response entity
	 */
	@PatchMapping("/{id}")
	public ResponseEntity<UsuarioResponseDTO> actualizarParcialUsuario(@PathVariable UUID id, @RequestBody UsuarioPatchDTO patch) {
		UsuarioResponseDTO response = userService.actualizarParcial(id, patch);
		return ResponseEntity.ok(response);
	}

	/**
	 * Eliminar usuario.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarUsuario(@PathVariable UUID id) {
		userService.eliminarUsuario(id);
		return ResponseEntity.noContent().build(); // HTTP 204
	}

}
