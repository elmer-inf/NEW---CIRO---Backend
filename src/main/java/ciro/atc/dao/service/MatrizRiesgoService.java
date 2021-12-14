package ciro.atc.dao.service;

import ciro.atc.model.dto.EventoRiesgo.EventoRiesgoPutDTOevaluacion;
import ciro.atc.model.dto.MatrizRiesgo.MatrizRiesgoGetDTO;
import ciro.atc.model.dto.MatrizRiesgo.MatrizRiesgoPostDTO;
import ciro.atc.model.dto.MatrizRiesgo.MatrizRiesgoPutDTOevaluacion;
import ciro.atc.model.entity.EventoRiesgo;
import ciro.atc.model.entity.MatrizRiesgo;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MatrizRiesgoService {

    ResponseEntity<MatrizRiesgo> create(MatrizRiesgoPostDTO t);
    List<MatrizRiesgo> listMatrizRiesgo();
    MatrizRiesgoGetDTO findMatrizRiesgoByID(Long id);
    MatrizRiesgo findByIdRiesgo(Long id);
    ResponseEntity<MatrizRiesgo> evaluaRiesgo (Long id, MatrizRiesgoPutDTOevaluacion data);

  /*
    EventoRiesgoGetDTO updateById (Long id, EventoRiesgoPutDTO data);
    EventoRiesgoGetDTO findEventoByID(Long id);
   */

}
