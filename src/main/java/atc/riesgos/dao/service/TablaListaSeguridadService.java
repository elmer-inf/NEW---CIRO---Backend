package atc.riesgos.dao.service;

import atc.riesgos.model.dto.TablaListaSeguridad.TablaListaSeguridadPostDTO;
import atc.riesgos.model.entity.TablaListaSeguridad;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TablaListaSeguridadService {
    ResponseEntity<TablaListaSeguridad> create(TablaListaSeguridadPostDTO data);
    TablaListaSeguridad findByIdTabla(Long id);
    List<TablaListaSeguridad> listTabla();
}

