package com.alkemyProject.disneyAPI_v1;

import ch.qos.logback.classic.pattern.DateConverter;
import com.alkemyProject.disneyAPI_v1.entidades.Genero;
import com.alkemyProject.disneyAPI_v1.entidades.Pelicula;
import com.alkemyProject.disneyAPI_v1.entidades.Personaje;
import com.alkemyProject.disneyAPI_v1.repositorios.GeneroRepositorio;
import com.alkemyProject.disneyAPI_v1.repositorios.PeliculaRepositorio;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.sql.Array;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase( replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PeliculaTest {

    @Autowired
    private PeliculaRepositorio peliculaRepositorio;

    @Autowired
    private GeneroRepositorio generoRepositorio;


    @Test
    @Rollback( value = false)
    @Order(1)
    void testGuardarPelicula(){



        Pelicula pelicula_1 = new Pelicula();
        Pelicula pelicula_2 = new Pelicula();

        pelicula_1.setTitulo("shrek 1");
        pelicula_1.setCalificacion(4);

        Genero genero = new Genero();
        genero.setNombre("comedia");
        genero.setImagen_genero("tumbrl");
        generoRepositorio.save(genero);

        pelicula_1.setGenero(genero);
        pelicula_1.setFecha_creacion(LocalDate.of(2006,10,15));
        pelicula_1.setPersonajes( new ArrayList<>());
        pelicula_1.setImagen("/tumbrl");

        // guardando pelicula_2

        peliculaRepositorio.save(pelicula_1);


        pelicula_2.setTitulo("shrek 2");
        pelicula_2.setCalificacion(3);


        pelicula_2.setGenero(genero);
        pelicula_2.setFecha_creacion(LocalDate.of(2008,10,05));
        pelicula_2.setImagen("/tumbrl");
        pelicula_2.setPersonajes(new ArrayList<>());
        peliculaRepositorio.save(pelicula_2);

        System.out.println(pelicula_1);
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println(pelicula_2);



    }



    @Test
    @Order(2)
    void testBuscarPelicula(){

        Pelicula p_1 = peliculaRepositorio.findByTitulo("shrek 1");
        Pelicula p_2 = peliculaRepositorio.findByTitulo("shrek 2");

        // buscar pelicula/serie existente

        Assertions.assertNotNull(p_1);
        Assertions.assertNotNull(p_2);

        Assertions.assertTrue( p_1.equals( peliculaRepositorio.findByTitulo("shrek 1") )  );
        Assertions.assertTrue( p_2.equals( peliculaRepositorio.findByTitulo("shrek 2")));


        // buscar pelicula no existente

        Assertions.assertNull(peliculaRepositorio.findByTitulo("Terminator"));


    }


    @Test
    @Order(3)
    public void actualizarPelicula(){

        Pelicula p_1 = peliculaRepositorio.findByTitulo("shrek 1");

        p_1.setCalificacion(1);
        p_1.setImagen("/imgs");

        peliculaRepositorio.save(p_1);

        assertEquals( peliculaRepositorio.findByTitulo("shrek 1").getCalificacion() , 1);
        assertEquals( peliculaRepositorio.findByTitulo("shrek 1").getImagen() , "/imgs");
        System.out.println("datos actualizados de shrek 1 : ");

    }


    @Test
    @Order(4)
    public void listarPeliculas(){
        List<Pelicula> peliculas = peliculaRepositorio.obtenerPeliculas();

        System.out.println("Lista de peliculas/series :");


        assertEquals(peliculas.size(), 2);
    }


    @Test
    @Order(5)
    @Rollback( value = false)
    public void eliminarPeliculas(){

        Pelicula p_1 = peliculaRepositorio.findByTitulo("shrek 1");
        Pelicula p_2 = peliculaRepositorio.findByTitulo("shrek 2");

        peliculaRepositorio.deleteById( p_1.getId() );
        peliculaRepositorio.deleteById( p_2.getId() );

        Assertions.assertNull( peliculaRepositorio.findByTitulo("shrek 1"));
        Assertions.assertNull( peliculaRepositorio.findByTitulo("shrek 2"));
    }


}
