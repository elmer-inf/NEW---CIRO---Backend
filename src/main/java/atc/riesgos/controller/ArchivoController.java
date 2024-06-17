package atc.riesgos.controller;

import atc.riesgos.dao.service.ArchivoService;
import atc.riesgos.model.dto.Archivo.ArchivoPostDTO;
import atc.riesgos.model.entity.Archivo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/v1/archivo")
@CrossOrigin(origins = "*", maxAge = 20000)

public class ArchivoController {

    @Autowired
    ArchivoService archivoService;

    @PostMapping()
    public List<Archivo> saveRespaldosRegularizacion(@ModelAttribute ArchivoPostDTO data) {
        return archivoService.create(data);
    }

    @GetMapping("/listarArchivobyId/{id}")
    public List<Archivo> findAllByEvento(@PathVariable(value = "id") Long id) {
        return archivoService.findAllByEvento(id);
    }

    @PutMapping("/eliminar/{id}")
    public Archivo deleteById(@PathVariable(value = "id") Long id) {
        return archivoService.deleteByIdArchivo(id);
    }

}