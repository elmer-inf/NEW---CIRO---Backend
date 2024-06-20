package atc.riesgos.dao.service.report;

import atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa1.MapaInherente1DTO;
import atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa1.MapaInherenteResidual1DTO;
import atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa2.MapaInherenteResidual2DTO;
import java.util.Date;


public interface ReporteRiesgoService {

    MapaInherenteResidual1DTO mapaInherenteResidual1(Date fechaDesde, Date fechaHasta);

    MapaInherenteResidual2DTO mapaInherenteResidual2(Long procesoId);
}
