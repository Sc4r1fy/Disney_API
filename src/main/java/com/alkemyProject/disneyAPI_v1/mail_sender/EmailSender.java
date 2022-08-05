package com.alkemyProject.disneyAPI_v1.mail_sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {


    @Autowired
    JavaMailSender javaMailSender;


    public void enviarMailBienvenida(String destino , String nombre){

        SimpleMailMessage mensaje = new SimpleMailMessage();

        mensaje.setFrom("brai.email.sender@gmail.com");
        mensaje.setTo(destino);
        mensaje.setText("Bienvenido " + nombre + " a la API de disney. Ahora podés ver el catálogo entero de nuestras series/peliculas");
        mensaje.setSubject("¡Registro exitoso, Disney API!");


        javaMailSender.send(mensaje);
        System.out.println("mail enviado");
    }




}
