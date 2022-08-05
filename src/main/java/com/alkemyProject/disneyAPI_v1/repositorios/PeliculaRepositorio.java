package com.alkemyProject.disneyAPI_v1.repositorios;


import com.alkemyProject.disneyAPI_v1.entidades.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PeliculaRepositorio extends JpaRepository<Pelicula , Integer> {

    @Query(value = "SELECT * FROM peliculas_series" , nativeQuery = true)
    List<Pelicula> obtenerPeliculas();

    @Query( value = "SELECT * FROM peliculas_series" +
            " WHERE peliculas_series.genero_id LIKE %:generoId%"
            , nativeQuery = true)
    List<Pelicula> peliculasPorGenero(@Param("generoId") Integer generoId);

    @Query( value = "SELECT * FROM peliculas_series" +
            " WHERE peliculas_series.titulo LIKE %:titulo%" , nativeQuery = true)
    Pelicula findByTitulo(@Param("titulo") String titulo);

    @Query( value = "SELECT * FROM peliculas_series ORDER BY fecha_creacion ASC" , nativeQuery = true)
    List<Pelicula> peliculasPorOrdenASC();

    @Query( value = "SELECT * FROM peliculas_series ORDER BY fecha_creacion DESC" , nativeQuery = true)
    List<Pelicula> peliculasPorOrdenDESC();




}
