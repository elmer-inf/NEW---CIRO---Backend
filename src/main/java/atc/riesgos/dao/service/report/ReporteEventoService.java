package atc.riesgos.dao.service.report;

import atc.riesgos.model.dto.Reporte.eventos.FiltroReporteAuditoria;
import atc.riesgos.model.dto.Reporte.eventos.FiltroReporteConfigEvento;
import atc.riesgos.model.dto.Reporte.eventos.FiltroReporteEvento;
import atc.riesgos.model.dto.Reporte.eventos.ReporteEventoGralDTO;

import java.util.List;

public interface ReporteEventoService {

    byte[] reporteEventoExcel(FiltroReporteEvento filter);
    List<ReporteEventoGralDTO> getReportEvento(FiltroReporteEvento filter);

    byte[] reporteAuditoriaExtExcel(FiltroReporteAuditoria filter);
    byte[] reporteAuditoriaIntExcel(FiltroReporteAuditoria filter);
    byte[] reporteAsfiExcel(FiltroReporteAuditoria filter);
    byte[] reporteConfigEvento(FiltroReporteConfigEvento filter);
}
