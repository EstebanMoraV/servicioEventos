package com.evento.servicioEvento.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evento.servicioEvento.model.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> { // Interfaz que extiende JpaRepository para operaciones CRUD
    // JpaRepository<Evento, Long> indica que la entidad es Evento y el tipo de ID es Long
    // Puedes agregar métodos personalizados aquí si es necesario
}
