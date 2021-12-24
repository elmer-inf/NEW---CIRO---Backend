package ciro.atc.controller;

import ciro.atc.dao.service.TablaDescripcionSeguridadService;
import ciro.atc.model.dto.TablaDescripcionSeguridad.TablaDescripcionSeguridadGetDTO;
import ciro.atc.model.dto.TablaDescripcionSeguridad.TablaDescripcionSeguridadPostDTO;
import ciro.atc.model.dto.TablaDescripcionSeguridad.TablaDescripcionSeguridadPutDTO;
import ciro.atc.model.entity.TablaDescripcionSeguridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/tablaDescripcionSeguridad")
@CrossOrigin(origins = "*", maxAge = 20000)

public class TablaDescripcionSeguridadController {

    @Autowired
    TablaDescripcionSeguridadService tablaDescripcionSeguridadService;

    @PostMapping("/registrar")
    public ResponseEntity<TablaDescripcionSeguridad> save (@Valid @RequestBody TablaDescripcionSeguridadPostDTO data){
        return tablaDescripcionSeguridadService.create(data);
    }

    @GetMapping("/listar")
    public List<TablaDescripcionSeguridad> list() {
        return tablaDescripcionSeguridadService.listTablaDescripcionSeguridad();
    }

    @GetMapping("/listarNivel1/{id}")
    public List<TablaDescripcionSeguridad> listNivel1(@PathVariable(value = "id") Long id) {
        return tablaDescripcionSeguridadService.findTablaNivel1(id);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<TablaDescripcionSeguridadGetDTO> updateById(@PathVariable(value = "id") Long id, @Valid @RequestBody TablaDescripcionSeguridadPutDTO data) {
        return tablaDescripcionSeguridadService.updateById(id, data);
    }

    @GetMapping("/mostrar/{id}")
    public TablaDescripcionSeguridadGetDTO getTablaDescripcionByById(@PathVariable(value = "id") Long id) {
        return tablaDescripcionSeguridadService.findTablaDescripcionByID(id);
    }

}
