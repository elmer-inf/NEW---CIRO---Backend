package atc.riesgos.dao.service;

import atc.riesgos.model.dto.TablaDescripcion.TablaDescripcionGetDTO;
import atc.riesgos.model.dto.TablaDescripcion.TablaDescripcionGetDTO2;
import atc.riesgos.model.dto.TablaDescripcion.TablaDescripcionPostDTO;
import atc.riesgos.model.dto.TablaDescripcion.TablaDescripcionPutDTO;
import atc.riesgos.model.entity.TablaDescripcion;

import java.util.List;

public interface TablaDescripcionService {

    TablaDescripcion create(TablaDescripcionPostDTO t);
    List<TablaDescripcion> listTablaDescripcion();
    List<TablaDescripcion> findTablaNivel1(Long id);
    List<TablaDescripcion> findTablaNivel2(Long id, int id2);
    List<TablaDescripcion> findTablaNivel3(Long id, int id2, int id3);
    TablaDescripcionGetDTO updateById (Long id, TablaDescripcionPutDTO data);
    TablaDescripcionGetDTO findTablaDescripcionByID(Long id);
    TablaDescripcionGetDTO2 findTablaDescripcionByID2(Long id);
    TablaDescripcion deleteById(Long id);

    TablaDescripcion findByIdTablaDesc(Long id);

}

