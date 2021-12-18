package ciro.atc.dao.service;

import ciro.atc.model.dto.MatrizOportunidad.MatrizOportunidadGetDTO;
import ciro.atc.model.dto.MatrizOportunidad.MatrizOportunidadPostDTO;
import ciro.atc.model.dto.MatrizOportunidad.MatrizOportunidadPutDTO;
import ciro.atc.model.dto.MatrizOportunidad.MatrizOportunidadPutDTOevaluacion;
import ciro.atc.model.entity.MatrizOportunidad;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MatrizOportunidadService {

    ResponseEntity<MatrizOportunidad> create(MatrizOportunidadPostDTO t);
    List<MatrizOportunidad> listMatrizOportunidad();
    MatrizOportunidadGetDTO findMatrizOportunidadByID(Long id);
    MatrizOportunidad findByIdMatrizOportunidad(Long id);
    MatrizOportunidadGetDTO updateById (Long id, MatrizOportunidadPutDTO data);
    /*ResponseEntity<MatrizOportunidad> evaluaOportunidad (Long id, MatrizOportunidadPutDTOevaluacion data);*/

}
