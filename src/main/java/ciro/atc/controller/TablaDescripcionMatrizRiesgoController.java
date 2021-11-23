package ciro.atc.controller;

import ciro.atc.dao.service.TablaDescripcionMatrizRiesgoService;
import ciro.atc.dao.service.TablaDescripcionService;
import ciro.atc.model.dto.TablaDescripcion.TablaDescripcionGetDTO;
import ciro.atc.model.dto.TablaDescripcion.TablaDescripcionGetDTO2;
import ciro.atc.model.dto.TablaDescripcion.TablaDescripcionPostDTO;
import ciro.atc.model.dto.TablaDescripcion.TablaDescripcionPutDTO;
import ciro.atc.model.dto.TablaDescripcionMatrizRiesgo.TablaDescripcionMatrizRiesgoPostDTO;
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

   /*



    @GetMapping("/listarNivel2/{id}/{id2}")
    public List<TablaDescripcion> listNivel2(@PathVariable(value = "id") Long id, @PathVariable(value = "id2") int id2) {
        return tablaDescripcionService.findTablaNivel2(id, id2);
    }

    @GetMapping("/listarNivel3/{id}/{id2}/{id3}")
    public List<TablaDescripcion> listNivel3(@PathVariable(value = "id") Long id, @PathVariable(value = "id2") int id2, @PathVariable(value = "id3") int id3) {
        return tablaDescripcionService.findTablaNivel3(id, id2, id3);
    }

    @PutMapping("/editar/{id}")
    public TablaDescripcionGetDTO updateById(@PathVariable(value = "id") Long id, @Valid @RequestBody TablaDescripcionPutDTO data) {
        return tablaDescripcionService.updateById(id, data);
    }

    @GetMapping("/mostrar/{id}")
    public TablaDescripcionGetDTO getTablaDescripcionByById(@PathVariable(value = "id") Long id) {
        return tablaDescripcionService.findTablaDescripcionByID(id);
    }

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
