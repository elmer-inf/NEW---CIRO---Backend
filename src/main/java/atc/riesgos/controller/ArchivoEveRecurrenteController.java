package atc.riesgos.controller;

import atc.riesgos.dao.service.ArchivoEveRecurrenteService;
import atc.riesgos.dao.service.ArchivoService;
import atc.riesgos.model.dto.Archivo.ArchivoPostDTO;
import atc.riesgos.model.dto.ArchivoEveRecurrente.ArchivoEveRecurrentePostDTO;
import atc.riesgos.model.entity.Archivo;
import atc.riesgos.model.entity.ArchivoEveRecurrente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/archivoeverecurrente")
@CrossOrigin(origins = "*", maxAge = 20000)

public class ArchivoEveRecurrenteController {

    @Autowired
    ArchivoEveRecurrenteService archivoEveRecurrenteService;

    @PostMapping()
    public List<ArchivoEveRecurrente> saveRespaldosRegularizacion(@ModelAttribute ArchivoEveRecurrentePostDTO data) {
        return archivoEveRecurrenteService.create(data);
    }

    @GetMapping("/listarArchivobyId/{id}")
    public List<ArchivoEveRecurrente> findAllByEvento(@PathVariable(value = "id") Long id) {
        return archivoEveRecurrenteService.findAllByEvento(id);
    }

    @PutMapping("/eliminar/{id}")
    public ArchivoEveRecurrente deleteById(@PathVariable(value = "id") Long id) {
        return archivoEveRecurrenteService.deleteByIdArchivo(id);
    }

}