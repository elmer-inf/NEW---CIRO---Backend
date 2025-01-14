package atc.riesgos.dao.service.report;

import atc.riesgos.model.dto.report.ciro.eventos.*;

public interface ReporteOportunidadService {

    byte[] reporteConfigOportunidad(FiltroReporteConfigOportunidad filter);
}
