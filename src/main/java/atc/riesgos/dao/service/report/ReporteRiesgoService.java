package atc.riesgos.dao.service.report;

import atc.riesgos.model.dto.MatrizRiesgo.mapas.MapaInherenteDTO;
import atc.riesgos.model.dto.MatrizRiesgo.mapas.PerfilRiesgoInherenteDTO;

public interface ReporteRiesgoService {

    MapaInherenteDTO mapaInherente1();

    Object[][] mapaInherente2();
}
