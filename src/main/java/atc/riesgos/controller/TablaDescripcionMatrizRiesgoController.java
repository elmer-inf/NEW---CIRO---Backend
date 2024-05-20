package atc.riesgos.controller;

import atc.riesgos.dao.service.TablaDescripcionMatrizRiesgoService;
import atc.riesgos.model.dto.TablaDescripcionMatrizRiesgo.TablaDescripcionMatrizRiesgoGetDTO;
import atc.riesgos.model.dto.TablaDescripcionMatrizRiesgo.TablaDescripcionMatrizRiesgoPostDTO;
import atc.riesgos.model.dto.TablaDescripcionMatrizRiesgo.TablaDescripcionMatrizRiesgoPutDTO;
import atc.riesgos.model.entity.TablaDescripcion;
import atc.riesgos.model.entity.TablaDescripcionMatrizRiesgo;
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

    @GetMapping("/listarNivel2/{id}/{id2}")
    public List<TablaDescripcionMatrizRiesgo> listNivel2(@PathVariable(value = "id") Long id, @PathVariable(value = "id2") int id2) {
        return tablaDescripcionMatrizRiesgoService.findTablaNivel2(id, id2);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<TablaDescripcionMatrizRiesgoGetDTO> updateById(@PathVariable(value = "id") Long id, @Valid @RequestBody TablaDescripcionMatrizRiesgoPutDTO data) {
        return tablaDescripcionMatrizRiesgoService.updateById(id, data);
    }

    @GetMapping("/mostrar/{id}")
    public TablaDescripcionMatrizRiesgoGetDTO getTablaDescripcionByById(@PathVariable(value = "id") Long id) {
        return tablaDescripcionMatrizRiesgoService.findTablaDescripcionByID(id);
    }

    @DeleteMapping("/eliminar/{id}")
    public TablaDescripcionMatrizRiesgo delete(@PathVariable Long id){
        return tablaDescripcionMatrizRiesgoService.deleteById(id);
    }

}
