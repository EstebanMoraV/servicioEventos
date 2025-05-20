package com.evento.servicioEvento.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evento.servicioEvento.model.Evento;
import com.evento.servicioEvento.repository.EventoRepository;

import jakarta.transaction.Transactional;

@Service // Indica que esta clase es un servicio
@Transactional // Indica que los métodos de esta clase están en una transacción
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    public List<Evento> findAll() {
        return eventoRepository.findAll();
    }

    public Optional<Evento> findById(Long id) {
        return eventoRepository.findById(id);
    }

    public Evento save(Evento evento) {
        return eventoRepository.save(evento);
    }

    public void delete(Long id) {
        eventoRepository.deleteById(id);
    }
}
