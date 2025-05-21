package com.grupo6.servicioEventos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo6.servicioEventos.model.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> { // Interfaz que extiende JpaRepository para operaciones CRUD
    // JpaRepository<Evento, Long> indica que la entidad es Evento y el tipo de ID es Long
    // Puedes agregar métodos personalizados aquí si es necesario
}
