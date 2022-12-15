package com.alkemyProject.disneyAPI_v1;

import com.alkemyProject.disneyAPI_v1.entidades.Rol;
import com.alkemyProject.disneyAPI_v1.entidades.Usuario;
import com.alkemyProject.disneyAPI_v1.servicio.UsuarioServicio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class DisneyApiV1Application {

	public static void main(String[] args) {
		SpringApplication.run(DisneyApiV1Application.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
/*

	@Bean
	CommandLineRunner commandLineRunner(UsuarioServicio userService) {
		return args -> {

			 //agregando roles a la BD
			userService.guardarRol(new Rol("ROLE_USER"));
			userService.guardarRol(new Rol("ROLE_MANAGER"));
			userService.guardarRol(new Rol("ROLE_ADMIN"));


			// agregando usuarios a la BD

			userService.guardarUsuario(new Usuario("pepito the user", "p3p3", "1234", "pepe@gmail.com", new ArrayList<>()));
			userService.guardarUsuario(new Usuario("lucas the manager", "luke", "1234", "luke@gmail.com", new ArrayList<>()));
			userService.guardarUsuario(new Usuario("Braian", "scarify", "1234", "braian.yo18@gmail.com", new ArrayList<>()));


			// dando roles

			userService.agregarRolAUsuario("p3p3", "ROLE_USER");
			userService.agregarRolAUsuario("luke", "ROLE_MANAGER");
			userService.agregarRolAUsuario("scarify", "ROLE_MANAGER");
			userService.agregarRolAUsuario("scarify", "ROLE_USER");
			userService.agregarRolAUsuario("scarify", "ROLE_ADMIN");


		};
	}

*/


}

