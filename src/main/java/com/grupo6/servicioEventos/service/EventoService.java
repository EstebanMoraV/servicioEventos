package com.evento.servicioEvento.service;

import com.evento.servicioEvento.dto.*;
import com.evento.servicioEvento.model.EventEntity;
import com.evento.servicioEvento.repository.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository repository;

    public List<EventEntity> findAll() {
        return repository.findAll();
    }

    public Optional<EventEntity> findById(Long id) {
        return repository.findById(id);
    }

    public EventEntity create(EventCreateDTO dto) {
        EventEntity evento = new EventEntity();
        evento.setTitulo(dto.getTitulo());
        evento.setDescripcion(dto.getDescripcion());
        evento.setUbicacion(dto.getUbicacion());
        evento.setFechaInicio(dto.getFechaInicio());
        evento.setFechaFin(dto.getFechaFin());
        evento.setUsuarioId(dto.getUsuarioId());
        return repository.save(evento);
    }

    public Optional<EventEntity> update(Long id, EventUpdateDTO dto) {
        return repository.findById(id).map(evento -> {
            evento.setTitulo(dto.getTitulo());
            evento.setDescripcion(dto.getDescripcion());
            evento.setUbicacion(dto.getUbicacion());
            evento.setFechaInicio(dto.getFechaInicio());
            evento.setFechaFin(dto.getFechaFin());
            evento.setUsuarioId(dto.getUsuarioId());
            return repository.save(evento);
        });
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}