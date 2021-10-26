package ciro.atc.dao.service;

import ciro.atc.model.dto.TablaDescripcion.TablaDescripcionGetDTO;
import ciro.atc.model.dto.TablaDescripcion.TablaDescripcionGetDTO2;
import ciro.atc.model.dto.TablaDescripcion.TablaDescripcionPostDTO;
import ciro.atc.model.dto.TablaDescripcion.TablaDescripcionPutDTO;
import ciro.atc.model.dto.TablaLista.TablaListaGetDTO;
import ciro.atc.model.dto.TablaLista.TablaListaPutDTO;
import ciro.atc.model.entity.TablaDescripcion;
import ciro.atc.model.entity.TablaLista;

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

