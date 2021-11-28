package ciro.atc.dao.service;

import ciro.atc.model.dto.TablaDescripcionMatrizRiesgo.TablaDescripcionMatrizRiesgoGetDTO;
import ciro.atc.model.dto.TablaDescripcionMatrizRiesgo.TablaDescripcionMatrizRiesgoPostDTO;
import ciro.atc.model.dto.TablaDescripcionMatrizRiesgo.TablaDescripcionMatrizRiesgoPutDTO;
import ciro.atc.model.entity.TablaDescripcionMatrizRiesgo;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TablaDescripcionMatrizRiesgoService {

    ResponseEntity<TablaDescripcionMatrizRiesgo> create(TablaDescripcionMatrizRiesgoPostDTO data);
    List<TablaDescripcionMatrizRiesgo> listTablaDescripcionMatrizR();
    List<TablaDescripcionMatrizRiesgo> findTablaNivel1(Long id);
    ResponseEntity<TablaDescripcionMatrizRiesgoGetDTO> updateById (Long id, TablaDescripcionMatrizRiesgoPutDTO data);
    TablaDescripcionMatrizRiesgoGetDTO findTablaDescripcionByID(Long id);

   /*
    TablaDescripcionGetDTO2 findTablaDescripcionByID2(Long id);
    TablaDescripcion deleteById(Long id);

    TablaDescripcion findByIdTablaDesc(Long id);*/

}

