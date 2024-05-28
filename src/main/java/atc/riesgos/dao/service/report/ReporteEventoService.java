package atc.riesgos.dao.service.report;

import atc.riesgos.model.dto.report.ciro.eventos.FiltroReporteAuditoria;
import atc.riesgos.model.dto.report.ciro.eventos.FiltroReporteConfigEvento;
import atc.riesgos.model.dto.report.ciro.eventos.FiltroReporteEvento;
import atc.riesgos.model.dto.report.ciro.eventos.ReporteEventoGralDTO;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ReporteEventoService {

    byte[] reporteEventoExcel(FiltroReporteEvento filter);
    List<ReporteEventoGralDTO> getReportEvento(FiltroReporteEvento filter);

    byte[] reporteAuditoriaExtExcel(FiltroReporteAuditoria filter);
    byte[] reporteAuditoriaIntExcel(FiltroReporteAuditoria filter);
    byte[] reporteAsfiExcel(FiltroReporteAuditoria filter);
    byte[] reporteConfigEvento(FiltroReporteConfigEvento filter);
}
