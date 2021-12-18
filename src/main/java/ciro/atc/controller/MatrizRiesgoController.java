package ciro.atc.controller;

import ciro.atc.dao.service.MatrizRiesgoService;
import ciro.atc.model.dto.MatrizRiesgo.MatrizRiesgoGetDTO;
import ciro.atc.model.dto.MatrizRiesgo.MatrizRiesgoPostDTO;
import ciro.atc.model.dto.MatrizRiesgo.MatrizRiesgoPutDTO;
import ciro.atc.model.dto.MatrizRiesgo.MatrizRiesgoPutDTOevaluacion;
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

    @GetMapping("/mostrar/{id}")
    public MatrizRiesgoGetDTO findMatrizRiesgoByID(@PathVariable(value = "id") Long id) {
        return matrizRiesgoService.findMatrizRiesgoByID(id);
    }

    @PutMapping("/evaluaRiesgo/{id}")
    public ResponseEntity<MatrizRiesgo> evaluaRiesgo (@PathVariable(value = "id") Long id, @Valid @RequestBody MatrizRiesgoPutDTOevaluacion data){
        return matrizRiesgoService.evaluaRiesgo(id, data);
    }

    /* No lo probe */
    @PutMapping("/editar/{id}")
    public MatrizRiesgoGetDTO updateById (@PathVariable(value = "id") Long id, @Valid @RequestBody MatrizRiesgoPutDTO data){
        return matrizRiesgoService.updateById(id, data);
    }

}
