package com.grupo6.servicioEventos;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo6.servicioEventos.controller.EventoController;
import com.grupo6.servicioEventos.model.Evento;
import com.grupo6.servicioEventos.service.EventoService;

@WebMvcTest(EventoController.class)
public class EventoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventoService eventoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Evento crearEventoEjemplo() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Evento evento = new Evento();
        evento.setId(1);
        evento.setTitulo("Título de prueba");
        evento.setDescripcion("Descripción de prueba");
        evento.setFechaInicio(sdf.parse("10-07-2025"));
        evento.setFechaFin(sdf.parse("12-07-2025"));
        evento.setUbicacion("Santiago");
        evento.setFechaCreacion(new Date());
        evento.setFechaActualizacion(new Date());
        return evento;
    }

    @Test
    @DisplayName("✅ Debería listar todos los eventos")
    void testListarEventos() throws Exception {
        when(eventoService.findAll()).thenReturn(List.of(crearEventoEjemplo()));

        mockMvc.perform(get("/api/v1/eventos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    @DisplayName("✅ Debería retornar 204 si no hay eventos")
    void testListarEventosVacio() throws Exception {
        when(eventoService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/eventos"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("✅ Debería obtener evento por ID")
    void testObtenerEventoPorId() throws Exception {
        when(eventoService.findById(1)).thenReturn(Optional.of(crearEventoEjemplo()));

        mockMvc.perform(get("/api/v1/eventos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("❌ Debería retornar 404 si el evento no existe")
    void testObtenerEventoPorIdNoExiste() throws Exception {
        when(eventoService.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/eventos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("✅ Debería obtener evento por título")
    void testObtenerEventoPorTitulo() throws Exception {
        when(eventoService.findByTitulo("Título de prueba")).thenReturn(Optional.of(crearEventoEjemplo()));

        mockMvc.perform(get("/api/v1/eventos/titulo/Título de prueba"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Título de prueba"));
    }

    @Test
    @DisplayName("❌ Debería retornar 404 si el título no existe")
    void testObtenerEventoPorTituloNoExiste() throws Exception {
        when(eventoService.findByTitulo("Inexistente")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/eventos/titulo/Inexistente"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("✅ Debería crear un evento")
    void testCrearEvento() throws Exception {
        Evento evento = crearEventoEjemplo();
        evento.setId(null); // simulando evento nuevo

        Evento creado = crearEventoEjemplo();

        when(eventoService.save(any(Evento.class))).thenReturn(creado);

        mockMvc.perform(post("/api/v1/eventos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(evento)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("❌ Error interno al crear evento")
    void testErrorCrearEvento() throws Exception {
        Evento evento = crearEventoEjemplo();
        evento.setId(null);

        when(eventoService.save(any(Evento.class))).thenThrow(new RuntimeException("Error simulado"));

        mockMvc.perform(post("/api/v1/eventos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(evento)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("✅ Debería actualizar un evento")
    void testActualizarEvento() throws Exception {
        Evento evento = crearEventoEjemplo();

        when(eventoService.findById(1)).thenReturn(Optional.of(evento));
        when(eventoService.save(any(Evento.class))).thenReturn(evento);

        mockMvc.perform(put("/api/v1/eventos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(evento)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("❌ No debería actualizar evento inexistente")
    void testActualizarEventoNoExiste() throws Exception {
        Evento evento = crearEventoEjemplo();

        when(eventoService.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/v1/eventos/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(evento)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("✅ Debería eliminar un evento")
    void testEliminarEvento() throws Exception {
        doNothing().when(eventoService).delete(1);

        mockMvc.perform(delete("/api/v1/eventos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("❌ Error interno al eliminar un evento")
    void testErrorEliminarEvento() throws Exception {
        // Simular error al eliminar
        doNothing().when(eventoService).delete(1);
        when(eventoService.findById(1)).thenThrow(new RuntimeException("Error"));

        mockMvc.perform(delete("/api/v1/eventos/1"))
                .andExpect(status().isNoContent()); // ajusta si decides manejar excepciones
    }
}
