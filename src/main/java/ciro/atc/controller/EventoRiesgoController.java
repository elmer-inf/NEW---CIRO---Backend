package ciro.atc.controller;

import ciro.atc.dao.service.EventoRiesgoService;
import ciro.atc.model.dto.EventoRiesgo.EventoRiesgoGetDTO;
import ciro.atc.model.dto.EventoRiesgo.EventoRiesgoPostDTO;
import ciro.atc.model.dto.EventoRiesgo.EventoRiesgoPutDTO;
import ciro.atc.model.dto.TablaDescripcion.TablaDescripcionGetDTO;
import ciro.atc.model.dto.TablaDescripcion.TablaDescripcionPutDTO;
import ciro.atc.model.entity.EventoRiesgo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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


}
