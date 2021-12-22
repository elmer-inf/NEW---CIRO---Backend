package ciro.atc.controller;

import ciro.atc.auth.Controller;
import ciro.atc.dao.service.EventoRiesgoService;
import ciro.atc.model.dto.EventoRiesgo.*;
import ciro.atc.model.dto.TablaDescripcion.TablaDescripcionGetDTO;
import ciro.atc.model.dto.TablaDescripcion.TablaDescripcionPutDTO;
import ciro.atc.model.entity.EventoRiesgo;
import ciro.atc.model.entity.TablaDescripcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/eventoRiesgo")
@CrossOrigin(origins = "*", maxAge = 20000)

public class EventoRiesgoController extends Controller {

    @Autowired
    EventoRiesgoService eventoRiesgoService;

    @PostMapping("/registrar")
    public ResponseEntity<EventoRiesgo>  save (@Valid @RequestBody EventoRiesgoDTO data){
        return eventoRiesgoService.create(data);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<EventoRiesgoGetDTO> updateById (@PathVariable(value = "id") Long id, @Valid @RequestBody EventoRiesgoDTO data){
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


    @GetMapping({"/{page}/{size}/{order}", "/{page}/{size}"})
    public Object filterBy(
            @PathVariable(value = "page") int page,
            @PathVariable(value = "size") int size,
            @PathVariable(value = "order", required = false) String order,
            HttpServletRequest request) {

        return createHQL(EventoRiesgo.class).order(order)
                .map(request).paging(page, size);
    }


}
