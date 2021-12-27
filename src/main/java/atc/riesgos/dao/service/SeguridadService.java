package atc.riesgos.dao.service;

import atc.riesgos.model.dto.Seguridad.SeguridadGetDTO;
import atc.riesgos.model.dto.Seguridad.SeguridadPostDTO;
import atc.riesgos.model.dto.Seguridad.SeguridadPutDTO;
import atc.riesgos.model.entity.Seguridad;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SeguridadService {

    ResponseEntity<Seguridad> create(SeguridadPostDTO t);
    List<Seguridad> listSeguridad();
    SeguridadGetDTO findSeguridadByID(Long id);
    Seguridad findByIdSeguridad(Long id);
    SeguridadGetDTO updateById (Long id, SeguridadPutDTO data);
    List<Seguridad> groupByArea();

}
