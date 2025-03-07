package atc.riesgos.dao.service.report;

import atc.riesgos.model.dto.Reporte.oportunidades.FiltroReporteConfigOportunidad;
import atc.riesgos.model.dto.Reporte.oportunidades.mapas.MapaInherenteOportunidadDTO;
import atc.riesgos.model.dto.Reporte.oportunidades.mapas.conOportunidades.MapaInherenteConOportunidadesListDTO;

public interface ReporteOportunidadService {

    byte[] reporteConfigOportunidad(FiltroReporteConfigOportunidad filter);

    MapaInherenteOportunidadDTO mapaInherenteOportunidad(Long procesoId);

    MapaInherenteConOportunidadesListDTO getMapaInherenteConOportunidades(Long procesoId);
}
