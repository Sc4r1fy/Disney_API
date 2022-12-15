package com.alkemyProject.disneyAPI_v1.seguridad.filtros;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    // "AuthenticationManager" NO es una clase, es una interface. aca estamos aceptando cualquier clase
    // que implemente a AuthenticationManager.
    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        log.info("usuario es : {}", username);
        log.info("password es : {}", password);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken( username , password);
        // Una vez generado el token de autenticacion voy a llamar a "authenticationManager" y le voy a pasar el token para que se pueda
        // realizar la autenticacion.
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());


        String access_token = JWT.create()
                .withSubject( user.getUsername())
                .withExpiresAt( new Date( System.currentTimeMillis() + 6000 * 30 * 10)) // 30 mins jejox
                .withIssuer( request.getRequestURL().toString() )
                .withClaim( "roles" , user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        // ahora el refresh token
        // omitimos la lina de withClaim porque no necesitamos especificar de nuevo los roles
        String refresh_token = JWT.create()
                .withSubject( user.getUsername())
                .withExpiresAt( new Date( System.currentTimeMillis() + 6000 * 60 * 10))
                .withIssuer( request.getRequestURL().toString() )
                .sign(algorithm);
        response.setHeader( "access_token" , access_token);
        response.setHeader( "refresh_token" , refresh_token);



    }
}
