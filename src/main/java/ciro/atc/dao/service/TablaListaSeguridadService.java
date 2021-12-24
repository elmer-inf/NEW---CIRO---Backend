package ciro.atc.dao.service;

import ciro.atc.model.dto.TablaListaSeguridad.TablaListaSeguridadPostDTO;
import ciro.atc.model.entity.TablaListaSeguridad;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TablaListaSeguridadService {
    ResponseEntity<TablaListaSeguridad> create(TablaListaSeguridadPostDTO data);
    TablaListaSeguridad findByIdTabla(Long id);
    List<TablaListaSeguridad> listTabla();
}

