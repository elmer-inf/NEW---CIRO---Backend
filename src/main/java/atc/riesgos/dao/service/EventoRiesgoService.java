package atc.riesgos.dao.service;

import atc.riesgos.model.dto.EventoRiesgo.EventoRecurrente.EventoRiesgoPutDTOrecurrente;
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
    //ResponseEntity<EventoRiesgoGetDTO> updateById(Long id, EventoRiesgoPutDTO data, MultipartFile[] files, String idsEliminar);
    ResponseEntity<EventoRiesgoGetDTO> updateById (Long id, EventoRiesgoPutDTO data);
    EventoRiesgo findByIdEvento(Long id);
    ResponseEntity<EventoRiesgo> evaluaEvento (Long id, EventoRiesgoPutDTOevaluacion data);
    EventoRiesgoGetDTO findEventoByID(Long id);
    List<EventoRiesgo> listEventoRiesgo();
    String generaCodigo(Long id);
    EventoRiesgo deleteByIdEvento(Long id);

    List<EventoRiesgo> listEventoRiesgoRecurrentes();
    ResponseEntity<EventoRiesgoGetDTO> updateEventoRecurrenteWithFiles(Long id, EventoRiesgoPutDTOrecurrente data, MultipartFile[] files);
}
