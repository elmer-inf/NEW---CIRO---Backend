package ciro.atc.controller;

import ciro.atc.dao.service.MatrizRiesgoService;
import ciro.atc.model.dto.MatrizRiesgo.MatrizRiesgoPostDTO;
import ciro.atc.model.entity.MatrizRiesgo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/matrizRiesgo")
@CrossOrigin(origins = "*", maxAge = 20000)

public class MatrizRiesgoController {

    @Autowired
    MatrizRiesgoService matrizRiesgoService;

    @PostMapping("/registrar")
    public ResponseEntity<MatrizRiesgo> save (@Valid @RequestBody MatrizRiesgoPostDTO data){
        return matrizRiesgoService.create(data);
    }

    @GetMapping("/listar")
    public List<MatrizRiesgo> listMatrizRiesgo() {
        return matrizRiesgoService.listMatrizRiesgo();
    }

   /* @PutMapping("/editar/{id}")
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

    */


}
