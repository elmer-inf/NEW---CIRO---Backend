package ciro.atc.dao.service;

import ciro.atc.model.dto.TablaDescripcionMatrizOportunidad.TablaDescripcionMatrizOportunidadGetDTO;
import ciro.atc.model.dto.TablaDescripcionMatrizOportunidad.TablaDescripcionMatrizOportunidadGetDTO2;
import ciro.atc.model.dto.TablaDescripcionMatrizOportunidad.TablaDescripcionMatrizOportunidadPostDTO;
import ciro.atc.model.dto.TablaDescripcionMatrizOportunidad.TablaDescripcionMatrizOportunidadPutDTO;
import ciro.atc.model.entity.TablaDescripcionMatrizOportunidad;
import ciro.atc.model.entity.TablaListaMatrizOportunidad;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TablaDescripcionMatrizOportunidadService {

    ResponseEntity<TablaDescripcionMatrizOportunidad> create(TablaDescripcionMatrizOportunidadPostDTO data);
    List<TablaDescripcionMatrizOportunidad> listTablaDescripcionMatrizO();
    List<TablaDescripcionMatrizOportunidad> findTablaNivel1(Long id);
    List<TablaDescripcionMatrizOportunidad> findTablaNivel2(Long id, int id2);
    ResponseEntity<TablaDescripcionMatrizOportunidadGetDTO> updateById (Long id, TablaDescripcionMatrizOportunidadPutDTO data);
    TablaDescripcionMatrizOportunidadGetDTO findTablaDescripcionByID(Long id);
    TablaDescripcionMatrizOportunidadGetDTO2 findTablaDescripcionByID2(Long id);
    TablaDescripcionMatrizOportunidad findByIdTablaDesc(Long id);

}

