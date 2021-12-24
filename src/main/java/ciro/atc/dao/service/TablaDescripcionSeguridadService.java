package ciro.atc.dao.service;

import ciro.atc.model.dto.TablaDescripcionSeguridad.TablaDescripcionSeguridadGetDTO;
import ciro.atc.model.dto.TablaDescripcionSeguridad.TablaDescripcionSeguridadPostDTO;
import ciro.atc.model.dto.TablaDescripcionSeguridad.TablaDescripcionSeguridadPutDTO;
import ciro.atc.model.entity.TablaDescripcionSeguridad;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TablaDescripcionSeguridadService {

    ResponseEntity<TablaDescripcionSeguridad> create(TablaDescripcionSeguridadPostDTO data);
    List<TablaDescripcionSeguridad> listTablaDescripcionSeguridad();
    List<TablaDescripcionSeguridad> findTablaNivel1(Long id);
    ResponseEntity<TablaDescripcionSeguridadGetDTO> updateById (Long id, TablaDescripcionSeguridadPutDTO data);
    TablaDescripcionSeguridadGetDTO findTablaDescripcionByID(Long id);
    TablaDescripcionSeguridad findByIdTablaDesc(Long id);

}

