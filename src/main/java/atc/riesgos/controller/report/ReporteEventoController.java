package atc.riesgos.controller.report;

import atc.riesgos.dao.service.report.ReporteEventoService;
import atc.riesgos.model.dto.report.ciro.eventos.FiltroReporteAuditoria;
import atc.riesgos.model.dto.report.ciro.eventos.FiltroReporteEvento;
import atc.riesgos.model.dto.report.ciro.eventos.ReporteEventoGralDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/v1/reporteevento/")
@CrossOrigin(origins = "*", maxAge = 20000)
public class ReporteEventoController {

    @Autowired
    ReporteEventoService reporteEventoService;

    @PostMapping("/eventoexcel")
    public byte[] reporteEventoExcel(HttpServletResponse response, @RequestBody FiltroReporteEvento filter, HttpServletRequest request) {

        byte[] excelContent = reporteEventoService.reporteEventoExcel(filter);
        response.setHeader("Content-Disposition", "attachment; filename=ReporteEventoRiesgo.xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        return excelContent;
    }

    @PostMapping("/evento")
    public List<ReporteEventoGralDTO> reporteEvento(@RequestBody FiltroReporteEvento filter) {
        return reporteEventoService.getReportEvento(filter);
    }

    @PostMapping("/auditextexcel")
    public byte[] reporteAuditExtExcel(HttpServletResponse response, @RequestBody FiltroReporteAuditoria filter, HttpServletRequest request) {

        byte[] excelContent = reporteEventoService.reporteAuditoriaExtExcel(filter);
        response.setHeader("Content-Disposition", "attachment; filename=ReporteAuditoriaExterna.xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        return excelContent;
    }

    @PostMapping("/auditintexcel")
    public byte[] reporteAuditIntExcel(HttpServletResponse response, @RequestBody FiltroReporteAuditoria filter, HttpServletRequest request) {

        byte[] excelContent = reporteEventoService.reporteAuditoriaIntExcel(filter);
        response.setHeader("Content-Disposition", "attachment; filename=ReporteAuditoriaInterna.xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        return excelContent;
    }

    @PostMapping("/asfiexcel")
    public byte[] reporteAsfiExcel(HttpServletResponse response, @RequestBody FiltroReporteAuditoria filter, HttpServletRequest request) {

        byte[] excelContent = reporteEventoService.reporteAsfiExcel(filter);
        response.setHeader("Content-Disposition", "attachment; filename=ReporteASFI.xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        return excelContent;
    }


}
