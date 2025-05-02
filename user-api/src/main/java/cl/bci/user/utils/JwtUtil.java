package cl.bci.user.utils;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * The Class JwtUtil.
 */
@Component
public class JwtUtil {

	/* PARA QUE EL SECRETO Y EL TIEMPO DE EXPIRACIÓN SEA CONFIGURABLE SE TIENE QUE PASAR ESTA LOGICA A UN SERVICE EN VEZ DE UTIL */
	
	/** The Constant SECRET_KEY. */
	// Clave secreta (en producción se utilizaria una en .env u otro sistema seguro)
	private static final String SECRET_KEY = "mySecretKeyBCI12345";

	/** The Constant EXPIRATION_TIME. */
	// Tiempo de expiración: 60 minutos
	private static final long EXPIRATION_TIME = 60 * 60 * 1000L;

	/**
	 * Generar token.
	 *
	 * @param correo the correo
	 * @return the string
	 */
	public String generarToken(String correo) {
		return Jwts.builder()
				.setSubject(correo)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes())
				.compact();
	}

	/**
	 * Validar token.
	 *
	 * @param token the token
	 * @return the claims
	 */
	public Claims validarToken(String token) {
		return Jwts.parser()
				.setSigningKey(SECRET_KEY.getBytes())
				.parseClaimsJws(token)
				.getBody();
	}

}
