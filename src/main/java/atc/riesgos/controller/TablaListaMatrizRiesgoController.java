package atc.riesgos.controller;

import atc.riesgos.dao.service.TablaListaMatrizRiesgoService;
import atc.riesgos.model.dto.TablaListaMatrizRiesgo.TablaListaMatrizRiesgoPostDTO;
import atc.riesgos.model.entity.TablaListaMatrizRiesgo;
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
