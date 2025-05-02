package cl.bci.user.dto;

import java.util.List;

/**
 * The Class UsuarioPatchDTO.
 */
public class UsuarioPatchDTO {

	/** The nombre. */
	private String nombre;

	/** The contraseña. */
	private String contraseña;

	/** The telefonos. */
	private List<PhoneDTO> telefonos;

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
	 * Gets the telefonos.
	 *
	 * @return the telefonos
	 */
	public List<PhoneDTO> getTelefonos() {
		return telefonos;
	}

	/**
	 * Sets the telefonos.
	 *
	 * @param telefonos the new telefonos
	 */
	public void setTelefonos(List<PhoneDTO> telefonos) {
		this.telefonos = telefonos;
	}
}
