package atc.riesgos.controller;

import atc.riesgos.dao.service.ArchivoService;
import atc.riesgos.model.dto.Archivo.ArchivoPostDTO;
import atc.riesgos.model.dto.Archivo.ArchivoPostDTOv2;
import atc.riesgos.model.entity.Archivo;
import atc.riesgos.model.entity.TablaDescripcionMatrizRiesgo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/archivo")
@CrossOrigin(origins = "*", maxAge = 20000)

public class ArchivoController {

    @Autowired
    ArchivoService archivoService;

    @PostMapping("/registrar")
    public Archivo save (@Valid @RequestBody ArchivoPostDTO data){
        return archivoService.create(data);
    }

    @PostMapping()
    public List<Archivo> saveRespaldosRegularizacion(@ModelAttribute ArchivoPostDTOv2 data) {
      return archivoService.create(data);
    }

    @GetMapping("/listarArchivobyId/{id}")
    public List<Archivo> findAllByEvento(@PathVariable(value = "id") Long id) {
        return archivoService.findAllByEvento(id);
    }

}
