package com.grupo6.servicioEventos.Assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.grupo6.servicioEventos.controller.EventoController;
import com.grupo6.servicioEventos.model.Evento;

@Component
public class EventoModelAssembler implements RepresentationModelAssembler<Evento, EntityModel<Evento>> {

    @Override
    public EntityModel<Evento> toModel(Evento evento) {
        return EntityModel.of(evento,
                linkTo(methodOn(EventoController.class).getEventoById(evento.getId())).withSelfRel(),
                linkTo(methodOn(EventoController.class).getAllEventos()).withRel("eventos"));
    }




}
