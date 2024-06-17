package atc.riesgos.dao.service;

import atc.riesgos.model.dto.Archivo.ArchivoPostDTO;
import atc.riesgos.model.entity.Archivo;
import java.util.List;

public interface ArchivoService {
    List<Archivo> create(ArchivoPostDTO data);
    List<Archivo> findAllByEvento(Long id);
    Archivo deleteByIdArchivo(Long id);
}