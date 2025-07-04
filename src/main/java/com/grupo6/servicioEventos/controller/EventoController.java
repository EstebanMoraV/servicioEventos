package com.grupo6.servicioEventos.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo6.servicioEventos.model.Evento;
import com.grupo6.servicioEventos.service.EventoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/eventos")
@Tag(name = "Eventos", description = "Operaciones relacionadas con eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @GetMapping
    @Operation(summary = "Obtener todos los eventos", description = "Devuelve una lista de todos los eventos registrados")
    public ResponseEntity<List<Evento>> getAllEventos() {
        List<Evento> eventos = eventoService.findAll();
        if (eventos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(eventos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener evento por ID", description = "Devuelve un evento específico por su ID")
    public ResponseEntity<Evento> getEventoById(@PathVariable Integer id) {
        Optional<Evento> evento = eventoService.findById(id);
        return evento
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping
    @Operation(summary = "Crear un nuevo evento", description = "Registra un nuevo evento en el sistema")
    public ResponseEntity<Evento> createEvento(@RequestBody Evento evento) {
        try {
            Evento createdEvento = eventoService.save(evento);
            return new ResponseEntity<>(createdEvento, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un evento", description = "Actualiza los detalles de un evento existente")
    public ResponseEntity<Evento> updateEvento(@PathVariable Integer id, @RequestBody Evento evento) {
        Optional<Evento> eventData = eventoService.findById(id);

        if (eventData.isPresent()) {
            Evento existingEvento = eventData.get();
            existingEvento.setTitulo(evento.getTitulo());
            existingEvento.setDescripcion(evento.getDescripcion());
            existingEvento.setFechaInicio(evento.getFechaInicio());
            existingEvento.setFechaFin(evento.getFechaFin());
            existingEvento.setUbicacion(evento.getUbicacion());
            // No vamos a modificar fecha de creación por obvias razones
            Evento updatedEvento = eventoService.save(existingEvento);
            return new ResponseEntity<>(updatedEvento, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un evento", description = "Elimina un evento específico por su ID")
    public ResponseEntity<HttpStatus> deleteEvento(@PathVariable Integer id) {
        try {
            eventoService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
