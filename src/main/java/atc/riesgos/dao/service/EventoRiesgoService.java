package atc.riesgos.dao.service;

import atc.riesgos.model.dto.EventoRiesgo.EventoRiesgoDTO;
import atc.riesgos.model.dto.EventoRiesgo.EventoRiesgoGetDTO;
import atc.riesgos.model.dto.EventoRiesgo.EventoRiesgoPostDTO;
import atc.riesgos.model.dto.EventoRiesgo.EventoRiesgoPutDTOevaluacion;
import atc.riesgos.model.dto.EventoRiesgo.*;

import atc.riesgos.model.entity.EventoRiesgo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EventoRiesgoService {

    ResponseEntity<EventoRiesgo> create(EventoRiesgoPostDTO t);
    ResponseEntity<EventoRiesgo> createWithFiles(EventoRiesgoPostDTO data, MultipartFile[] files);

    EventoRiesgo findByIdEvento(Long id);
    ResponseEntity<EventoRiesgoGetDTO> updateById (Long id, EventoRiesgoPostDTO data);
    ResponseEntity<EventoRiesgo> evaluaEvento (Long id, EventoRiesgoPutDTOevaluacion data);

    EventoRiesgoGetDTO findEventoByID(Long id);
    List<EventoRiesgo> listEventoRiesgo();
    List<EventoRiesgo> diezDiasAntes();
    List<EventoRiesgo> cincoDiasAntes();
    List<EventoRiesgo> planVencido();
    String generaCodigo(Long id);
}
