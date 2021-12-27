package atc.riesgos.dao.service;

import atc.riesgos.model.dto.Observacion.ObservacionGetDTO;
import atc.riesgos.model.dto.Observacion.ObservacionPostDTO;
import atc.riesgos.model.entity.Observacion;

public interface ObservacionService {

    Observacion create(ObservacionPostDTO t, Long id);
    ObservacionGetDTO ultimaObservacionEvento(Long id);
    ObservacionGetDTO ultimaObservacionRiesgo(Long id);
    ObservacionGetDTO ultimaObservacionOportunidad(Long id);
}
