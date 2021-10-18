package ciro.atc.dao.service;

import ciro.atc.model.dto.TablaLista.TablaListaGetDTO;
import ciro.atc.model.dto.TablaLista.TablaListaPostDTO;
import ciro.atc.model.dto.TablaLista.TablaListaPutDTO;
import ciro.atc.model.entity.TablaLista;

import java.util.List;

public interface TablaListaService {
    TablaLista create(TablaListaPostDTO t);
    TablaLista findByIdTabla(Long id);
    List<TablaLista> listTabla();
    TablaListaGetDTO updateById (Long id, TablaListaPutDTO data);
    TablaListaGetDTO findTablaListaByID(Long id);
    TablaLista deleteById(Long id);

}

