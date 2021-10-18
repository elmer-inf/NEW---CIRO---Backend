package ciro.atc.controller;

import ciro.atc.dao.service.TablaDescripcionService;
import ciro.atc.model.dto.TablaDescripcion.TablaDescripcionGetDTO;
import ciro.atc.model.dto.TablaDescripcion.TablaDescripcionPostDTO;
import ciro.atc.model.dto.TablaDescripcion.TablaDescripcionPutDTO;
import ciro.atc.model.dto.TablaLista.TablaListaGetDTO;
import ciro.atc.model.dto.TablaLista.TablaListaPutDTO;
import ciro.atc.model.entity.TablaDescripcion;
import ciro.atc.model.entity.TablaLista;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/tablaDescripcion")
@CrossOrigin(origins = "*", maxAge = 20000)

public class TablaDescripcionController {

    @Autowired
    TablaDescripcionService tablaDescripcionService;

    @PostMapping("/registrar")
    public TablaDescripcion save (@Valid @RequestBody TablaDescripcionPostDTO data){
        return tablaDescripcionService.create(data);
    }

    @GetMapping("/listar")
    public List<TablaDescripcion> list() {
        return tablaDescripcionService.listTablaDescripcion();
    }

    @GetMapping("/listarNivel1/{id}")
    public List<TablaDescripcion> listNivel1(@PathVariable(value = "id") Long id) {
        return tablaDescripcionService.findTablaNivel1(id);
    }

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
    public TablaDescripcionGetDTO getEntidadByById(@PathVariable(value = "id") Long id) {
        return tablaDescripcionService.findTablaDescripcionByID(id);
    }

    @DeleteMapping("/eliminar/{id}")
    public TablaDescripcion delete(@PathVariable Long id){
        return tablaDescripcionService.deleteById(id);
    }


}
