package cl.bci.user;

import java.sql.SQLException;

import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The Class UserApplication.
 */
@SpringBootApplication
public class UserApplication {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) throws SQLException {
		// Para permitir conexion a H2 desde DB Manager (solamente para scope del ejercicio)
		Server.createTcpServer("-tcp", "-tcpAllowOthers", "-ifNotExists").start();
		SpringApplication.run(UserApplication.class, args);
	}

}
