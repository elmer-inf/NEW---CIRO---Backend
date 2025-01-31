package atc.riesgos.dao.service.report;

import atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa1.MapaInherenteResidual1DTO;
import atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa2.MapaInherenteResidual2DTO;
import atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa2.conRiesgos.MapaInherente2ConRiesgosListDTO;
import atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa2.conRiesgos.MapaResidual2ConRiesgosListDTO;
import atc.riesgos.model.dto.report.ciro.riesgos.FiltroReporteConfigRiesgo;
import atc.riesgos.model.dto.report.ciro.riesgos.FiltroReporteGerencialDTO;
import atc.riesgos.model.dto.report.ciro.riesgos.ResponseReporteGerencialDTO;

import java.util.Date;


public interface ReporteRiesgoService {

    MapaInherenteResidual1DTO mapaInherenteResidual1(Date fechaDesde, Date fechaHasta);
    MapaInherenteResidual2DTO mapaInherenteResidual2(Long procesoId);

    MapaInherente2ConRiesgosListDTO getMapaInherente2ConRiesgos(Long procesoId);
    MapaResidual2ConRiesgosListDTO getMapaResidual2ConRiesgos(Long procesoId);

    byte[] reporteConfigRiesgo(FiltroReporteConfigRiesgo filter);

    ResponseReporteGerencialDTO generarReporteGerencial(Date fechaDesde, Date fechaHasta);
}
