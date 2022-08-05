package com.alkemyProject.disneyAPI_v1.seguridad.filtros;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Arrays.stream;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if( request.getServletPath().equals( "/auth/login") || request.getServletPath().equals("api/token/refresh")){
            filterChain.doFilter( request , response);
        } else {
            // CASO EN EL QUE LA RUTA NO ES LA DE LOGIN O LA DE REFRESH TOKEN:
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if( authorizationHeader != null && authorizationHeader.startsWith("Bearer ") ){
                // CASO : AUTHORIZATION HEADER
                // vamos a asumir que en el header esta el key value Authorization que tiene la sig forma : "Bearer ****"
                // los ***+ son el token.
                try {
                    String token = authorizationHeader.substring("Bearer ".length());
                    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    String username = decodedJWT.getSubject();
                    String[] roles = decodedJWT.getClaim("roles").asArray( String.class);
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    // ahora reconstruimos la coleccion de roles en formato Collection<...
                    stream( roles ).forEach( rol -> {
                        authorities.add( new SimpleGrantedAuthority( rol ));
                    } );
                    // creamos una instancia de UsernamePas... y pasamos como parametros username y como password pasamos null ya que
                    // no lo tenemos, asumimos que ya estamos autorizados, luego pasamos la coleccion de roles como parametro tambien.
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken( username , null , authorities);
                    //seteamos authenticacion, aqui es donde le decimos a spring security : Hey, aqui tenemos al usuario, aqui esta el username, aqui estan sus roles!!
                    SecurityContextHolder.getContext().setAuthentication( authenticationToken );
                    // llamamos al filterChain y tenemos que hacer que la request continue su curso en el filtrado.
                    filterChain.doFilter( request , response);
                } catch (Exception exception){

                    log.error( "Error en el logueo : {}" , exception.getMessage() );
                    response.setHeader("error" , exception.getMessage() );
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                }

            } else {
                // CASO : NO TENEMOS EL HEADER "AUTHORIZATION"
                // simplemente dejamos pasar la request al siguiente filtro
                filterChain.doFilter( request , response);
            }
        }
    }


    }

