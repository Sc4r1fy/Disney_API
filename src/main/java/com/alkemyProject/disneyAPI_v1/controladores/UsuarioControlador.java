package com.alkemyProject.disneyAPI_v1.controladores;

import com.alkemyProject.disneyAPI_v1.DTO.UsuarioRolDTO;
import com.alkemyProject.disneyAPI_v1.entidades.Rol;
import com.alkemyProject.disneyAPI_v1.entidades.Usuario;
import com.alkemyProject.disneyAPI_v1.servicio.UsuarioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UsuarioControlador {

    @Autowired
    UsuarioServicio usuarioServicio;


    @GetMapping("/users")
    public ResponseEntity<List<Usuario>> getUsuarios() {
        return ResponseEntity.ok().body(usuarioServicio.getUsarios());
    }

    @PostMapping("/auth/register")
    public ResponseEntity<Usuario> saveUsuario(@RequestBody Usuario usuario) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/users").toUriString());
        return ResponseEntity.created(uri).body(usuarioServicio.guardarUsuarioLimitado(usuario));
    }

    @PostMapping("/roles")
    public ResponseEntity<Rol> guardarRol(@RequestBody Rol rol) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/roles").toUriString());
        return ResponseEntity.created(uri).body(usuarioServicio.guardarRol(rol));
    }


    @PostMapping("/roles/addRoleToUser")
    public ResponseEntity<?> addRolToUsuario(@RequestBody UsuarioRolDTO usuarioRolDTO) {
        usuarioServicio.agregarRolAUsuario(usuarioRolDTO.getUsername(), usuarioRolDTO.getRoleName());
        return ResponseEntity.ok().build();
    }


    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
        usuarioServicio.refreshToken(request, response);


    }


}