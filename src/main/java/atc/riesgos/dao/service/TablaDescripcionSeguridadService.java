package atc.riesgos.dao.service;

import atc.riesgos.model.dto.TablaDescripcionSeguridad.TablaDescripcionSeguridadGetDTO;
import atc.riesgos.model.dto.TablaDescripcionSeguridad.TablaDescripcionSeguridadPostDTO;
import atc.riesgos.model.dto.TablaDescripcionSeguridad.TablaDescripcionSeguridadPutDTO;
import atc.riesgos.model.entity.TablaDescripcionSeguridad;
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

