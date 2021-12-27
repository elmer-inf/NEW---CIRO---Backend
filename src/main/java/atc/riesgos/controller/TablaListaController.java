package atc.riesgos.controller;

import atc.riesgos.dao.service.TablaListaService;
import atc.riesgos.model.dto.TablaLista.TablaListaGetDTO;
import atc.riesgos.model.dto.TablaLista.TablaListaPostDTO;
import atc.riesgos.model.dto.TablaLista.TablaListaPutDTO;
import atc.riesgos.model.entity.TablaLista;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/tablaLista")
@CrossOrigin(origins = "*", maxAge = 20000)

public class TablaListaController {

    @Autowired
    TablaListaService tablaListaService;

    @PostMapping("/registrar")
    public TablaLista save (@Valid @RequestBody TablaListaPostDTO data){
        return tablaListaService.create(data);
    }

    @GetMapping("/listar")
    public List<TablaLista> list() {
        return tablaListaService.listTabla();
    }

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
    }

}
