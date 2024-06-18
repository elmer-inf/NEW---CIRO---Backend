package atc.riesgos.controller.report;

import atc.riesgos.dao.service.report.ReporteRiesgoService;
import atc.riesgos.model.dto.MatrizRiesgo.mapas.MapaInherenteDTO;
import atc.riesgos.model.entity.Archivo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/reporteriesgo/")
@CrossOrigin(origins = "*", maxAge = 20000)
public class ReporteRiesgoController {

    @Autowired
    ReporteRiesgoService reporteRiesgoService;

    @GetMapping("/mapainherente1")
    public MapaInherenteDTO getMapaInherente1(){
        return reporteRiesgoService.mapaInherente1();
    }


    @GetMapping("/mapainherente2")
    public ResponseEntity<Object[][]> getMapaInherente2(){
        Object[][] matrix = reporteRiesgoService.mapaInherente2();
        return ResponseEntity.ok(matrix);
    }

}
