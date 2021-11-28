package ciro.atc.controller;

import ciro.atc.dao.service.TablaDescripcionMatrizRiesgoService;
import ciro.atc.dao.service.TablaDescripcionService;
import ciro.atc.model.dto.TablaDescripcion.TablaDescripcionGetDTO;
import ciro.atc.model.dto.TablaDescripcion.TablaDescripcionGetDTO2;
import ciro.atc.model.dto.TablaDescripcion.TablaDescripcionPostDTO;
import ciro.atc.model.dto.TablaDescripcion.TablaDescripcionPutDTO;
import ciro.atc.model.dto.TablaDescripcionMatrizRiesgo.TablaDescripcionMatrizRiesgoGetDTO;
import ciro.atc.model.dto.TablaDescripcionMatrizRiesgo.TablaDescripcionMatrizRiesgoPostDTO;
import ciro.atc.model.dto.TablaDescripcionMatrizRiesgo.TablaDescripcionMatrizRiesgoPutDTO;
import ciro.atc.model.entity.TablaDescripcion;
import ciro.atc.model.entity.TablaDescripcionMatrizRiesgo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/tablaDescripcionMatrizRiesgo")
@CrossOrigin(origins = "*", maxAge = 20000)

public class TablaDescripcionMatrizRiesgoController {

    @Autowired
    TablaDescripcionMatrizRiesgoService tablaDescripcionMatrizRiesgoService;

    @PostMapping("/registrar")
    public ResponseEntity<TablaDescripcionMatrizRiesgo> save (@Valid @RequestBody TablaDescripcionMatrizRiesgoPostDTO data){
        return tablaDescripcionMatrizRiesgoService.create(data);
    }

    @GetMapping("/listar")
    public List<TablaDescripcionMatrizRiesgo> list() {
        return tablaDescripcionMatrizRiesgoService.listTablaDescripcionMatrizR();
    }

    @GetMapping("/listarNivel1/{id}")
    public List<TablaDescripcionMatrizRiesgo> listNivel1(@PathVariable(value = "id") Long id) {
        return tablaDescripcionMatrizRiesgoService.findTablaNivel1(id);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<TablaDescripcionMatrizRiesgoGetDTO> updateById(@PathVariable(value = "id") Long id, @Valid @RequestBody TablaDescripcionMatrizRiesgoPutDTO data) {
        return tablaDescripcionMatrizRiesgoService.updateById(id, data);
    }

    @GetMapping("/mostrar/{id}")
    public TablaDescripcionMatrizRiesgoGetDTO getTablaDescripcionByById(@PathVariable(value = "id") Long id) {
        return tablaDescripcionMatrizRiesgoService.findTablaDescripcionByID(id);
    }

   /*

    @GetMapping("/mostrar2/{id}")
    public TablaDescripcionGetDTO2 getTablaDscripcionByById2(@PathVariable(value = "id") Long id) {
        return tablaDescripcionService.findTablaDescripcionByID2(id);
    }

    @DeleteMapping("/eliminar/{id}")
    public TablaDescripcion delete(@PathVariable Long id){
        return tablaDescripcionService.deleteById(id);
    }
*/

}
