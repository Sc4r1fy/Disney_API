package com.alkemyProject.disneyAPI_v1;

import com.alkemyProject.disneyAPI_v1.entidades.Personaje;
import com.alkemyProject.disneyAPI_v1.repositorios.PersonajeRepositorio;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@DataJpaTest
@AutoConfigureTestDatabase( replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonajeTest {

    @Autowired
   private PersonajeRepositorio personajeRepositorio;



    @Test
    @Rollback(value = false)
    @Order(1)
    public void testGuardarPersonaje(){


        Personaje personaje = new Personaje();
        personaje.setImagen("tumrbl");
        personaje.setNombre("shrek");
        personaje.setEdad(30);
        personaje.setPeso(350.0);
        personaje.setHistoria("shrek es un ogro gracioso");
        personaje.setPeliculas( new ArrayList<>());



        Personaje pjSaved = personajeRepositorio.save(personaje);

        // agregando personaje 2 :

        Personaje personaje2 = new Personaje();
        personaje2.setImagen("tumrbl");
        personaje2.setNombre("fiona");
        personaje2.setEdad(25);
        personaje2.setPeso(200.0);
        personaje2.setHistoria("Fiona renunció a su humanidad");
        personaje2.setPeliculas( new ArrayList<>());



        Personaje pjSaved2 = personajeRepositorio.save(personaje2);

        assertEquals( personajeRepositorio.buscarPorNombre("shrek").getNombre() , "shrek");
        assertEquals( personajeRepositorio.buscarPorNombre("fiona").getNombre() , "fiona");


        System.out.println(personajeRepositorio.buscarPorNombre("shrek"));
        System.out.println( personajeRepositorio.buscarPorNombre("fiona"));
    }

    @Test
    @Order(2)
    public void buscarPersonaje(){

        Personaje p_1 = personajeRepositorio.findByNombre("shrek");
        Personaje p_2 = personajeRepositorio.findByNombre("fiona");

        // buscar personaje existente

        Assertions.assertNotNull(p_1);
        Assertions.assertNotNull(p_2);

        // buscar personaje no existente

        Assertions.assertNull(personajeRepositorio.findByNombre("luke"));
    }

    @Test
    @Order(3)
    public void actualizarPersonaje(){

        Personaje p_1 = personajeRepositorio.findByNombre("shrek");
        p_1.setPeso(550.0);
        p_1.setEdad(31);
        personajeRepositorio.save(p_1);

        assertEquals( personajeRepositorio.findByNombre("shrek").getPeso() , 550.0);
        assertEquals( personajeRepositorio.findByNombre("shrek").getEdad() , 31);
        System.out.println("datos actualizados de shrek : ");
        System.out.println(personajeRepositorio.findByNombre("shrek"));

    }

    @Test
    @Order(4)
    public void listarPersonajes(){
        List<Personaje> personajes = personajeRepositorio.findAll();

        System.out.println(personajes);

        assertEquals(personajes.size(), 2);
    }


    @Test
    @Order(5)
    @Rollback( value = false)
    public void eliminarPersonaje(){

        Personaje p_1 = personajeRepositorio.findByNombre("shrek");
        Personaje p_2 = personajeRepositorio.findByNombre("fiona");

        personajeRepositorio.deleteById( p_1.getId() );
        personajeRepositorio.deleteById( p_2.getId() );

        Assertions.assertNull( personajeRepositorio.findByNombre("shrek"));
        Assertions.assertNull( personajeRepositorio.findByNombre("fiona"));
    }





}
