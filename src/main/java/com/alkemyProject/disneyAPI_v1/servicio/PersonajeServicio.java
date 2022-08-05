package com.alkemyProject.disneyAPI_v1.servicio;

import com.alkemyProject.disneyAPI_v1.entidades.Personaje;
import com.alkemyProject.disneyAPI_v1.DTO.PersonajeResponseDTO;

import java.util.List;


public interface PersonajeServicio {


    Personaje guardarPersonaje(Personaje personaje);

    void eliminarPersonaje(Integer id);

    Personaje buscarPorId(Integer id);

    PersonajeResponseDTO buscarPorNombre(String nombrePersonaje);

    List<PersonajeResponseDTO> personajesPorEdad(Integer edad);

    List<PersonajeResponseDTO> personajesPorpeso(Double peso);

    List<PersonajeResponseDTO> personajesPorPelicula(Integer peliculaId);

    List<PersonajeResponseDTO> listarPersonajes();

    Personaje modificarPersonaje(Integer personajeId , Personaje personaje);
}
