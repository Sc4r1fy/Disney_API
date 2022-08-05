package com.alkemyProject.disneyAPI_v1.servicio;

import com.alkemyProject.disneyAPI_v1.entidades.Rol;
import com.alkemyProject.disneyAPI_v1.entidades.Usuario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface UsuarioServicio {

    Usuario guardarUsuario(Usuario user);

    Rol guardarRol(Rol role);

    void agregarRolAUsuario(String username , String nombre_rol);

    Usuario getUsuario(String username);

    List<Usuario> getUsarios();

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

    Usuario guardarUsuarioLimitado(Usuario usuario);
}
