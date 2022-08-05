package com.alkemyProject.disneyAPI_v1.servicio;

import com.alkemyProject.disneyAPI_v1.entidades.Personaje;
import com.alkemyProject.disneyAPI_v1.DTO.PersonajeResponseDTO;
import com.alkemyProject.disneyAPI_v1.repositorios.PersonajeRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonajeServicioImpl implements PersonajeServicio{

    @Autowired
    private PersonajeRepositorio personajeRepositorio;

    @Autowired
    private ModelMapper modelMapper;



    // chequeado, cumple con la consigna
    @Override
    public Personaje guardarPersonaje(Personaje personaje) {
        return personajeRepositorio.save(personaje);
    }

    // chequeado, cumple.
    @Override
    public void eliminarPersonaje(Integer id) {
        personajeRepositorio.deleteById(id);
    }


    // ------------------------------------------------------------------------------------------------------------

    private List<PersonajeResponseDTO> personajesToDTO( List<Personaje> personajes ){
        List<PersonajeResponseDTO> personajeResponseDTOList = new ArrayList<>();

        for( Personaje p : personajes){
            PersonajeResponseDTO personajeResponseDTO = modelMapper.map( p , PersonajeResponseDTO.class);
            personajeResponseDTOList.add(  personajeResponseDTO );
        }
        return personajeResponseDTOList;
    }

    //---------------------------------------------------------------------------------------------------------------



    // no hay restriccion acá. chequeado
    @Override
    public Personaje buscarPorId(Integer id) {
        return personajeRepositorio.findById(id).orElseThrow();
    }

    // debemos devolver un DTO para cumplir con el requisito. Chequeado
    @Override
    public PersonajeResponseDTO buscarPorNombre(String nombrePersonaje) {
        Personaje personaje =  personajeRepositorio.findByNombre(nombrePersonaje);
        PersonajeResponseDTO personajeResponseDTO =  modelMapper.map( personaje , PersonajeResponseDTO.class);
        return personajeResponseDTO;
    }



    // chequeado
    @Override
    public List<PersonajeResponseDTO> personajesPorEdad(Integer edad) {

        List<Personaje> personajes = personajeRepositorio.personajesPorEdad(edad);
        return personajesToDTO(personajes);
    }


    // chequeado, cumple requisitos.
    @Override
    public List<PersonajeResponseDTO> personajesPorpeso(Double peso) {

        List<Personaje> personajes = personajeRepositorio.personajesPorPeso(peso);
        return personajesToDTO(personajes);
    }


    // chequeadisimo
    @Override
    public List<PersonajeResponseDTO> personajesPorPelicula(Integer peliculaId) {

        List<Personaje> personajes = personajeRepositorio.personajesPorPelicula(peliculaId);
        return personajesToDTO(personajes);
    }

    // chequeado
    @Override
    public List<PersonajeResponseDTO> listarPersonajes() {
        List<Personaje> personajes = personajeRepositorio.findAll();
        return personajesToDTO(personajes);
    }

    @Override
    public Personaje modificarPersonaje(Integer personajeId, Personaje personaje) {

        Personaje personaje_existente = personajeRepositorio.findById(personajeId).orElseThrow( () -> new RuntimeException("id no encontrado"));

        personaje.setId(personajeId);
        personaje.setPeliculas( personaje_existente.getPeliculas());
        return personajeRepositorio.save(personaje);



    }


}
