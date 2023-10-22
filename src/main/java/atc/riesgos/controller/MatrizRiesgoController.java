package atc.riesgos.controller;

import atc.riesgos.dao.service.MatrizRiesgoService;
import atc.riesgos.model.dto.MatrizRiesgo.MatrizRiesgoPutDTOevaluacion;
import atc.riesgos.auth.Controller;
import atc.riesgos.model.dto.MatrizRiesgo.MatrizRiesgoGetDTO;
import atc.riesgos.model.dto.MatrizRiesgo.MatrizRiesgoPostDTO;
import atc.riesgos.model.dto.MatrizRiesgo.MatrizRiesgoPutDTO;
import atc.riesgos.model.entity.EventoRiesgo;
import atc.riesgos.model.entity.MatrizRiesgo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/matrizRiesgo")
@CrossOrigin(origins = "*", maxAge = 20000)

public class MatrizRiesgoController extends Controller {

    @Autowired
    MatrizRiesgoService matrizRiesgoService;

    @PostMapping("/registrar")
    public ResponseEntity<MatrizRiesgo> save (@Valid @RequestBody MatrizRiesgoPostDTO data){
        return matrizRiesgoService.create(data);
    }

    @GetMapping("/listar")
    public List<MatrizRiesgo> listMatrizRiesgo() {
        return matrizRiesgoService.listMatrizRiesgo();
    }

    @GetMapping("/mostrar/{id}")
    public MatrizRiesgoGetDTO findMatrizRiesgoByID(@PathVariable(value = "id") Long id) {
        return matrizRiesgoService.findMatrizRiesgoByID(id);
    }

    @PutMapping("/evaluaRiesgo/{id}")
    public ResponseEntity<MatrizRiesgo> evaluaRiesgo (@PathVariable(value = "id") Long id, @Valid @RequestBody MatrizRiesgoPutDTOevaluacion data){
        return matrizRiesgoService.evaluaRiesgo(id, data);
    }

    @GetMapping("/generaCodigo/{id}")
    public String generaCodigo (@PathVariable(value = "id") Long id){
        return matrizRiesgoService.generaCodigo(id);
    }

    @PutMapping("/editar/{id}")
    public MatrizRiesgoGetDTO updateById (@PathVariable(value = "id") Long id, @Valid @RequestBody MatrizRiesgoPutDTO data){
        return matrizRiesgoService.updateById(id, data);
    }

    @PutMapping("/eliminar/{id}")
    public MatrizRiesgo deleteById(@PathVariable(value = "id") Long id) {
        return matrizRiesgoService.deleteByIdRiesgo(id);
    }

    @GetMapping({"/{page}/{size}/{order}", "/{page}/{size}"})
    public Object filterBy(
            @PathVariable(value = "page") int page,
            @PathVariable(value = "size") int size,
            @PathVariable(value = "order", required = false) String order,
            HttpServletRequest request) {

        return createHQL(MatrizRiesgo.class).order(order)
                .map(request).paging(page, size);
    }

}
