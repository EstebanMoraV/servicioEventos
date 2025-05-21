package com.evento.servicioEvento.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.evento.servicioEvento.model.Evento;
import com.evento.servicioEvento.service.EventoService;

@RestController
@RequestMapping("/api/v1/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @GetMapping
    public ResponseEntity<List<Evento>> getAllEventos() {
        List<Evento> eventos = eventoService.findAll();
        if (eventos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Si la lista está vacía, devuelve un código 204 No Content
        }
        return new ResponseEntity<>(eventos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evento> getEventoById(@PathVariable Long id) {
        Optional<Evento> evento = eventoService.findById(id);
        return evento
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Evento> createEvento(@RequestBody Evento evento) {
        try {
            Evento createdEvento = eventoService.save(evento);
            return new ResponseEntity<>(createdEvento, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evento> updateEvento(@PathVariable Long id, @RequestBody Evento evento) {
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
    public ResponseEntity<HttpStatus> deleteEvento(@PathVariable Long id) {
        try {
            eventoService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
