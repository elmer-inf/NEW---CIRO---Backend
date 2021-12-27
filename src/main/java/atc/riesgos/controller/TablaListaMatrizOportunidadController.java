package atc.riesgos.controller;

import atc.riesgos.dao.service.TablaListaMatrizOportunidadService;
import atc.riesgos.model.dto.TablaListaMatrizOportunidad.TablaListaMatrizOportunidadPostDTO;
import atc.riesgos.model.entity.TablaListaMatrizOportunidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/tablaListaMatrizOportunidad")
@CrossOrigin(origins = "*", maxAge = 20000)

public class TablaListaMatrizOportunidadController {

    @Autowired
    TablaListaMatrizOportunidadService tablaListaMatrizOportunidadService;

    @PostMapping("/registrar")
    public ResponseEntity<TablaListaMatrizOportunidad> save (@Valid @RequestBody TablaListaMatrizOportunidadPostDTO data){
        return tablaListaMatrizOportunidadService.create(data);
    }

    @GetMapping("/listar")
    public List<TablaListaMatrizOportunidad> list() {
        return tablaListaMatrizOportunidadService.listTabla();
    }
}
