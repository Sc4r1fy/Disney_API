package com.alkemyProject.disneyAPI_v1.repositorios;

import com.alkemyProject.disneyAPI_v1.entidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepositorio extends JpaRepository<Rol, Long> {

    Optional<Rol> findByName(String name);

}
