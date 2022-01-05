package atc.riesgos.controller;

import atc.riesgos.dao.service.EventoRiesgoService;
import atc.riesgos.auth.Controller;
import atc.riesgos.config.log.Log;
import atc.riesgos.model.dto.EventoRiesgo.EventoRiesgoDTO;
import atc.riesgos.model.dto.EventoRiesgo.EventoRiesgoGetDTO;
import atc.riesgos.model.dto.EventoRiesgo.EventoRiesgoPostDTO;
import atc.riesgos.model.dto.EventoRiesgo.EventoRiesgoPutDTOevaluacion;
import atc.riesgos.model.dto.EventoRiesgo.*;
import atc.riesgos.model.entity.EventoRiesgo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    //@RequestMapping(value = "/registrar" , method = RequestMethod.POST, consumes = { "multipart/form-data" })
    //@PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,
    //MediaType.APPLICATION_JSON_VALUE }, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventoRiesgo>  save (@Valid @RequestBody EventoRiesgoPostDTO data){
        //System.out.println("Demooooooooooooooooo: " + Log.toJSON(data));
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

    @GetMapping("/diezDiasAntes")
    public List<EventoRiesgo> diezDiasAntes() {
        return eventoRiesgoService.diezDiasAntes();
    }

    @GetMapping("/cincoDiasAntes")
    public List<EventoRiesgo> cincoDiasAntes() {
        return eventoRiesgoService.cincoDiasAntes();
    }

    @GetMapping("/planVencido")
    public List<EventoRiesgo> planVencido() {
        return eventoRiesgoService.planVencido();
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
