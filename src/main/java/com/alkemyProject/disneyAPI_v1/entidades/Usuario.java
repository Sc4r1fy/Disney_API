package com.alkemyProject.disneyAPI_v1.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @Column( unique = true)
    private String username;

    private String password;

    @Column( unique = true)
    private String email;

    // agrego fetch de tipo EAGER porque cuando cargo un usuario quiero cargar a su vez todos sus roles asociados
    @ManyToMany(fetch = FetchType.EAGER )
    private Collection<Rol> roles = new ArrayList<>();


    public Usuario(String name, String username, String password , String email ,Collection<Rol> roles) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }



}


