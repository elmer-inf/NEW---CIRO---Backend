package ciro.atc.dao.service;

import ciro.atc.model.dto.EventoRiesgo.*;

import ciro.atc.model.dto.TablaDescripcion.TablaDescripcionGetDTO;
import ciro.atc.model.entity.EventoRiesgo;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EventoRiesgoService {

    ResponseEntity<EventoRiesgo> create(EventoRiesgoPostDTO t);
    EventoRiesgo findByIdEvento(Long id);
    ResponseEntity<EventoRiesgoGetDTO> updateById (Long id, EventoRiesgoDTO data);
    ResponseEntity<EventoRiesgo> evaluaEvento (Long id, EventoRiesgoPutDTOevaluacion data);

    EventoRiesgoGetDTO findEventoByID(Long id);
    List<EventoRiesgo> listEventoRiesgo();

    //String countEventoCodigo (String sigla);



}
