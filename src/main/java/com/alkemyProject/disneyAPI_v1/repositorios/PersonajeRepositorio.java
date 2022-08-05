package com.alkemyProject.disneyAPI_v1.repositorios;

import com.alkemyProject.disneyAPI_v1.entidades.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PersonajeRepositorio extends JpaRepository<Personaje , Integer> {

     Personaje findByNombre(String nombre);


     @Query( value = "SELECT * FROM personajes WHERE personajes.nombre LIKE %:nombre% " , nativeQuery = true)
     Personaje buscarPorNombre(@Param("nombre") String nombre);

     @Query( value = "SELECT * FROM personajes WHERE personajes.edad LIKE %:edad%" , nativeQuery = true)
     List<Personaje> personajesPorEdad(@Param("edad") int edad);

     @Query( value = "SELECT * FROM personajes WHERE personajes.peso LIKE %:peso% " , nativeQuery = true)
     List<Personaje> personajesPorPeso(@Param("peso") Double peso);


     @Query( value = "SELECT * FROM personajes P" +
             "LEFT JOIN personajes_peliculas T" +
             " ON P.personaje_id = T.personaje_id" +
             " WHERE T.pelicula_id = %:peliculaId%"
             , nativeQuery = true)
     List<Personaje> personajesPorPelicula(@Param("peliculaId") int id);


}
