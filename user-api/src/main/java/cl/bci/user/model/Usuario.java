package cl.bci.user.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

/**
 * The Class Usuario.
 */
@Entity
public class Usuario {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	/** The nombre. */
	private String nombre;

	/** The correo. */
	@Column(unique = true)
	private String correo;

	/** The contraseña. */
	private String contraseña;

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

	/** The telefonos. */
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Telefono> telefonos;

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
	 * Gets the nombre.
	 *
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Sets the nombre.
	 *
	 * @param nombre the new nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	/**
	 * Gets the contraseña.
	 *
	 * @return the contraseña
	 */
	public String getContraseña() {
		return contraseña;
	}

	/**
	 * Sets the contraseña.
	 *
	 * @param contraseña the new contraseña
	 */
	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
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
	 * Gets the telefonos.
	 *
	 * @return the telefonos
	 */
	public List<Telefono> getTelefonos() {
		return telefonos;
	}

	/**
	 * Sets the telefonos.
	 *
	 * @param telefonos the new telefonos
	 */
	public void setTelefonos(List<Telefono> telefonos) {
		this.telefonos = telefonos;
	}
}
