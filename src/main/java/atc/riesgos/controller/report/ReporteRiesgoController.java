package atc.riesgos.controller.report;

import atc.riesgos.dao.service.report.ReporteRiesgoService;
import atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa1.MapaInherente1DTO;
import atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa1.MapaInherenteResidual1DTO;
import atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa2.MapaInherenteResidual2DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;


@RestController
@RequestMapping("/v1/reporteriesgo/")
@CrossOrigin(origins = "*", maxAge = 20000)
public class ReporteRiesgoController {

    @Autowired
    ReporteRiesgoService reporteRiesgoService;

    @GetMapping("/mapainherenteresidual1")
    public MapaInherenteResidual1DTO getMapaInherente1(
            @RequestParam(value = "fechaDesde", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
            @RequestParam(value = "fechaHasta", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta) {
        return reporteRiesgoService.mapaInherenteResidual1(fechaDesde, fechaHasta);
    }


    @GetMapping("/mapainherenteresidual2")
    public ResponseEntity<MapaInherenteResidual2DTO> getMapaInherente2(@RequestParam(value = "procesoId", required = false) Long procesoId) {
        MapaInherenteResidual2DTO result = reporteRiesgoService.mapaInherenteResidual2(procesoId);
        return ResponseEntity.ok(result);
    }

}
