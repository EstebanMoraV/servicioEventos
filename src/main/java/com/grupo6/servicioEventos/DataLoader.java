package com.grupo6.servicioEventos;


import java.util.concurrent.TimeUnit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.grupo6.servicioEventos.model.Evento;
import com.grupo6.servicioEventos.repository.EventoRepository;

import net.datafaker.Faker;

@Component
public class DataLoader implements CommandLineRunner {

    private final EventoRepository eventoRepository;
    private final Faker faker = new Faker();

    private final String[] titulos = {
        "Concierto de Rock",
        "Feria de Comida",
        "Exposición de Arte",
        "Festival de Cine",
        "Conferencia de Tecnología",
        "Taller de Fotografía",
        "Maratón Benéfica",
        "Espectáculo de Magia",
        "Evento Deportivo Local",
        "Charla Motivacional"
    };

    private final String[] descripciones = {
        "Un emocionante concierto de rock con bandas locales.",
        "Una feria gastronómica con comida de todo el mundo.",
        "Una exposición de arte contemporáneo con artistas emergentes.",
        "Un festival de cine independiente con proyecciones y charlas.",
        "Una conferencia sobre las últimas tendencias en tecnología.",
        "Un taller práctico de fotografía para principiantes.",
        "Una maratón benéfica para recaudar fondos para una buena causa.",
        "Un espectáculo de magia con ilusionistas reconocidos.",
        "Un evento deportivo local con competiciones y actividades para toda la familia.",
        "Una charla motivacional para inspirar a los asistentes."
    };

    private final String[] ubicaciones = {
        "Estadio Municipal",
        "Centro Cultural",
        "Parque Central",
        "Auditorio Principal",
        "Sala de Conferencias",
        "Galería de Arte",
        "Plaza Mayor",
        "Teatro Local",
        "Complejo Deportivo",
        "Biblioteca Pública"
    };



    public DataLoader(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void run(String... args) throws Exception {
        // Cargar datos de ejemplo si la base de datos está vacía
        if (eventoRepository.count() == 0) {
            for (int i = 0; i < 10; i++) {
                Evento evento = new Evento();
                evento.setTitulo(titulos[i]);
                evento.setDescripcion(descripciones[i]);
                evento.setUbicacion(ubicaciones[i]);
                evento.setFechaCreacion(faker.date().past(30, TimeUnit.DAYS));
                evento.setFechaInicio(faker.date().future(60, TimeUnit.DAYS));
                evento.setFechaFin(faker.date().future(60, TimeUnit.DAYS));
                eventoRepository.save(evento);
            }
        }

        System.out.println("Datos de ejemplo cargados en la base de datos.");
    }

}
