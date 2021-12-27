package atc.riesgos.controller;

import atc.riesgos.dao.service.ObservacionService;
import atc.riesgos.model.dto.Observacion.ObservacionGetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/observacion")
@CrossOrigin(origins = "*", maxAge = 20000)

public class ObservacionController {

    @Autowired
    ObservacionService observacionService;

    @GetMapping("/ultimaObservacionEvento/{id}")
    public ObservacionGetDTO getObservacionByById(@PathVariable(value = "id") Long id) {
        return observacionService.ultimaObservacionEvento(id);
    }

    @GetMapping("/ultimaObservacionRiesgo/{id}")
    public ObservacionGetDTO getObservacionByByIdRiesgo(@PathVariable(value = "id") Long id) {
        return observacionService.ultimaObservacionRiesgo(id);
    }

    @GetMapping("/ultimaObservacionOportunidad/{id}")
    public ObservacionGetDTO getObservacionByByIdOportunidad(@PathVariable(value = "id") Long id) {
        return observacionService.ultimaObservacionOportunidad(id);
    }
}
