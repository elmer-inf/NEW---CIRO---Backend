package ciro.atc.dao.service;

import ciro.atc.model.dto.TablaListaMatrizRiesgo.TablaListaMatrizRiesgoPostDTO;
import ciro.atc.model.entity.TablaListaMatrizRiesgo;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TablaListaMatrizRiesgoService {
    ResponseEntity<TablaListaMatrizRiesgo> create(TablaListaMatrizRiesgoPostDTO data);
    TablaListaMatrizRiesgo findByIdTabla(Long id);
    List<TablaListaMatrizRiesgo> listTabla();
    /*
    TablaListaGetDTO updateById (Long id, TablaListaPutDTO data);
    TablaListaGetDTO findTablaListaByID(Long id);
    TablaLista deleteById(Long id);*/

}

