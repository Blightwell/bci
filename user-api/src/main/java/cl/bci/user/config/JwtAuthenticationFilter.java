package cl.bci.user.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import cl.bci.user.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The Class JwtAuthenticationFilter.
 */
@Component
public class JwtAuthenticationFilter implements Filter {

	/** The jwt util. */
	@Autowired
	private JwtUtil jwtUtil;

	/**
	 * Do filter.
	 *
	 * @param request the request
	 * @param response the response
	 * @param chain the chain
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ServletException the servlet exception
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		// Permitir POST a /usuarios sin autenticación
		String path = httpRequest.getRequestURI();
		if ("POST".equals(httpRequest.getMethod()) && "/usuarios".equals(path) || path.startsWith("/h2-console")) {
			chain.doFilter(request, response);
			return;
		}

		String authHeader = httpRequest.getHeader("Authorization");

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);
			try {
				Claims claims = jwtUtil.validarToken(token);

				// Configurar autenticación manualmente
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						claims.getSubject(), null, null); // subject = correo
				SecurityContextHolder.getContext().setAuthentication(authentication);

				chain.doFilter(request, response);

			} catch (Exception e) {
				httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
				httpResponse.setContentType("application/json");
				httpResponse.setCharacterEncoding("UTF-8");
				httpResponse.getWriter().write("{\"message\":\"Token inválido o expirado\"}");
			}
		} else {
			httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
			httpResponse.setContentType("application/json");
			httpResponse.setCharacterEncoding("UTF-8");
			httpResponse.getWriter().write("{\"message\":\"Falta token de autorización\"}");
		}
	}
}
