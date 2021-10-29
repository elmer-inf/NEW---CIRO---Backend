package ciro.atc.dao.service;

import ciro.atc.model.dto.EventoRiesgo.EventoRiesgoGetDTO;
import ciro.atc.model.dto.EventoRiesgo.EventoRiesgoPostDTO;
import ciro.atc.model.dto.EventoRiesgo.EventoRiesgoPutDTO;

import ciro.atc.model.dto.EventoRiesgo.EventoRiesgoPutDTOevaluacion;
import ciro.atc.model.dto.TablaDescripcion.TablaDescripcionGetDTO;
import ciro.atc.model.entity.EventoRiesgo;

import java.util.List;

public interface EventoRiesgoService {

    EventoRiesgo create(EventoRiesgoPostDTO t);
    EventoRiesgo findByIdEvento(Long id);
    EventoRiesgoGetDTO updateById (Long id, EventoRiesgoPutDTO data);
    void evaluaEvento (Long id, EventoRiesgoPutDTOevaluacion data);

    EventoRiesgoGetDTO findEventoByID(Long id);
    List<EventoRiesgo> listEventoRiesgo();

    //String countEventoCodigo (String sigla);



}
