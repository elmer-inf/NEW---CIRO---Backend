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
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1/eventoRiesgo")
@CrossOrigin(origins = "*", maxAge = 20000)

public class EventoRiesgoController extends Controller {

    @Autowired
    EventoRiesgoService eventoRiesgoService;

    @PostMapping("/registrar")

    public ResponseEntity<EventoRiesgo>  save (@Valid @RequestBody EventoRiesgoPostDTO data){
        return eventoRiesgoService.create(data);
    }

    @PostMapping("/registrarwithfiles")
    public ResponseEntity<EventoRiesgo>  saveWithFiles (@ModelAttribute EventoRiesgoFilePostDTO data){
       EventoRiesgoPostDTO dataDTO = Log.jsonToObject(data.getEventoRiesgoPostDTO(),EventoRiesgoPostDTO.class);
       return eventoRiesgoService.createWithFiles(dataDTO, data.getFile());
    }

    @PutMapping(value = "/editarwithfiles/{id}")
    public ResponseEntity<EventoRiesgoGetDTO> updateById(@PathVariable("id") Long id, @ModelAttribute EventoRiesgoFilePutDTO data) {
        System.out.println("data.getFiles() " + data.getFile());
        EventoRiesgoPutDTO dataDTO = Log.jsonToObject(data.getEventoRiesgoPutDTO(),EventoRiesgoPutDTO.class);

        System.out.println("DAAATTAAA desseria: " + Log.toJSON(dataDTO));
        return eventoRiesgoService.updateById(id, dataDTO, data.getFile() , data.getFilesToDelete());
    }

    @PutMapping("/evaluaEvento/{id}")
    public ResponseEntity<EventoRiesgo> evaluaEvento (@PathVariable(value = "id") Long id, @Valid @RequestBody EventoRiesgoPutDTOevaluacion data){
        return eventoRiesgoService.evaluaEvento(id, data);
    }

    @GetMapping("/generaCodigo/{id}")
    public String generaCodigo (@PathVariable(value = "id") Long id){
        return eventoRiesgoService.generaCodigo(id);
    }

    @GetMapping("/mostrar/{id}")
    public EventoRiesgoGetDTO getEventoByById(@PathVariable(value = "id") Long id) {
        return eventoRiesgoService.findEventoByID(id);
    }

    @GetMapping("/listar")
    public List<EventoRiesgo> listEventoRiesgo() {
        return eventoRiesgoService.listEventoRiesgo();
    }

    @PutMapping("/eliminar/{id}")
    public EventoRiesgo deleteById(@PathVariable(value = "id") Long id) {
        return eventoRiesgoService.deleteByIdEvento(id);
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
