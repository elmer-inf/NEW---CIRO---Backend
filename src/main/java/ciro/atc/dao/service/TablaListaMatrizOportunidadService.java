package ciro.atc.dao.service;

import ciro.atc.model.dto.TablaListaMatrizOportunidad.TablaListaMatrizOportunidadPostDTO;
import ciro.atc.model.entity.TablaDescripcion;
import ciro.atc.model.entity.TablaListaMatrizOportunidad;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TablaListaMatrizOportunidadService {
    ResponseEntity<TablaListaMatrizOportunidad> create(TablaListaMatrizOportunidadPostDTO data);
    TablaListaMatrizOportunidad findByIdTabla(Long id);
    List<TablaListaMatrizOportunidad> listTabla();
}

