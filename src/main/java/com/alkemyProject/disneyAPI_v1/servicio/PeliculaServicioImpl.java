package com.alkemyProject.disneyAPI_v1.servicio;

import com.alkemyProject.disneyAPI_v1.DTO.PeliculaResponseDTO;
import com.alkemyProject.disneyAPI_v1.Excepciones.ResourceNotFoundException;
import com.alkemyProject.disneyAPI_v1.entidades.Genero;
import com.alkemyProject.disneyAPI_v1.entidades.Pelicula;
import com.alkemyProject.disneyAPI_v1.entidades.Personaje;
import com.alkemyProject.disneyAPI_v1.repositorios.GeneroRepositorio;
import com.alkemyProject.disneyAPI_v1.repositorios.PeliculaRepositorio;
import com.alkemyProject.disneyAPI_v1.repositorios.PersonajeRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PeliculaServicioImpl implements PeliculaServicio {

    @Autowired
    private PeliculaRepositorio peliculaRepositorio;

    @Autowired
    private GeneroRepositorio generoRepositorio;

    @Autowired
    private PersonajeRepositorio personajeRepositorio;

    @Autowired
    private ModelMapper modelMapper;




    // checked
    @Override
    public Pelicula guardarPelicula(Pelicula pelicula , Integer idGenre) {
        pelicula.setGenero( generoRepositorio.findById(idGenre).orElseThrow());
        return peliculaRepositorio.save(pelicula);
    }

    //chequed
    @Override
    public void eliminarPelicula(Integer id) {
        peliculaRepositorio.deleteById(id);
    }


    // ---------------------------------------------------------------------------------------------------------

    // Método auxiliar para evitar boiler code.

    private List<PeliculaResponseDTO> peliculasToDTO( List<Pelicula> peliculas){
        List<PeliculaResponseDTO> peliculaResponseDTOList = new ArrayList<>();
        for( Pelicula p : peliculas ){
            PeliculaResponseDTO peliculaResponseDTO = modelMapper.map( p , PeliculaResponseDTO.class );
            peliculaResponseDTOList.add(peliculaResponseDTO);
        }
        return peliculaResponseDTOList;
    }

    //-----------------------------------------------------------------------------------------------------------



    // chequeado
    @Override
    public PeliculaResponseDTO buscarPorTitulo(String tituloPelicula) {
        Pelicula pelicula =  peliculaRepositorio.findByTitulo(tituloPelicula);
        PeliculaResponseDTO peliculaResponseDTO = modelMapper.map( pelicula , PeliculaResponseDTO.class );
        return peliculaResponseDTO;
    }

    // chequeado
    @Override
    public List<PeliculaResponseDTO> peliculasPorGenero(Integer idGenero) {
        Optional<Genero> generoOpcional = generoRepositorio.findById(idGenero);

        if ( !generoOpcional.isPresent() ) {
            throw new RuntimeException("error al buscar el género con el id especificado : " + idGenero );
        }
        List<Pelicula> peliculas = peliculaRepositorio.peliculasPorGenero(idGenero);

        return peliculasToDTO(peliculas);
    }




    // chequeado
    @Override
    public List<PeliculaResponseDTO> listarPeliculas() {
        List<Pelicula> peliculas = peliculaRepositorio.obtenerPeliculas();
        return peliculasToDTO(peliculas);
    }

    @Override
    public Optional<Pelicula> obtenerPeliculaPorId(Integer peliculaId) {
        Optional<Pelicula> peliculaOpcional = peliculaRepositorio.findById(peliculaId);

        if( !peliculaOpcional.isPresent() ){
            throw new RuntimeException("error al buscar la película con el id especificado : " + peliculaId);
        }
        return peliculaRepositorio.findById(peliculaId);
    }


    @Override
    public List<PeliculaResponseDTO> peliculasPorOrdenASC() {
        List<Pelicula> peliculas =  peliculaRepositorio.peliculasPorOrdenASC();
        return peliculasToDTO(peliculas);
    }

    @Override
    public List<PeliculaResponseDTO> peliculasPorOrdenDESC() {
        List<Pelicula> peliculas = peliculaRepositorio.peliculasPorOrdenDESC();
        return peliculasToDTO(peliculas);
    }

    @Override
    public void añadirPersonajeApelicula(Integer idMovie, Integer idCharacter) {

        if( peliculaRepositorio.findById(idMovie).isPresent() ){
            if(personajeRepositorio.findById(idCharacter).isPresent()){
                Personaje personajeDB = personajeRepositorio.findById(idCharacter).get();
                Pelicula peliculaDB = peliculaRepositorio.findById(idMovie).get();
                peliculaDB.getPersonajes().add(personajeDB);
                System.out.println( "La cantidad de pjs en la pelicula es " + peliculaDB.getPersonajes().size());

                peliculaRepositorio.save(peliculaDB);
            } else {
                throw new ResourceNotFoundException("No se encontró el personaje con id : " + idCharacter);
            }
        } else {
            throw new ResourceNotFoundException("No se encontró la pelicula con id : " + idMovie );
        }
    }

    @Override
    public void eliminarPersonajeDePelicula(Integer idMovie, Integer idCharacter) {

        if( peliculaRepositorio.findById(idMovie).isPresent() ){
            if(personajeRepositorio.findById(idCharacter).isPresent()){
                Personaje personajeDB = personajeRepositorio.findById(idCharacter).get();
                Pelicula peliculaDB = peliculaRepositorio.findById(idMovie).get();
                peliculaDB.getPersonajes().remove(personajeDB);

                peliculaRepositorio.save(peliculaDB);
            } else {
                throw new ResourceNotFoundException("No se encontró el personaje con id : " + idCharacter);
            }
        } else {
            throw new ResourceNotFoundException("No se encontró la pelicula con id : " + idMovie );
        }
    }



    }


