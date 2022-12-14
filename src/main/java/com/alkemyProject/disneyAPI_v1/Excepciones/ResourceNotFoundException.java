package com.alkemyProject.disneyAPI_v1.Excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( code = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{


    public ResourceNotFoundException(){}

    public ResourceNotFoundException(String mensaje){
        super(mensaje);
    }

    public ResourceNotFoundException(String mensaje , Throwable error){
        super( mensaje , error);
    }

}
