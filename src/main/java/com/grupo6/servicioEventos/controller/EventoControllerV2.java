package com.grupo6.servicioEventos.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo6.servicioEventos.Assemblers.EventoModelAssembler;
import com.grupo6.servicioEventos.model.Evento;
import com.grupo6.servicioEventos.service.EventoService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v2/eventos")
@Tag(name = "Eventos V2", description = "Controlador para manejar eventos con HATEOAS")
public class EventoControllerV2 {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private EventoModelAssembler eventoModelAssembler;

    // Métodos para manejar las solicitudes HTTP
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Evento>> getAllEventos() {
        List<Evento> eventos = eventoService.findAll();
        List<EntityModel<Evento>> eventoModels = eventos.stream()
                .map(eventoModelAssembler::toModel)
                .toList();

        return CollectionModel.of(eventoModels,
                linkTo(methodOn(EventoControllerV2.class).getAllEventos()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Evento>> getEventoById(@PathVariable Integer id) {
        Optional<Evento> eventoOptional = eventoService.findById(id);
        if (eventoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        EntityModel<Evento> eventoModel = eventoModelAssembler.toModel(eventoOptional.get());
        return ResponseEntity.ok(eventoModel);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Evento>> createEvento(@RequestBody Evento evento) {
        Evento createdEvento = eventoService.save(evento);
        EntityModel<Evento> eventoModel = eventoModelAssembler.toModel(createdEvento);
        return ResponseEntity.created(linkTo(methodOn(EventoControllerV2.class).getEventoById(createdEvento.getId())).toUri())
                .body(eventoModel);
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Evento>> updateEvento(@PathVariable Integer id, @RequestBody Evento evento) {
        Optional<Evento> eventoOptional = eventoService.findById(id);
        if (eventoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        evento.setTitulo(evento.getTitulo());
        evento.setDescripcion(evento.getDescripcion());
        evento.setUbicacion(evento.getUbicacion());
        evento.setFechaInicio(evento.getFechaInicio());
        evento.setFechaFin(evento.getFechaFin());
        evento.setFechaActualizacion(Timestamp.valueOf(LocalDateTime.now()));

        Evento updatedEvento = eventoService.save(evento);
        EntityModel<Evento> eventoModel = eventoModelAssembler.toModel(updatedEvento);
        return ResponseEntity.ok(eventoModel);
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteEvento(@PathVariable Integer id) {
        Optional<Evento> eventoOptional = eventoService.findById(id);
        if (eventoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        eventoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
