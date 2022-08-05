package com.alkemyProject.disneyAPI_v1.controladores;

import com.alkemyProject.disneyAPI_v1.Excepciones.ResourceNotFoundException;
import com.alkemyProject.disneyAPI_v1.entidades.Pelicula;
import com.alkemyProject.disneyAPI_v1.entidades.Personaje;
import com.alkemyProject.disneyAPI_v1.servicio.PeliculaServicio;
import org.apache.catalina.connector.Response;
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
@RequestMapping("/movies")
public class PeliculaControlador {


    @Autowired
    private PeliculaServicio peliculaServicio;





    // -------------------------------------------------------------------------------------------------------

    private void upload(MultipartFile file){
        Path rutaRelativa = Paths.get("src//main//resources//static/images/movies");
        String rutaAbsoluta = rutaRelativa.toFile().getAbsolutePath();
        try {
            byte[] bytes = file.getBytes();
            Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + file.getOriginalFilename());
            Files.write(rutaCompleta , bytes);
        } catch (IOException exception) {
            exception.printStackTrace(System.out);
        }
    }


    private void borrarImagen(Integer peliculaId){
        String imagenPelicula = peliculaServicio.obtenerPeliculaPorId(peliculaId).get().getImagen();
        Path rutaRelativa = Paths.get("src//main//resources//static/images/movies");
        String rutaAbsoluta = rutaRelativa.toFile().getAbsolutePath();
        Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagenPelicula);
        try {
            Files.deleteIfExists(rutaCompleta);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // funcion auxiliar que devuelve el nombre del parámetro no nulo

    public String getQueryParameter(String param_name, String param_genre, String param_order) {
        if (param_name != null) {
            return "name";
        }
        if (param_genre != null) {
            return "genre";
        }
        if (param_order != null) {
            return "order";
        }
        return "";
    }

    //--------------------------------------------------------------------------------------

    @PostMapping
    public ResponseEntity<?> guardarPelicula(@RequestPart(name = "pelicula") @Valid Pelicula pelicula , @RequestParam Integer idGenre ,
                                             @RequestPart( name = "file") MultipartFile imagen ) {

        if( !imagen.isEmpty() ) {
            upload(imagen);
            pelicula.setImagen( imagen.getOriginalFilename() );
        }
        return new ResponseEntity<>(peliculaServicio.guardarPelicula(pelicula , idGenre), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> listarPeliculas(@RequestParam(required = false) String name,
                                             @RequestParam(required = false) String genre,
                                             @RequestParam(required = false) String order) {

        String queryParameter = getQueryParameter(name, genre, order);

        switch (queryParameter) {
            case "name":
                return new ResponseEntity<>(peliculaServicio.buscarPorTitulo(name), HttpStatus.OK);

            case "genre":
                return new ResponseEntity<>(peliculaServicio.peliculasPorGenero(Integer.parseInt(genre)), HttpStatus.OK);

            case "order":
                if (order.equals("ASC")) {
                    return new ResponseEntity<>(peliculaServicio.peliculasPorOrdenASC(), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(peliculaServicio.peliculasPorOrdenDESC(), HttpStatus.OK);
                }

            default : return new ResponseEntity<>(peliculaServicio.listarPeliculas(), HttpStatus.OK);

        }
    }

    @PutMapping("/{idMovie}")
    public ResponseEntity<?> modificarPelicula(@PathVariable Integer idMovie , @RequestPart(name = "pelicula") @Valid Pelicula pelicula ,
                                               @RequestParam Integer idGenre ,
                                               @RequestPart(required = false , name = "file") MultipartFile imagen){


        if( peliculaServicio.obtenerPeliculaPorId(idMovie).isPresent() ){
            if( imagen != null  ){
                borrarImagen(idMovie);
                upload(imagen);
            }
            pelicula.setId(idMovie);
            Pelicula peliculaDB = peliculaServicio.obtenerPeliculaPorId(idMovie).orElseThrow();
            pelicula.setPersonajes( peliculaDB.getPersonajes() );
            pelicula.setImagen( imagen.getOriginalFilename() );
            return new ResponseEntity<>( peliculaServicio.guardarPelicula(pelicula , idGenre) , HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("{idMovie}")
    public ResponseEntity<?> eliminarPelicula(@PathVariable Integer idMovie){
        if( peliculaServicio.obtenerPeliculaPorId(idMovie).isPresent() ){
            borrarImagen(idMovie);
            peliculaServicio.eliminarPelicula(idMovie);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/{idMovie}/characters/{idCharacter}")
    public ResponseEntity<?> agregarPersonajeAPelicula(@PathVariable Integer idMovie , @PathVariable Integer idCharacter ) {

        peliculaServicio.añadirPersonajeApelicula(idMovie, idCharacter);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{idMovie}/characters/{idCharacter}")
    public ResponseEntity<?> eliminarPersonajeDePelicula(@PathVariable Integer idMovie , @PathVariable Integer idCharacter){
        try {
            peliculaServicio.eliminarPersonajeDePelicula(idMovie , idCharacter);
            return ResponseEntity.ok().build();
        } catch(Exception e){
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/{idMovie}")
    public ResponseEntity<Pelicula> peliculaDetails(@PathVariable Integer idMovie){
        if( peliculaServicio.obtenerPeliculaPorId(idMovie) != null){
            return ResponseEntity.ok( peliculaServicio.obtenerPeliculaPorId(idMovie).get() );
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    


}










