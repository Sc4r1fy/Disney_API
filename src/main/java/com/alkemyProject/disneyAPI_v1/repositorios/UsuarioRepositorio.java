package com.alkemyProject.disneyAPI_v1.repositorios;

import com.alkemyProject.disneyAPI_v1.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepositorio extends JpaRepository<Usuario ,Long> {

    Optional<Usuario> findByUsername(String username);
}
