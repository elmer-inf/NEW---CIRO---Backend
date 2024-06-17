package atc.riesgos.dao.service;

import atc.riesgos.model.dto.ArchivoEveRecurrente.ArchivoEveRecurrentePostDTO;
import atc.riesgos.model.entity.ArchivoEveRecurrente;
import java.util.List;

public interface ArchivoEveRecurrenteService {
    List<ArchivoEveRecurrente> create(ArchivoEveRecurrentePostDTO data);
    List<ArchivoEveRecurrente> findAllByEvento(Long id);
    ArchivoEveRecurrente deleteByIdArchivo(Long id);
}