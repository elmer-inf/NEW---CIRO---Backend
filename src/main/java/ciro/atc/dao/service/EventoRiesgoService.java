package ciro.atc.dao.service;

import ciro.atc.model.dto.EventoRiesgo.EventoRiesgoGetDTO;
import ciro.atc.model.dto.EventoRiesgo.EventoRiesgoPostDTO;
import ciro.atc.model.dto.EventoRiesgo.EventoRiesgoPutDTO;

import ciro.atc.model.entity.EventoRiesgo;

public interface EventoRiesgoService {

    EventoRiesgo create(EventoRiesgoPostDTO t);
    EventoRiesgo findByIdEvento(Long id);
    EventoRiesgoGetDTO updateById (Long id, EventoRiesgoPutDTO data);

}
