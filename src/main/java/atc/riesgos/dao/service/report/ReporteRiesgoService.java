package atc.riesgos.dao.service.report;

import atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa1.MapaInherenteResidual1DTO;
import atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa2.MapaInherenteResidual2DTO;

public interface ReporteRiesgoService {

    MapaInherenteResidual1DTO mapaInherenteResidual1();

    MapaInherenteResidual2DTO mapaInherenteResidual2(Long procesoId);
}
