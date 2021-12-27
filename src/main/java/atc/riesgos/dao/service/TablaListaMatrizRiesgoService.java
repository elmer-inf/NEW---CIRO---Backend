package atc.riesgos.dao.service;

import atc.riesgos.model.dto.TablaListaMatrizRiesgo.TablaListaMatrizRiesgoPostDTO;
import atc.riesgos.model.entity.TablaListaMatrizRiesgo;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TablaListaMatrizRiesgoService {
    ResponseEntity<TablaListaMatrizRiesgo> create(TablaListaMatrizRiesgoPostDTO data);
    TablaListaMatrizRiesgo findByIdTabla(Long id);
    List<TablaListaMatrizRiesgo> listTabla();
    /*
    TablaListaGetDTO updateById (Long id, TablaListaPutDTO data);
    TablaListaGetDTO findTablaListaByID(Long id);
    TablaLista deleteById(Long id);*/

}

