package cl.bci.user.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * The Class UsuarioResponseDTO.
 */
@JsonInclude(Include.NON_NULL)
public class UsuarioResponseDTO {

	/** The id. */
	private UUID id;

	/** The creado. */
	private LocalDateTime creado;

	/** The modificado. */
	private LocalDateTime modificado;

	/** The ultimo login. */
	private LocalDateTime ultimoLogin;

	/** The token. */
	private String token;

	/** The activo. */
	private boolean activo;

	/** The correo. */
	private String correo;
	// Getters y Setters

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(UUID id) {
		this.id = id;
	}

	/**
	 * Gets the creado.
	 *
	 * @return the creado
	 */
	public LocalDateTime getCreado() {
		return creado;
	}

	/**
	 * Sets the creado.
	 *
	 * @param creado the new creado
	 */
	public void setCreado(LocalDateTime creado) {
		this.creado = creado;
	}

	/**
	 * Gets the modificado.
	 *
	 * @return the modificado
	 */
	public LocalDateTime getModificado() {
		return modificado;
	}

	/**
	 * Sets the modificado.
	 *
	 * @param modificado the new modificado
	 */
	public void setModificado(LocalDateTime modificado) {
		this.modificado = modificado;
	}

	/**
	 * Gets the ultimo login.
	 *
	 * @return the ultimo login
	 */
	public LocalDateTime getUltimoLogin() {
		return ultimoLogin;
	}

	/**
	 * Sets the ultimo login.
	 *
	 * @param ultimoLogin the new ultimo login
	 */
	public void setUltimoLogin(LocalDateTime ultimoLogin) {
		this.ultimoLogin = ultimoLogin;
	}

	/**
	 * Gets the token.
	 *
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Sets the token.
	 *
	 * @param token the new token
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * Checks if is activo.
	 *
	 * @return true, if is activo
	 */
	public boolean isActivo() {
		return activo;
	}

	/**
	 * Sets the activo.
	 *
	 * @param activo the new activo
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	/**
	 * Gets the correo.
	 *
	 * @return the correo
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Sets the correo.
	 *
	 * @param correo the new correo
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}
}
