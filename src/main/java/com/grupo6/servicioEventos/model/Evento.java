package com.grupo6.servicioEventos.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "eventos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(nullable = false, length = 500)
    private String descripcion;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "America/Santiago")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "America/Santiago")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;

    @Column(nullable = false, length = 100)
    private String ubicacion;

    @Column(nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Santiago")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @Column(nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Santiago")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacion;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.fechaActualizacion = new Date();
    }
}

