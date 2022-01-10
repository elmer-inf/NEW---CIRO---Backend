package atc.riesgos.dao.service;

import atc.riesgos.model.entity.MatrizOportunidad;
import atc.riesgos.model.dto.MatrizOportunidad.MatrizOportunidadGetDTO;
import atc.riesgos.model.dto.MatrizOportunidad.MatrizOportunidadPostDTO;
import atc.riesgos.model.dto.MatrizOportunidad.MatrizOportunidadPutDTO;
import atc.riesgos.model.dto.MatrizOportunidad.MatrizOportunidadPutDTOevaluacion;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MatrizOportunidadService {

    ResponseEntity<MatrizOportunidad> create(MatrizOportunidadPostDTO t);
    List<MatrizOportunidad> listMatrizOportunidad();
    MatrizOportunidadGetDTO findMatrizOportunidadByID(Long id);
    MatrizOportunidad findByIdOportunidad(Long id);
    MatrizOportunidadGetDTO updateById (Long id, MatrizOportunidadPutDTO data);
    ResponseEntity<MatrizOportunidad> evaluaOportunidad (Long id, MatrizOportunidadPutDTOevaluacion data);
    String generaCodigo (Long id);
}
