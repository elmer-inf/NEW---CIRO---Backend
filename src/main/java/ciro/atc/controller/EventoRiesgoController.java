package ciro.atc.controller;

import ciro.atc.dao.service.EventoRiesgoService;
import ciro.atc.model.dto.EventoRiesgo.EventoRiesgoGetDTO;
import ciro.atc.model.dto.EventoRiesgo.EventoRiesgoPostDTO;
import ciro.atc.model.dto.EventoRiesgo.EventoRiesgoPutDTO;
import ciro.atc.model.dto.EventoRiesgo.EventoRiesgoPutDTOevaluacion;
import ciro.atc.model.dto.TablaDescripcion.TablaDescripcionGetDTO;
import ciro.atc.model.dto.TablaDescripcion.TablaDescripcionPutDTO;
import ciro.atc.model.entity.EventoRiesgo;
import ciro.atc.model.entity.TablaDescripcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/eventoRiesgo")
@CrossOrigin(origins = "*", maxAge = 20000)

public class EventoRiesgoController {

    @Autowired
    EventoRiesgoService eventoRiesgoService;

    @PostMapping("/registrar")
    public EventoRiesgo save (@Valid @RequestBody EventoRiesgoPostDTO data){
        return eventoRiesgoService.create(data);
    }

    @PutMapping("/editar/{id}")
    public EventoRiesgoGetDTO updateById (@PathVariable(value = "id") Long id, @Valid @RequestBody EventoRiesgoPutDTO data){
        return eventoRiesgoService.updateById(id, data);
    }

    @PutMapping("/evaluaEvento/{id}")
    public ResponseEntity<EventoRiesgo> evaluaEvento (@PathVariable(value = "id") Long id, @Valid @RequestBody EventoRiesgoPutDTOevaluacion data){
        return eventoRiesgoService.evaluaEvento(id, data);
    }

    @GetMapping("/mostrar/{id}")
    public EventoRiesgoGetDTO getEventoByById(@PathVariable(value = "id") Long id) {
        return eventoRiesgoService.findEventoByID(id);
    }

    @GetMapping("/listar")
    public List<EventoRiesgo> listEventoRiesgo() {
        return eventoRiesgoService.listEventoRiesgo();
    }


}
