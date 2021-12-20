package ciro.atc.controller;

import ciro.atc.dao.service.MatrizOportunidadService;
import ciro.atc.model.dto.MatrizOportunidad.MatrizOportunidadGetDTO;
import ciro.atc.model.dto.MatrizOportunidad.MatrizOportunidadPostDTO;
import ciro.atc.model.dto.MatrizOportunidad.MatrizOportunidadPutDTO;
import ciro.atc.model.dto.MatrizOportunidad.MatrizOportunidadPutDTOevaluacion;
import ciro.atc.model.entity.MatrizOportunidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/matrizOportunidad")
@CrossOrigin(origins = "*", maxAge = 20000)

public class MatrizOportunidadController {

    @Autowired
    MatrizOportunidadService matrizOportunidadService;

    @PostMapping("/registrar")
    public ResponseEntity<MatrizOportunidad> save (@Valid @RequestBody MatrizOportunidadPostDTO data){
        return matrizOportunidadService.create(data);
    }

    @GetMapping("/listar")
    public List<MatrizOportunidad> listMatrizOportunidad() {
        return matrizOportunidadService.listMatrizOportunidad();
    }

    @GetMapping("/mostrar/{id}")
    public MatrizOportunidadGetDTO findMatrizOportunidadByID(@PathVariable(value = "id") Long id) {
        return matrizOportunidadService.findMatrizOportunidadByID(id);
    }

    @PutMapping("/evaluaOportunidad/{id}")
    public ResponseEntity<MatrizOportunidad> evaluaOportunidad (@PathVariable(value = "id") Long id, @Valid @RequestBody MatrizOportunidadPutDTOevaluacion data){
        return matrizOportunidadService.evaluaOportunidad(id, data);
    }

    /* No lo probe */
    @PutMapping("/editar/{id}")
    public MatrizOportunidadGetDTO updateById (@PathVariable(value = "id") Long id, @Valid @RequestBody MatrizOportunidadPutDTO data){
        return matrizOportunidadService.updateById(id, data);
    }


}
