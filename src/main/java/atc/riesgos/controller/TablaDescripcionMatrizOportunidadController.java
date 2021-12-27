package atc.riesgos.controller;

import atc.riesgos.model.dto.TablaDescripcionMatrizOportunidad.TablaDescripcionMatrizOportunidadPutDTO;
import atc.riesgos.dao.service.TablaDescripcionMatrizOportunidadService;
import atc.riesgos.model.dto.TablaDescripcionMatrizOportunidad.TablaDescripcionMatrizOportunidadGetDTO;
import atc.riesgos.model.dto.TablaDescripcionMatrizOportunidad.TablaDescripcionMatrizOportunidadGetDTO2;
import atc.riesgos.model.dto.TablaDescripcionMatrizOportunidad.TablaDescripcionMatrizOportunidadPostDTO;
import atc.riesgos.model.entity.TablaDescripcionMatrizOportunidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/tablaDescripcionMatrizOportunidad")
@CrossOrigin(origins = "*", maxAge = 20000)

public class TablaDescripcionMatrizOportunidadController {

    @Autowired
    TablaDescripcionMatrizOportunidadService tablaDescripcionMatrizOportunidadService;

    @PostMapping("/registrar")
    public ResponseEntity<TablaDescripcionMatrizOportunidad> save (@Valid @RequestBody TablaDescripcionMatrizOportunidadPostDTO data){
        return tablaDescripcionMatrizOportunidadService.create(data);
    }

    @GetMapping("/listar")
    public List<TablaDescripcionMatrizOportunidad> list() {
        return tablaDescripcionMatrizOportunidadService.listTablaDescripcionMatrizO();
    }

    @GetMapping("/listarNivel1/{id}")
    public List<TablaDescripcionMatrizOportunidad> listNivel1(@PathVariable(value = "id") Long id) {
        return tablaDescripcionMatrizOportunidadService.findTablaNivel1(id);
    }

    @GetMapping("/listarNivel2/{id}/{id2}")
    public List<TablaDescripcionMatrizOportunidad> listNivel2(@PathVariable(value = "id") Long id, @PathVariable(value = "id2") int id2) {
        return tablaDescripcionMatrizOportunidadService.findTablaNivel2(id, id2);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<TablaDescripcionMatrizOportunidadGetDTO> updateById(@PathVariable(value = "id") Long id, @Valid @RequestBody TablaDescripcionMatrizOportunidadPutDTO data) {
        return tablaDescripcionMatrizOportunidadService.updateById(id, data);
    }

    @GetMapping("/mostrar/{id}")
    public TablaDescripcionMatrizOportunidadGetDTO getTablaDescripcionByById(@PathVariable(value = "id") Long id) {
        return tablaDescripcionMatrizOportunidadService.findTablaDescripcionByID(id);
    }

    @GetMapping("/mostrar2/{id}")
    public TablaDescripcionMatrizOportunidadGetDTO2 getTablaDscripcionByById2(@PathVariable(value = "id") Long id) {
        return tablaDescripcionMatrizOportunidadService.findTablaDescripcionByID2(id);
    }


}
