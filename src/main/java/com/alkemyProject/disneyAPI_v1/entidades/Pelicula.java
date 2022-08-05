package com.alkemyProject.disneyAPI_v1.entidades;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@Entity
@Table( name = "peliculas_series")
public class Pelicula {

    // atributos

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pelicula_id")
    private int id;

    private String imagen;

    @Size( max = 70)
    @NotEmpty(message = "El campo título no puede estar vacío")
    @Column( unique = true )
    private String titulo;

    //@JsonFormat( pattern = "YYYY-MM-dd")
    @DateTimeFormat
    private LocalDate fecha_creacion;

    @Range( min = 1 , max = 5 )
    private int calificacion;



   // @ManyToMany( mappedBy = "peliculas" , fetch = FetchType.EAGER , cascade = CascadeType.MERGE)
    @JsonIgnoreProperties("peliculas")
    @JoinTable( name = "personajes_peliculas" ,
                joinColumns = @JoinColumn( name = "pelicula_id") ,
                inverseJoinColumns = @JoinColumn( name = "personaje_id") )
    @ManyToMany
    private List<Personaje> personajes = new ArrayList<>();

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn( name = "genero_id")
    private Genero genero;





}
