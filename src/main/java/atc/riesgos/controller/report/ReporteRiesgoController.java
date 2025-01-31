package atc.riesgos.controller.report;

import atc.riesgos.dao.service.report.ReporteRiesgoService;
import atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa1.MapaInherenteResidual1DTO;
import atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa2.MapaInherenteResidual2DTO;
import atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa2.conRiesgos.MapaInherente2ConRiesgosListDTO;
import atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa2.conRiesgos.MapaResidual2ConRiesgosListDTO;
import atc.riesgos.model.dto.report.ciro.riesgos.FiltroReporteConfigRiesgo;
import atc.riesgos.model.dto.report.ciro.riesgos.ResponseReporteGerencialDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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


    @GetMapping("/mapainherente2conriesgos")
    public ResponseEntity<MapaInherente2ConRiesgosListDTO> getMapaInherente2ConRiesgos(@RequestParam(required = false) Long procesoId) {
        MapaInherente2ConRiesgosListDTO dto = reporteRiesgoService.getMapaInherente2ConRiesgos(procesoId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/maparesidual2conriesgos")
    public ResponseEntity<MapaResidual2ConRiesgosListDTO> getMapaResidual2ConRiesgos(@RequestParam(required = false) Long procesoId) {
        MapaResidual2ConRiesgosListDTO dto = reporteRiesgoService.getMapaResidual2ConRiesgos(procesoId);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/riesgoconfigexcel")
    public byte[] reporteRiesgoExcel(HttpServletResponse response, @RequestBody FiltroReporteConfigRiesgo filter) {
        byte[] excelContent = reporteRiesgoService.reporteConfigRiesgo(filter);
        response.setHeader("Content-Disposition", "attachment; filename=ReporteConfiguradoRiesgo.xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        return excelContent;
    }

    @GetMapping("/reportegerencial")
    public ResponseReporteGerencialDTO generarReporteGerencial(
            @RequestParam(value = "fechaDesde", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
            @RequestParam(value = "fechaHasta", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta) {
        return reporteRiesgoService.generarReporteGerencial(fechaDesde, fechaHasta);
    }
}
