package atc.riesgos.dao.service;

import atc.riesgos.model.dto.TablaListaMatrizOportunidad.TablaListaMatrizOportunidadPostDTO;
import atc.riesgos.model.entity.TablaListaMatrizOportunidad;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TablaListaMatrizOportunidadService {
    ResponseEntity<TablaListaMatrizOportunidad> create(TablaListaMatrizOportunidadPostDTO data);
    TablaListaMatrizOportunidad findByIdTabla(Long id);
    List<TablaListaMatrizOportunidad> listTabla();
}

