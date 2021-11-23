package ciro.atc.dao.service;

import ciro.atc.model.dto.TablaDescripcion.TablaDescripcionGetDTO;
import ciro.atc.model.dto.TablaDescripcion.TablaDescripcionGetDTO2;
import ciro.atc.model.dto.TablaDescripcion.TablaDescripcionPostDTO;
import ciro.atc.model.dto.TablaDescripcion.TablaDescripcionPutDTO;
import ciro.atc.model.dto.TablaDescripcionMatrizRiesgo.TablaDescripcionMatrizRiesgoPostDTO;
import ciro.atc.model.entity.TablaDescripcion;
import ciro.atc.model.entity.TablaDescripcionMatrizRiesgo;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TablaDescripcionMatrizRiesgoService {

    ResponseEntity<TablaDescripcionMatrizRiesgo> create(TablaDescripcionMatrizRiesgoPostDTO data);
    List<TablaDescripcionMatrizRiesgo> listTablaDescripcionMatrizR();
    List<TablaDescripcionMatrizRiesgo> findTablaNivel1(Long id);
   /*

    TablaDescripcionGetDTO updateById (Long id, TablaDescripcionPutDTO data);
    TablaDescripcionGetDTO findTablaDescripcionByID(Long id);
    TablaDescripcionGetDTO2 findTablaDescripcionByID2(Long id);
    TablaDescripcion deleteById(Long id);

    TablaDescripcion findByIdTablaDesc(Long id);*/

}

