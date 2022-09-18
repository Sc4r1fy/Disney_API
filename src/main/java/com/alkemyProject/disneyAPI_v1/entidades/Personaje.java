package com.alkemyProject.disneyAPI_v1.entidades;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "personajes")
@Data
public class Personaje {

    // atributos


    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "personaje_id")
    private int id;

    private String imagen;

    @NotEmpty(message = "el campo nombre no puede estar vacío")
    private String nombre;

    @Range(min = 0 , message = "la edad debe ser un número entero, es decir mayor o igual a cero")
    private int edad;

    @Positive
    private Double peso;

    @NotNull
    private String historia;


    @JsonIgnoreProperties("personajes")
    @ManyToMany( mappedBy = "personajes" , cascade = CascadeType.MERGE)
    private List<Pelicula> peliculas = new ArrayList<>();



}
