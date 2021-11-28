package ciro.atc.dao.service;

import ciro.atc.model.dto.MatrizRiesgo.MatrizRiesgoPostDTO;
import ciro.atc.model.entity.MatrizRiesgo;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MatrizRiesgoService {

    ResponseEntity<MatrizRiesgo> create(MatrizRiesgoPostDTO t);
    List<MatrizRiesgo> listMatrizRiesgo();

  /*  EventoRiesgo findByIdEvento(Long id);
    EventoRiesgoGetDTO updateById (Long id, EventoRiesgoPutDTO data);
    ResponseEntity<EventoRiesgo> evaluaEvento (Long id, EventoRiesgoPutDTOevaluacion data);

    EventoRiesgoGetDTO findEventoByID(Long id);
   */

}
