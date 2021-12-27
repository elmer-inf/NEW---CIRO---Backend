package atc.riesgos.controller;

import atc.riesgos.auth.Controller;
import atc.riesgos.dao.service.SeguridadService;
import atc.riesgos.model.dto.Seguridad.SeguridadGetDTO;
import atc.riesgos.model.dto.Seguridad.SeguridadPostDTO;
import atc.riesgos.model.dto.Seguridad.SeguridadPutDTO;
import atc.riesgos.model.entity.Seguridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/seguridad")
@CrossOrigin(origins = "*", maxAge = 20000)

public class SeguridadController extends Controller {

    @Autowired
    SeguridadService seguridadService;

    @PostMapping("/registrar")
    public ResponseEntity<Seguridad> save (@Valid @RequestBody SeguridadPostDTO data){
        return seguridadService.create(data);
    }

    @GetMapping("/listar")
    public List<Seguridad> listSeguridad() {
        return seguridadService.listSeguridad();
    }

    @GetMapping("/mostrar/{id}")
    public SeguridadGetDTO findSeguridadByID(@PathVariable(value = "id") Long id) {
        return seguridadService.findSeguridadByID(id);
    }

    /* No lo probe */
    @PutMapping("/editar/{id}")
    public SeguridadGetDTO updateById (@PathVariable(value = "id") Long id, @Valid @RequestBody SeguridadPutDTO data){
        return seguridadService.updateById(id, data);
    }

    @GetMapping({"/{page}/{size}/{order}", "/{page}/{size}"})
    public Object filterBy(
            @PathVariable(value = "page") int page,
            @PathVariable(value = "size") int size,
            @PathVariable(value = "order", required = false) String order,
            HttpServletRequest request) {

        return createHQL(Seguridad.class).order(order)
                .map(request).paging(page, size);
    }

    @GetMapping("/agrupaPorArea")
    public List<Seguridad> groupByArea() {
        return seguridadService.groupByArea();
    }

}
