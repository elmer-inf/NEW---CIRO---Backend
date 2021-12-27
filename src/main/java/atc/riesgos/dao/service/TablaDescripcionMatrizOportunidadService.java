package atc.riesgos.dao.service;

import atc.riesgos.model.dto.TablaDescripcionMatrizOportunidad.TablaDescripcionMatrizOportunidadPutDTO;
import atc.riesgos.model.dto.TablaDescripcionMatrizOportunidad.TablaDescripcionMatrizOportunidadGetDTO;
import atc.riesgos.model.dto.TablaDescripcionMatrizOportunidad.TablaDescripcionMatrizOportunidadGetDTO2;
import atc.riesgos.model.dto.TablaDescripcionMatrizOportunidad.TablaDescripcionMatrizOportunidadPostDTO;
import atc.riesgos.model.entity.TablaDescripcionMatrizOportunidad;
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

