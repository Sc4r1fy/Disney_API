package com.alkemyProject.disneyAPI_v1.controladores;

import com.alkemyProject.disneyAPI_v1.entidades.Personaje;
import com.alkemyProject.disneyAPI_v1.servicio.PeliculaServicio;
import com.alkemyProject.disneyAPI_v1.servicio.PersonajeServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/characters")
public class PersonajeControlador {

    @Autowired
    PersonajeServicio personajeServicio;

// ----------------------------------------------------------------------------------------------




    private void upload(MultipartFile file){
        Path rutaRelativa = Paths.get("src//main//resources//static/images/characters");
        String rutaAbsoluta = rutaRelativa.toFile().getAbsolutePath();
        try {
            byte[] bytes = file.getBytes();
            Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + file.getOriginalFilename());
            Files.write(rutaCompleta , bytes);
        } catch (IOException exception) {
            exception.printStackTrace(System.out);
        }
    }


    private void borrarImagen(Integer personajeId){
        String imagenPersonaje = personajeServicio.buscarPorId(personajeId).getImagen();
        Path rutaRelativa = Paths.get("src//main//resources//static/images/characters");
        String rutaAbsoluta = rutaRelativa.toFile().getAbsolutePath();
        Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagenPersonaje);
        try {
            Files.deleteIfExists(rutaCompleta);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
// ---------------------------------------------------------------------------------------------------

    // chequeado i believe
    @PostMapping
    public ResponseEntity<?> guardarPersonaje(@RequestPart(name = "personaje") @Valid Personaje personaje , @RequestPart(name = "file") MultipartFile imagen){

        if( !imagen.isEmpty() ) {
        upload( imagen );
        personaje.setImagen(imagen.getOriginalFilename());
        }

        return new ResponseEntity<>(personajeServicio.guardarPersonaje(personaje) , HttpStatus.CREATED);
    }


    //chequeado
    @GetMapping
    public ResponseEntity<?> listarPersonajes(@RequestParam(required = false) String name,
                                              @RequestParam(required = false) String edad,
                                              @RequestParam(required = false) String idMovie){

        // Si parámetro name es no nulo :
        if( name != null){
            if( personajeServicio.buscarPorNombre(name) != null) {
                return new ResponseEntity<>(personajeServicio.buscarPorNombre(name), HttpStatus.OK);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        // si parámetro edad es no nulo :

        if( edad != null ){
            return new ResponseEntity<>( personajeServicio.personajesPorEdad( Integer.parseInt(edad) ) ,HttpStatus.OK);
        }

        // si idMovie es no nulo:
        if( idMovie != null){
            if( personajeServicio.personajesPorPelicula( Integer.parseInt(idMovie)) != null ) {
                return new ResponseEntity<>(personajeServicio.personajesPorPelicula(Integer.parseInt(idMovie)), HttpStatus.OK);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        // no se recibió una query parameter :
        return new ResponseEntity<>( personajeServicio.listarPersonajes() , HttpStatus.OK);

        }


        //chequeado
        @GetMapping("/{personajeId}")
        public ResponseEntity<?> PersonajeDetails(@PathVariable Integer personajeId){
        if( personajeServicio.buscarPorId(personajeId) != null){
            return new ResponseEntity<>( personajeServicio.buscarPorId(personajeId), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
        }




        // chequeado
        @DeleteMapping("/{personajeId}")
        public ResponseEntity<?> eliminarPersonaje(@PathVariable int personajeId){
         if( personajeServicio.buscarPorId(personajeId) != null){
             borrarImagen(personajeId);
             personajeServicio.eliminarPersonaje(personajeId);
             return ResponseEntity.ok().build();
         } else {
             return ResponseEntity.notFound().build();
         }
        }



        // chequeado y testeado a medias
        @PutMapping("/{personajeId}")
        public ResponseEntity<?> modificarPersonaje(@PathVariable Integer personajeId , @RequestPart @Valid Personaje personaje ,
                                                    @RequestPart( required = false , name = "file") MultipartFile imagen){


        if( imagen != null ){
            borrarImagen(personajeId);
            upload(imagen);
            personaje.setImagen(imagen.getOriginalFilename());
        }
        if( personajeServicio.buscarPorId(personajeId) != null){
            return new ResponseEntity<>( personajeServicio.modificarPersonaje(personajeId , personaje) , HttpStatus.OK );
        } else {
            return ResponseEntity.notFound().build();
        }
        }






    }













