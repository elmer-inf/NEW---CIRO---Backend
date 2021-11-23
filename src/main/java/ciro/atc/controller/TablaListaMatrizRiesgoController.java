package ciro.atc.controller;

import ciro.atc.dao.service.TablaListaMatrizRiesgoService;
import ciro.atc.dao.service.TablaListaService;
import ciro.atc.model.dto.TablaLista.TablaListaGetDTO;
import ciro.atc.model.dto.TablaLista.TablaListaPostDTO;
import ciro.atc.model.dto.TablaLista.TablaListaPutDTO;
import ciro.atc.model.dto.TablaListaMatrizRiesgo.TablaListaMatrizRiesgoPostDTO;
import ciro.atc.model.entity.TablaLista;
import ciro.atc.model.entity.TablaListaMatrizRiesgo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/tablaListaMatrizRiesgo")
@CrossOrigin(origins = "*", maxAge = 20000)

public class TablaListaMatrizRiesgoController {

    @Autowired
    TablaListaMatrizRiesgoService tablaListaMatrizRiesgoService;

    @PostMapping("/registrar")
    public ResponseEntity<TablaListaMatrizRiesgo> save (@Valid @RequestBody TablaListaMatrizRiesgoPostDTO data){
        return tablaListaMatrizRiesgoService.create(data);
    }

    @GetMapping("/listar")
    public List<TablaListaMatrizRiesgo> list() {
        return tablaListaMatrizRiesgoService.listTabla();
    }

   /*

    @PutMapping("/editar/{id}")
    public TablaListaGetDTO updateById(@PathVariable(value = "id") Long id, @Valid @RequestBody TablaListaPutDTO data) {
        return tablaListaService.updateById(id, data);
    }

    @GetMapping("/mostrar/{id}")
    public TablaListaGetDTO getEntidadByById(@PathVariable(value = "id") Long id) {
        return tablaListaService.findTablaListaByID(id);
    }

    @DeleteMapping("/eliminar/{id}")
    public TablaLista delete(@PathVariable Long id){
        return tablaListaService.deleteById(id);
    }*/

}
