package com.alkemyProject.disneyAPI_v1.servicio;

import com.alkemyProject.disneyAPI_v1.entidades.Genero;

import java.util.List;

public interface GeneroServicio {

    void crearGenero(Genero genero);

    Genero buscarGeneroPorNombre(String nombreGenero);

    void eliminarGenero(Integer id);

    List<Genero> listarGeneros();





}
