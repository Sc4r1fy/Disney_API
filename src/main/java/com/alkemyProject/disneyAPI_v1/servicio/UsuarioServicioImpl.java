package com.alkemyProject.disneyAPI_v1.servicio;

import com.alkemyProject.disneyAPI_v1.Excepciones.ResourceNotFoundException;
import com.alkemyProject.disneyAPI_v1.entidades.Rol;
import com.alkemyProject.disneyAPI_v1.entidades.Usuario;
import com.alkemyProject.disneyAPI_v1.mail_sender.EmailSender;
import com.alkemyProject.disneyAPI_v1.repositorios.RolRepositorio;
import com.alkemyProject.disneyAPI_v1.repositorios.UsuarioRepositorio;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UsuarioServicioImpl implements UsuarioServicio , UserDetailsService {

    @Autowired
    private RolRepositorio rolRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    EmailSender emailSender;



    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        usuario.setPassword( passwordEncoder.encode( usuario.getPassword()) );
        return usuarioRepositorio.save(usuario);
    }

    @Override
    public Usuario guardarUsuarioLimitado(Usuario usuario) {
        usuario.setPassword( passwordEncoder.encode(usuario.getPassword()));
        usuario.getRoles().add(rolRepositorio.findByName("ROLE_USER").get());
        Usuario usuarioGuardado =  usuarioRepositorio.save(usuario);
        emailSender.enviarMailBienvenida(usuarioGuardado.getEmail() , usuarioGuardado.getName());
        return usuarioGuardado;
    }


    @Override
    public Rol guardarRol(Rol rol) {
        return rolRepositorio.save(rol);
    }

    @Override
    public void agregarRolAUsuario(String username, String nombre_rol) {

        Usuario usuarioDB = usuarioRepositorio.findByUsername(username).orElseThrow( () -> new ResourceNotFoundException("usuario con nombre : " + username + " no encontrado") );
        Rol rolDB = rolRepositorio.findByName(nombre_rol).orElseThrow( () -> new ResourceNotFoundException("El rol : " + nombre_rol + " no se encontró en la BD"));

        usuarioDB.getRoles().add(rolDB);
    }

    @Override
    public Usuario getUsuario(String username) {
        return usuarioRepositorio.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("usuario con nombre : " + username + " no encontrado"));
    }

    @Override
    public List<Usuario> getUsarios() {
        return usuarioRepositorio.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = usuarioRepositorio.findByUsername(username).get();
        if( user == null){
            log.error("user not found in the data base");
            throw new UsernameNotFoundException("user not found in the DB");
        }else {
            log.info("user found  : {}", username);
        }
        // aca la idea es crear una coleccion de SimpleGrantedAuthority donde cada instancia de esta clase va a tener
        // un rol determinado.
        // una vez creada la coleccion, iteramos entre todos los roles que tiene este usuario y agregamos uno a uno en "authorities"
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach( role -> {
            authorities.add(new SimpleGrantedAuthority( role.getName() ));
        });
        return new org.springframework.security.core.userdetails.User( user.getUsername() , user.getPassword(), authorities);

    }



    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {


        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                Usuario user = getUsuario(username);
                String accessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000)) //Treinta minutos de vida
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Rol::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refreshToken);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e) {

                response.setHeader("error", e.getMessage());
                response.setStatus(HttpStatus.FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("falta refresh token");
        }
    }



}
