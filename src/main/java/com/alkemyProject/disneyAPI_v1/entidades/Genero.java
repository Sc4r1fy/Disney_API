package com.alkemyProject.disneyAPI_v1.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table( name = "generos")
@Data
public class Genero  {

    // atributos

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    private String nombre;

    private String imagen_genero;

    @JsonIgnore
    @OneToMany( mappedBy = "genero")
    private List<Pelicula> peliculas = new ArrayList<>();

}
