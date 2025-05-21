package com.evento.servicioEvento.dto;

import lombok.Data;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;

@Data
public class EventCreateDTO {
    @NotNull
    private String titulo;

    private String descripcion;

    @NotNull
    private String ubicacion;

    @NotNull
    private LocalDateTime fechaInicio;

    @NotNull
    private LocalDateTime fechaFin;

    @NotNull
    private Long usuarioId;
}
