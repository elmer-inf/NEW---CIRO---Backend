package atc.riesgos.dao.service;

import atc.riesgos.model.dto.MatrizRiesgo.MatrizRiesgoPutDTOevaluacion;
import atc.riesgos.model.dto.MatrizRiesgo.MatrizRiesgoGetDTO;
import atc.riesgos.model.dto.MatrizRiesgo.MatrizRiesgoPostDTO;
import atc.riesgos.model.dto.MatrizRiesgo.MatrizRiesgoPutDTO;
import atc.riesgos.model.entity.MatrizRiesgo;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MatrizRiesgoService {

    ResponseEntity<MatrizRiesgo> create(MatrizRiesgoPostDTO t);
    List<MatrizRiesgo> listMatrizRiesgo();
    MatrizRiesgoGetDTO findMatrizRiesgoByID(Long id);
    MatrizRiesgo findByIdRiesgo(Long id);
    ResponseEntity<MatrizRiesgo> evaluaRiesgo (Long id, MatrizRiesgoPutDTOevaluacion data);
    MatrizRiesgoGetDTO updateById (Long id, MatrizRiesgoPutDTO data);
    List<MatrizRiesgo> getListMatrizInId(List<Long> in);
    String generaCodigo (Long id);

}
