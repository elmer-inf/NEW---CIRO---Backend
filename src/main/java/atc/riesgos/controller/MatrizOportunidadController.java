package atc.riesgos.controller;

import atc.riesgos.auth.Controller;
import atc.riesgos.model.entity.MatrizOportunidad;
import atc.riesgos.dao.service.MatrizOportunidadService;
import atc.riesgos.model.dto.MatrizOportunidad.MatrizOportunidadGetDTO;
import atc.riesgos.model.dto.MatrizOportunidad.MatrizOportunidadPostDTO;
import atc.riesgos.model.dto.MatrizOportunidad.MatrizOportunidadPutDTO;
import atc.riesgos.model.dto.MatrizOportunidad.MatrizOportunidadPutDTOevaluacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/matrizOportunidad")
@CrossOrigin(origins = "*", maxAge = 20000)

public class MatrizOportunidadController extends Controller {

    @Autowired
    MatrizOportunidadService matrizOportunidadService;

    @PostMapping("/registrar")
    public ResponseEntity<MatrizOportunidad> save (@Valid @RequestBody MatrizOportunidadPostDTO data){
        return matrizOportunidadService.create(data);
    }

    @GetMapping("/listar")
    public List<MatrizOportunidad> listMatrizOportunidad() {
        return matrizOportunidadService.listMatrizOportunidad();
    }

    @GetMapping("/mostrar/{id}")
    public MatrizOportunidadGetDTO findMatrizOportunidadByID(@PathVariable(value = "id") Long id) {
        return matrizOportunidadService.findMatrizOportunidadByID(id);
    }

    @PutMapping("/evaluaOportunidad/{id}")
    public ResponseEntity<MatrizOportunidad> evaluaOportunidad (@PathVariable(value = "id") Long id, @Valid @RequestBody MatrizOportunidadPutDTOevaluacion data){
        return matrizOportunidadService.evaluaOportunidad(id, data);
    }

    /* No lo probe */
    @PutMapping("/editar/{id}")
    public MatrizOportunidadGetDTO updateById (@PathVariable(value = "id") Long id, @Valid @RequestBody MatrizOportunidadPutDTO data){
        return matrizOportunidadService.updateById(id, data);
    }


    @GetMapping({"/{page}/{size}/{order}", "/{page}/{size}"})
    public Object filterBy(
            @PathVariable(value = "page") int page,
            @PathVariable(value = "size") int size,
            @PathVariable(value = "order", required = false) String order,
            HttpServletRequest request) {

        return createHQL(MatrizOportunidad.class).order(order)
                .map(request).paging(page, size);
    }



}
