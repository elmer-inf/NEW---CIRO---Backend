package atc.riesgos.dao.service.report;

import atc.riesgos.model.dto.report.ciro.DatesForReport;
import atc.riesgos.model.dto.report.ciro.ReportIDTO;
import atc.riesgos.model.dto.report.ciro.eventos.FiltroReporteEvento;
import atc.riesgos.model.dto.report.ciro.eventos.ReporteEventoGralDTO;

import java.util.List;

public interface ReporteEventoService {

    byte[] reporteEventoExcel(FiltroReporteEvento filter);
    List<ReporteEventoGralDTO> getReportEvento(FiltroReporteEvento filter);
}
