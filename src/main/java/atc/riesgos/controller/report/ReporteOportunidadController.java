package atc.riesgos.controller.report;

import atc.riesgos.dao.service.report.ReporteOportunidadService;
import atc.riesgos.model.dto.Reporte.oportunidades.FiltroReporteConfigOportunidad;
import atc.riesgos.model.dto.Reporte.oportunidades.mapas.MapaInherenteOportunidadDTO;
import atc.riesgos.model.dto.Reporte.oportunidades.mapas.conOportunidades.MapaInherenteConOportunidadesListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/v1/reporteoportunidad/")
@CrossOrigin(origins = "*", maxAge = 20000)
public class ReporteOportunidadController {

    @Autowired
    ReporteOportunidadService reporteOportunidadService;

    @PostMapping("/oportunidadconfigexcel")
    public byte[] reporteOportunidadExcel(HttpServletResponse response, @RequestBody FiltroReporteConfigOportunidad filter) {
        byte[] excelContent = reporteOportunidadService.reporteConfigOportunidad(filter);
        response.setHeader("Content-Disposition", "attachment; filename=ReporteConfiguradoOportunidad.xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        return excelContent;
    }

    @GetMapping("/mapainherente")
    public ResponseEntity<MapaInherenteOportunidadDTO> mapaInherenteOportunidad(@RequestParam(value = "procesoId", required = false) Long procesoId) {
        MapaInherenteOportunidadDTO result = reporteOportunidadService.mapaInherenteOportunidad(procesoId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/mapainherenteconoportunidades")
    public ResponseEntity<MapaInherenteConOportunidadesListDTO> getMapaInherenteConOportunidades(@RequestParam(required = false) Long procesoId) {
        MapaInherenteConOportunidadesListDTO dto = reporteOportunidadService.getMapaInherenteConOportunidades(procesoId);
        return ResponseEntity.ok(dto);
    }

}
