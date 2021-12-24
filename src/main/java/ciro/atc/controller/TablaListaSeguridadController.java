package ciro.atc.controller;

import ciro.atc.dao.service.TablaListaSeguridadService;
import ciro.atc.model.dto.TablaListaSeguridad.TablaListaSeguridadPostDTO;
import ciro.atc.model.entity.TablaListaSeguridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/tablaListaSeguridad")
@CrossOrigin(origins = "*", maxAge = 20000)

public class TablaListaSeguridadController {

    @Autowired
    TablaListaSeguridadService tablaListaSeguridadService;

    @PostMapping("/registrar")
    public ResponseEntity<TablaListaSeguridad> save (@Valid @RequestBody TablaListaSeguridadPostDTO data){
        return tablaListaSeguridadService.create(data);
    }

    @GetMapping("/listar")
    public List<TablaListaSeguridad> list() {
        return tablaListaSeguridadService.listTabla();
    }

}
