package com.evento.servicioEvento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.evento.servicioEvento.model.EventEntity;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {
}
