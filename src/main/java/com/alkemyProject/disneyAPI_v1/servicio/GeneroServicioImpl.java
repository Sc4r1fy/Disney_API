package com.alkemyProject.disneyAPI_v1.servicio;

import com.alkemyProject.disneyAPI_v1.entidades.Genero;
import com.alkemyProject.disneyAPI_v1.repositorios.GeneroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneroServicioImpl implements GeneroServicio{

    @Autowired
    GeneroRepositorio generoRepositorio;

    @Override
    public void crearGenero(Genero genero) {
        generoRepositorio.save(genero);
    }

    @Override
    public Genero buscarGeneroPorNombre(String nombreGenero) {
       return generoRepositorio.findByNombre(nombreGenero).orElseThrow();
    }

    @Override
    public void eliminarGenero(Integer id) {
        generoRepositorio.deleteById(id);
    }

    @Override
    public List<Genero> listarGeneros() {
        return generoRepositorio.findAll();
    }


}
