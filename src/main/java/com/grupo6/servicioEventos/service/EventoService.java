package com.grupo6.servicioEventos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo6.servicioEventos.model.Evento;
import com.grupo6.servicioEventos.repository.EventoRepository;

import jakarta.transaction.Transactional;

@Service // Indica que esta clase es un servicio
@Transactional // Indica que los métodos de esta clase están en una transacción
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    public List<Evento> findAll() {
        return eventoRepository.findAll();
    }

    public Optional<Evento> findById(Integer id) {
        return eventoRepository.findById(id);
    }

    public Optional<Evento> findByTitulo(String titulo) {
        return eventoRepository.findByTitulo(titulo);
    }

    public Evento save(Evento evento) {
        return eventoRepository.save(evento);
    }

    public void delete(Integer id) {
        eventoRepository.deleteById(id);
    }
}
