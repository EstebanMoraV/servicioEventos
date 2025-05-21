package com.evento.servicioEvento.dto;

import lombok.*;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventUpdateDTO {
    
    @NotBlank
    private String titulo;

    private String descripcion;

    @NotBlank
    private String ubicacion;

    @NotNull
    private LocalDateTime fechaInicio; 

    @NotNull
    private LocalDateTime fechaFin;
}
