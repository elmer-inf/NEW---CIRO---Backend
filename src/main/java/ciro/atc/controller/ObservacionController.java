package ciro.atc.controller;

import ciro.atc.dao.service.ObservacionService;
import ciro.atc.model.dto.Observacion.ObservacionGetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/observacion")
@CrossOrigin(origins = "*", maxAge = 20000)

public class ObservacionController {

    @Autowired
    ObservacionService observacionService;

    @GetMapping("/ultimaObservacion/{id}")
    public ObservacionGetDTO getObservacionByById(@PathVariable(value = "id") Long id) {
        return observacionService.ultimaObservacionEvento(id);
    }

}
