package ciro.atc.dao.service;

import ciro.atc.config.log.Log;
import ciro.atc.exception.DBException;
import ciro.atc.model.dto.MatrizRiesgo.MatrizRiesgoGetDTO;
import ciro.atc.model.dto.MatrizRiesgo.MatrizRiesgoPostDTO;
import ciro.atc.model.dto.MatrizRiesgo.MatrizRiesgoPutDTO;
import ciro.atc.model.dto.MatrizRiesgo.MatrizRiesgoPutDTOevaluacion;
import ciro.atc.model.dto.Observacion.ObservacionPostDTO;
import ciro.atc.model.entity.MatrizRiesgo;
import ciro.atc.model.entity.TablaDescripcion;
import ciro.atc.model.entity.TablaDescripcionMatrizRiesgo;
import ciro.atc.model.repository.MatrizRiesgoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MatrizRiesgoServiceImpl implements MatrizRiesgoService {

    @Autowired
    MatrizRiesgoRepository matrizRiesgoRepository;
    @Autowired
    TablaDescripcionService tablaDescripcionService;
    @Autowired
    TablaDescripcionMatrizRiesgoService tablaDescripcionMatrizRiesgoService;
    @Autowired
    ObservacionService observacionService;

    public ResponseEntity<MatrizRiesgo> create(MatrizRiesgoPostDTO data) {
        HttpHeaders responseHeaders = new HttpHeaders();
        MatrizRiesgo matrizRiesgo = new MatrizRiesgo();
        try{
            BeanUtils.copyProperties(data, matrizRiesgo);

            TablaDescripcion tablaAreaId = tablaDescripcionService.findByIdTablaDesc(data.getAreaId());
            matrizRiesgo.setAreaId(tablaAreaId);

            TablaDescripcion tablaUnidadId = tablaDescripcionService.findByIdTablaDesc(data.getUnidadId());
            matrizRiesgo.setUnidadId(tablaUnidadId);

            TablaDescripcion tablaProcesoId = tablaDescripcionService.findByIdTablaDesc(data.getProcesoId());
            matrizRiesgo.setProcesoId(tablaProcesoId);

            TablaDescripcion tablaProcedimientoId = tablaDescripcionService.findByIdTablaDesc(data.getProcedimientoId());
            matrizRiesgo.setProcedimientoId(tablaProcedimientoId);

            TablaDescripcion tablaDuenoCargoId = tablaDescripcionService.findByIdTablaDesc(data.getDuenoCargoId());
            matrizRiesgo.setDuenoCargoId(tablaDuenoCargoId);

            TablaDescripcion tablaResponsableCargoId = tablaDescripcionService.findByIdTablaDesc(data.getResponsableCargoId());
            matrizRiesgo.setResponsableCargoId(tablaResponsableCargoId);

            TablaDescripcionMatrizRiesgo tablaIdentificadoId = tablaDescripcionMatrizRiesgoService.findByIdTablaDesc(data.getIdentificadoId());
            matrizRiesgo.setIdentificadoId(tablaIdentificadoId);

            TablaDescripcion tablaEfectoPerdidaId = tablaDescripcionService.findByIdTablaDesc(data.getEfectoPerdidaId());
            matrizRiesgo.setEfectoPerdidaId(tablaEfectoPerdidaId);

            TablaDescripcionMatrizRiesgo tablaPerdidaAsfiId = tablaDescripcionMatrizRiesgoService.findByIdTablaDesc(data.getPerdidaAsfiId());
            matrizRiesgo.setPerdidaAsfiId(tablaPerdidaAsfiId);

            TablaDescripcion tablaFactorRiesgoId = tablaDescripcionService.findByIdTablaDesc(data.getFactorRiesgoId());
            matrizRiesgo.setFactorRiesgoId(tablaFactorRiesgoId);

            TablaDescripcionMatrizRiesgo tablaProbabilidadId = tablaDescripcionMatrizRiesgoService.findByIdTablaDesc(data.getProbabilidadId());
            matrizRiesgo.setProbabilidadId(tablaProbabilidadId);

            TablaDescripcionMatrizRiesgo tablaImpactoId = tablaDescripcionMatrizRiesgoService.findByIdTablaDesc(data.getImpactoId());
            matrizRiesgo.setImpactoId(tablaImpactoId);

            TablaDescripcionMatrizRiesgo tablaControlId = tablaDescripcionMatrizRiesgoService.findByIdTablaDesc(data.getControlId());
            matrizRiesgo.setControlId(tablaControlId);

            matrizRiesgoRepository.save(matrizRiesgo);
        }catch (Exception e) {
            Log.log("Guardar Matriz de riesgo =>", e);
            return ResponseEntity.badRequest().headers(responseHeaders).body(null);
        }
        return ResponseEntity.ok().headers(responseHeaders).body(matrizRiesgo);
    }

    public List<MatrizRiesgo> listMatrizRiesgo(){
        return matrizRiesgoRepository.findAllByDeleted(false);
    }

    public MatrizRiesgoGetDTO findMatrizRiesgoByID(Long id){
        Optional<MatrizRiesgo> matrizRiesgo = matrizRiesgoRepository.findById(id);
        MatrizRiesgoGetDTO matrizRiesgoGetDTO = new MatrizRiesgoGetDTO();
        BeanUtils.copyProperties(matrizRiesgo.get(), matrizRiesgoGetDTO);
        return matrizRiesgoGetDTO;
    }

    public MatrizRiesgo findByIdRiesgo(Long id){
        Optional<MatrizRiesgo> founded = matrizRiesgoRepository.findById(id);
        return founded.get();
    }

    public ResponseEntity<MatrizRiesgo> evaluaRiesgo (Long id, MatrizRiesgoPutDTOevaluacion data){
        HttpHeaders responseHeaders = new HttpHeaders();
        MatrizRiesgo matrizRiesgo = matrizRiesgoRepository.findById(id).get();

        try {
            if(data.getEstadoRegistro().equals("Autorizado")){
                if(matrizRiesgo.getCodigo() == null || matrizRiesgo.getCodigo().isEmpty()){
                    String codigo = generaCodigo(id);
                    //System.out.println("CODIGO DE MATRIZ DE RIESGO :   " + codigo);
                    int ultimoIdUnidad = 0;
                    int countCodigoUnidad = matrizRiesgoRepository.countMatrizRiesgoCodigo(matrizRiesgo.getUnidadId().getClave());
                    if(countCodigoUnidad > 0){
                        ultimoIdUnidad = matrizRiesgoRepository.findUltimoIdUnidad(matrizRiesgo.getUnidadId().getClave());
                        matrizRiesgo.setIdUnidadCorrelativo(ultimoIdUnidad + 1); // Se guarda el incremento del ID correlativo del Evento al autorizar
                        matrizRiesgo.setCodigo(codigo);
                    }else{
                        ultimoIdUnidad = 1;
                        matrizRiesgo.setIdUnidadCorrelativo(ultimoIdUnidad); // Se guarda el ID correlativo con 1 del Evento al autorizar
                        matrizRiesgo.setCodigo(codigo);
                    }
                }
                matrizRiesgo.setEstadoRegistro(data.getEstadoRegistro());
                matrizRiesgoRepository.save(matrizRiesgo);
            }
            if(data.getEstadoRegistro().equals("Observado")){
                if(!matrizRiesgo.getEstadoRegistro().equals("Autorizado")){
                    if(data.getListaObservacion() != null && data.getNota() != null && data.getEstado() != null){
                        ObservacionPostDTO observacionPostDTO = new ObservacionPostDTO();
                        observacionPostDTO.setListaObservacion(data.getListaObservacion());
                        observacionPostDTO.setNota(data.getNota());
                        observacionPostDTO.setEstado(data.getEstado());
                        observacionPostDTO.setModulo(data.getModulo());
                        observacionService.create(observacionPostDTO, id);
                        System.out.println("entra como observado a evaluar");
                        matrizRiesgo.setEstadoRegistro(data.getEstadoRegistro());
                        matrizRiesgoRepository.save(matrizRiesgo);
                    }
                }
            }
            if(data.getEstadoRegistro().equals("Pendiente") || data.getEstadoRegistro().equals("Descartado")){
                if(!matrizRiesgo.getEstadoRegistro().equals("Autorizado")){
                    matrizRiesgo.setEstadoRegistro(data.getEstadoRegistro());
                    matrizRiesgoRepository.save(matrizRiesgo);
                }
            }
        }catch (Exception e){
            Log.log("Error al evaluar Riesgo =>", e);
            return ResponseEntity.badRequest().headers(responseHeaders).body(null);
        }
        return ResponseEntity.ok().headers(responseHeaders).body(matrizRiesgo);
    }


    private String generaCodigo (Long id){
        MatrizRiesgo matrizRiesgo = matrizRiesgoRepository.findById(id).get();

        String codMacro = matrizRiesgo.getProcesoId().getClave();
        String codUnidad = matrizRiesgo.getUnidadId().getClave();
        String codigo = "RO-".concat(codMacro + '-' + codUnidad);

        //System.out.println("COUNT:  " + matrizRiesgoRepository.countMatrizRiesgoCodigo(matrizRiesgo.getUnidadId().getClave()));
        int countCodigoUnidad = matrizRiesgoRepository.countMatrizRiesgoCodigo(matrizRiesgo.getUnidadId().getClave());

        if(countCodigoUnidad > 0){
            int ultimoIdUnidad = matrizRiesgoRepository.findUltimoIdUnidad(matrizRiesgo.getUnidadId().getClave());
            int idIncrementado = ultimoIdUnidad + 1; // Id correlativo del Riesgo para el codigo al autorizar
            String idGenerado = "";

            if(idIncrementado < 10){
                idGenerado = "00" + idIncrementado;
            }
            if(idIncrementado < 100 && idIncrementado > 9){
                idGenerado = "0" + idIncrementado;
            }
            if(idIncrementado > 99){
                idGenerado = Integer.toString(idIncrementado);
            }
            codigo = codigo.concat('_' + idGenerado);
        }else{
            String idGenerado = "001";
            codigo = codigo.concat('_' + idGenerado);
        }
        return codigo;
    }

    public MatrizRiesgoGetDTO updateById (Long id, MatrizRiesgoPutDTO data){
        MatrizRiesgo matrizRiesgo = matrizRiesgoRepository.findById(id).orElseThrow(()-> new DBException("Matriz Riesgo: ", id));
        MatrizRiesgoGetDTO matrizRiesgoGetDTO = new MatrizRiesgoGetDTO();
        try{
            TablaDescripcion tablaAreaId = tablaDescripcionService.findByIdTablaDesc(data.getAreaId());
            matrizRiesgo.setAreaId(tablaAreaId);

            TablaDescripcion tablaUnidadId = tablaDescripcionService.findByIdTablaDesc(data.getUnidadId());
            matrizRiesgo.setUnidadId(tablaUnidadId);

            TablaDescripcion tablaProcesoId = tablaDescripcionService.findByIdTablaDesc(data.getProcesoId());
            matrizRiesgo.setProcesoId(tablaProcesoId);

            TablaDescripcion tablaProcedimientoId = tablaDescripcionService.findByIdTablaDesc(data.getProcedimientoId());
            matrizRiesgo.setProcedimientoId(tablaProcedimientoId);

            TablaDescripcion tablaDuenoCargoId = tablaDescripcionService.findByIdTablaDesc(data.getDuenoCargoId());
            matrizRiesgo.setDuenoCargoId(tablaDuenoCargoId);

            TablaDescripcion tablaResponsableCargoId = tablaDescripcionService.findByIdTablaDesc(data.getResponsableCargoId());
            matrizRiesgo.setResponsableCargoId(tablaResponsableCargoId);

            TablaDescripcionMatrizRiesgo tablaIdentificadoId = tablaDescripcionMatrizRiesgoService.findByIdTablaDesc(data.getIdentificadoId());
            matrizRiesgo.setIdentificadoId(tablaIdentificadoId);

            TablaDescripcion tablaEfectoPerdidaId = tablaDescripcionService.findByIdTablaDesc(data.getEfectoPerdidaId());
            matrizRiesgo.setEfectoPerdidaId(tablaEfectoPerdidaId);

            TablaDescripcionMatrizRiesgo tablaPerdidaAsfiId = tablaDescripcionMatrizRiesgoService.findByIdTablaDesc(data.getPerdidaAsfiId());
            matrizRiesgo.setPerdidaAsfiId(tablaPerdidaAsfiId);

            TablaDescripcion tablaFactorRiesgoId = tablaDescripcionService.findByIdTablaDesc(data.getFactorRiesgoId());
            matrizRiesgo.setFactorRiesgoId(tablaFactorRiesgoId);

            TablaDescripcionMatrizRiesgo tablaProbabilidadId = tablaDescripcionMatrizRiesgoService.findByIdTablaDesc(data.getProbabilidadId());
            matrizRiesgo.setProbabilidadId(tablaProbabilidadId);

            TablaDescripcionMatrizRiesgo tablaImpactoId = tablaDescripcionMatrizRiesgoService.findByIdTablaDesc(data.getImpactoId());
            matrizRiesgo.setImpactoId(tablaImpactoId);

            TablaDescripcionMatrizRiesgo tablaControlId = tablaDescripcionMatrizRiesgoService.findByIdTablaDesc(data.getControlId());
            matrizRiesgo.setControlId(tablaControlId);

            BeanUtils.copyProperties(data, matrizRiesgo);
            matrizRiesgoRepository.save(matrizRiesgo);
            BeanUtils.copyProperties(matrizRiesgo, matrizRiesgoGetDTO);
        } catch (Exception e){
            Log.log("Matriz de riesgo actualizado: ", e);
        }
        return matrizRiesgoGetDTO;
    }


    public List<MatrizRiesgo> getListMatrizInId(List<Long> in) {
        try{
            return matrizRiesgoRepository.matrizInById(in);
        }catch (Exception e){
            Log.log("Error en getListMatrizInId : ", e);

        }

        return new ArrayList<>();
    }


}
