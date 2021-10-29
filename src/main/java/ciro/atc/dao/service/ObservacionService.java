package ciro.atc.dao.service;

import ciro.atc.model.dto.Observacion.ObservacionPostDTO;
import ciro.atc.model.entity.Observacion;

public interface ObservacionService {

    Observacion create(ObservacionPostDTO t, Long id);
}
