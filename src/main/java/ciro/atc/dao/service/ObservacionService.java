package ciro.atc.dao.service;

import ciro.atc.model.dto.Observacion.ObservacionGetDTO;
import ciro.atc.model.dto.Observacion.ObservacionPostDTO;
import ciro.atc.model.entity.Observacion;

public interface ObservacionService {

    Observacion create(ObservacionPostDTO t, Long id);
    ObservacionGetDTO ultimaObservacionEvento(Long id);
    ObservacionGetDTO ultimaObservacionRiesgo(Long id);
}
