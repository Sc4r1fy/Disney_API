package com.alkemyProject.disneyAPI_v1.servicio;

import com.alkemyProject.disneyAPI_v1.DTO.PeliculaResponseDTO;
import com.alkemyProject.disneyAPI_v1.entidades.Pelicula;

import java.util.List;
import java.util.Optional;

public interface PeliculaServicio {


    Pelicula guardarPelicula(Pelicula pelicula , Integer idGenre);

    void eliminarPelicula(Integer id);

    PeliculaResponseDTO buscarPorTitulo(String tituloPelicula);

    List<PeliculaResponseDTO> peliculasPorGenero(Integer idGenero);

    List<PeliculaResponseDTO> listarPeliculas();

    Optional<Pelicula> obtenerPeliculaPorId(Integer peliculaId);

    List<PeliculaResponseDTO> peliculasPorOrdenASC();

    List<PeliculaResponseDTO> peliculasPorOrdenDESC();

    void añadirPersonajeApelicula(Integer idMovie , Integer idCharacter);

    void eliminarPersonajeDePelicula(Integer idMovie , Integer idCharacter);
}
