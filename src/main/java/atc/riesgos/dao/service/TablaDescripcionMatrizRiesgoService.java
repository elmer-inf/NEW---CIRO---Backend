package atc.riesgos.dao.service;

import atc.riesgos.model.dto.TablaDescripcionMatrizRiesgo.TablaDescripcionMatrizRiesgoGetDTO;
import atc.riesgos.model.dto.TablaDescripcionMatrizRiesgo.TablaDescripcionMatrizRiesgoPostDTO;
import atc.riesgos.model.dto.TablaDescripcionMatrizRiesgo.TablaDescripcionMatrizRiesgoPutDTO;
import atc.riesgos.model.entity.TablaDescripcionMatrizRiesgo;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TablaDescripcionMatrizRiesgoService {

    ResponseEntity<TablaDescripcionMatrizRiesgo> create(TablaDescripcionMatrizRiesgoPostDTO data);
    List<TablaDescripcionMatrizRiesgo> listTablaDescripcionMatrizR();
    List<TablaDescripcionMatrizRiesgo> findTablaNivel1(Long id);
    ResponseEntity<TablaDescripcionMatrizRiesgoGetDTO> updateById (Long id, TablaDescripcionMatrizRiesgoPutDTO data);
    TablaDescripcionMatrizRiesgoGetDTO findTablaDescripcionByID(Long id);
    TablaDescripcionMatrizRiesgo findByIdTablaDesc(Long id);
    TablaDescripcionMatrizRiesgo deleteById(Long id);
}

