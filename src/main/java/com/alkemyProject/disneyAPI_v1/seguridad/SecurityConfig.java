package com.alkemyProject.disneyAPI_v1.seguridad;

import com.alkemyProject.disneyAPI_v1.seguridad.filtros.CustomAuthenticationFilter;
import com.alkemyProject.disneyAPI_v1.seguridad.filtros.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {



    private final UserDetailsService userDetailsService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;




    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }



    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception{

        http.csrf().disable().sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS );


        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter( authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/auth/login");


        http.authorizeRequests().antMatchers("/auth/login").permitAll().
                antMatchers( HttpMethod.POST , "/auth/register").permitAll().
                antMatchers(HttpMethod.GET , "/characters").hasAnyAuthority("ROLE_USER").
                antMatchers(HttpMethod.GET , "/movies").hasAnyAuthority("ROLE_USER").
                antMatchers(HttpMethod.GET , "/movies/**").hasAnyAuthority("ROLE_ADMIN").
                antMatchers(HttpMethod.GET , "/characters/**").hasAnyAuthority("ROLE_ADMIN").
                antMatchers(HttpMethod.GET , "/genres").hasAnyAuthority("ROLE_USER").
                antMatchers(HttpMethod.GET , "/users" ).hasAnyAuthority("ROLE_ADMIN").
                antMatchers(HttpMethod.POST , "/users").hasAnyAuthority("ROLE_ADMIN").
                antMatchers(HttpMethod.POST , "/users/**").hasAnyAuthority("ROLE_ADMIN" , "ROLE_MANAGER").
                antMatchers(HttpMethod.POST , "/genres").hasAnyAuthority("ROLE_MANAGER").
                antMatchers(HttpMethod.POST , "/characters").hasAnyAuthority("ROLE_MANAGER").
                antMatchers(HttpMethod.POST , "/movies").hasAnyAuthority("ROLE_MANAGER").
                antMatchers( HttpMethod.POST , "/movies/**").hasAnyAuthority("ROLE_MANAGER").
                antMatchers( HttpMethod.PUT , "/movies/**" ).hasAnyAuthority("ROLE_MANAGER").
                antMatchers( HttpMethod.PUT , "/characters").hasAnyAuthority("ROLE_MANAGER").
                antMatchers(HttpMethod.DELETE , "/movies" , "/movies/**").hasAnyAuthority("ROLE_MANAGER").
                antMatchers(HttpMethod.DELETE , "/characters").hasAnyAuthority("ROLE_MANAGER");

        http.authorizeRequests().anyRequest().authenticated();

        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore( new CustomAuthorizationFilter() , UsernamePasswordAuthenticationFilter.class);
    }



}

