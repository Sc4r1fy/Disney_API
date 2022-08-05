package com.alkemyProject.disneyAPI_v1.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PeliculaResponseDTO {

    private String imagen;

    private String titulo;

    private LocalDate fecha_creacion;

}
