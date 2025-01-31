package atc.riesgos.controller.report;

import atc.riesgos.dao.service.report.ReporteOportunidadService;
import atc.riesgos.model.dto.report.ciro.oportunidades.FiltroReporteConfigOportunidad;
import org.springframework.beans.factory.annotation.Autowired;
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

}
