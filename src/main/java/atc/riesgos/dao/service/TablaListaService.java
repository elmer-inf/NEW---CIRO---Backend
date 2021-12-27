package atc.riesgos.dao.service;

import atc.riesgos.model.dto.TablaLista.TablaListaGetDTO;
import atc.riesgos.model.dto.TablaLista.TablaListaPostDTO;
import atc.riesgos.model.dto.TablaLista.TablaListaPutDTO;
import atc.riesgos.model.entity.TablaLista;

import java.util.List;

public interface TablaListaService {
    TablaLista create(TablaListaPostDTO t);
    TablaLista findByIdTabla(Long id);
    List<TablaLista> listTabla();
    TablaListaGetDTO updateById (Long id, TablaListaPutDTO data);
    TablaListaGetDTO findTablaListaByID(Long id);
    TablaLista deleteById(Long id);

}

