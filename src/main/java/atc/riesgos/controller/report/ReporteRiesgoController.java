package atc.riesgos.controller.report;

import atc.riesgos.dao.service.MatrizRiesgoService;
import atc.riesgos.dao.service.report.ReporteEventoService;
import atc.riesgos.dao.service.report.ReporteRiesgoService;
import atc.riesgos.model.dto.MatrizRiesgo.mapas.MapaInherenteDTO;
import atc.riesgos.model.dto.MatrizRiesgo.mapas.PerfilRiesgoInherenteDTO;
import atc.riesgos.model.dto.report.ciro.eventos.FiltroReporteAuditoria;
import atc.riesgos.model.dto.report.ciro.eventos.FiltroReporteConfigEvento;
import atc.riesgos.model.dto.report.ciro.eventos.FiltroReporteEvento;
import atc.riesgos.model.dto.report.ciro.eventos.ReporteEventoGralDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/v1/reporteriesgo/")
@CrossOrigin(origins = "*", maxAge = 20000)
public class ReporteRiesgoController {

    @Autowired
    ReporteRiesgoService reporteRiesgoService;

    @GetMapping("/inherente")
    public MapaInherenteDTO getValoracionExposicionInherente(){
        return reporteRiesgoService.getValoracionExposicionInherente();
    }


    @GetMapping("/inherente2")
    public ResponseEntity<Object[][]> getRiskMatrix(){
        Object[][] matrix = reporteRiesgoService.createRiskMatrix();
        return ResponseEntity.ok(matrix);
    }
}
