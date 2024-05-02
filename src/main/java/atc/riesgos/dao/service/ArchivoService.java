package atc.riesgos.dao.service;

import atc.riesgos.model.dto.Archivo.ArchivoPostDTO;
import atc.riesgos.model.dto.Archivo.ArchivoPostDTOv2;
import atc.riesgos.model.entity.Archivo;
import atc.riesgos.model.entity.EventoRiesgo;
import atc.riesgos.model.entity.TablaDescripcionMatrizRiesgo;

import java.util.List;

public interface ArchivoService {

    Archivo create(ArchivoPostDTO t);
    List<Archivo> create(ArchivoPostDTOv2 data);
    List<Archivo> findAllByEvento(Long id);
    Archivo deleteByIdArchivo(Long id);
}
