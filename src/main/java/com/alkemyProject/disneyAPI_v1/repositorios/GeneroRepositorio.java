package com.alkemyProject.disneyAPI_v1.repositorios;

import com.alkemyProject.disneyAPI_v1.entidades.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GeneroRepositorio extends JpaRepository<Genero , Integer> {


    Optional<Genero> findByNombre(String nombre);




}
