package com.grupo6.servicioEventos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo6.servicioEventos.model.Evento;


@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> { // Interfaz que extiende JpaRepository para operaciones CRUD
}
