package com.alkemyProject.disneyAPI_v1.controladores;

import com.alkemyProject.disneyAPI_v1.entidades.Genero;
import com.alkemyProject.disneyAPI_v1.servicio.GeneroServicio;
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
import java.util.List;

@RestController
@RequestMapping("/genres")
public class GeneroControlador {



    @Autowired
    private GeneroServicio generoServicio;


    @GetMapping
    public ResponseEntity<List<Genero>> listarGeneros(){
        return ResponseEntity.ok( generoServicio.listarGeneros() );
    }

    @PostMapping
    public ResponseEntity<?> guardarGenero(@RequestPart @Valid Genero genero , @RequestPart( name = "file") MultipartFile imagen){

        if( !imagen.isEmpty() ){
            Path rutaRelativa = Paths.get("src//main//resources//static/images/genres");
            String rutaAbsoluta = rutaRelativa.toFile().getAbsolutePath();
            try {
                byte[] bytes = imagen.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
                Files.write(rutaCompleta , bytes);
            } catch (IOException exception) {
                exception.printStackTrace(System.out);
            }
        }
        genero.setImagen_genero( imagen.getOriginalFilename());

        generoServicio.crearGenero(genero);
        return new ResponseEntity<>( HttpStatus.CREATED);
    }





}

