package atc.riesgos.dao.service.report;

import atc.riesgos.model.dto.Reporte.oportunidades.FiltroReporteConfigOportunidad;

public interface ReporteOportunidadService {

    byte[] reporteConfigOportunidad(FiltroReporteConfigOportunidad filter);
}
