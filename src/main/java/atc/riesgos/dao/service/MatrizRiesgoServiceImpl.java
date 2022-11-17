package atc.riesgos.dao.service;

import atc.riesgos.model.dto.MatrizRiesgo.MatrizRiesgoPutDTOevaluacion;
import atc.riesgos.config.log.Log;
import atc.riesgos.exception.DBException;
import atc.riesgos.model.dto.MatrizRiesgo.MatrizRiesgoGetDTO;
import atc.riesgos.model.dto.MatrizRiesgo.MatrizRiesgoPostDTO;
import atc.riesgos.model.dto.MatrizRiesgo.MatrizRiesgoPutDTO;
import atc.riesgos.model.dto.Observacion.ObservacionPostDTO;
import atc.riesgos.model.entity.EventoRiesgo;
import atc.riesgos.model.entity.MatrizRiesgo;
import atc.riesgos.model.entity.TablaDescripcion;
import atc.riesgos.model.entity.TablaDescripcionMatrizRiesgo;
import atc.riesgos.model.repository.EventoRiesgoRepository;
import atc.riesgos.model.repository.MatrizRiesgoRepository;
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
    @Autowired
    EventoRiesgoRepository eventoRiesgoRepository;

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

            matrizRiesgo.setEstadoRegistro("Pendiente");
            matrizRiesgoRepository.save(matrizRiesgo);
        }catch (Exception e) {
            Log.log("Error al Guardar Matriz de riesgo =>", e);
            return ResponseEntity.badRequest().headers(responseHeaders).body(null);
        }
        return ResponseEntity.ok().headers(responseHeaders).body(matrizRiesgo);
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
            Log.log("Error al modificar Matriz de riesgo: ", e);
        }
        return matrizRiesgoGetDTO;
    }

    public List<MatrizRiesgo> listMatrizRiesgo(){
        return matrizRiesgoRepository.findAllByDeleted(false);
    }

    public MatrizRiesgoGetDTO findMatrizRiesgoByID(Long id){
        Optional<MatrizRiesgo> matrizRiesgo = matrizRiesgoRepository.findById(id);
        MatrizRiesgoGetDTO matrizRiesgoGetDTO = new MatrizRiesgoGetDTO();
        BeanUtils.copyProperties(matrizRiesgo.get(), matrizRiesgoGetDTO);

        if(matrizRiesgo.get().getEventoRiesgoId() != null){
            Optional<EventoRiesgo> eventoRiesgo = eventoRiesgoRepository.findById(matrizRiesgo.get().getEventoRiesgoId());
            matrizRiesgoGetDTO.setEventoRiesgoId(eventoRiesgo.get());
        }
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
                    String codMacro = matrizRiesgo.getProcesoId().getClave();
                    int countCodigoUnidad = matrizRiesgoRepository.countMatrizRiesgoCodigo(codMacro);
                    System.out.println("sigla entrada :   " + matrizRiesgo.getUnidadId().getClave());
                    System.out.println("countCodigoUnidad :   " + countCodigoUnidad);
                    if(countCodigoUnidad > 0){
                        ultimoIdUnidad = matrizRiesgoRepository.findUltimoIdUnidad(codMacro);
                        System.out.println("ultimoIdUnidad :   " + ultimoIdUnidad);
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
                        //System.out.println("entra como observado a evaluar");
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


    public String generaCodigo (Long id){
        MatrizRiesgo matrizRiesgo = matrizRiesgoRepository.findById(id).get();

        String codMacro = matrizRiesgo.getProcesoId().getClave();
        String codigo = "RO-".concat(codMacro); //String codigo = "RO-".concat(codMacro + '-' + codUnidad);
        int countCodigoUnidad = matrizRiesgoRepository.countMatrizRiesgoCodigo(codMacro);
        if(countCodigoUnidad > 0){
            int ultimoIdUnidad = matrizRiesgoRepository.findUltimoIdUnidad(codMacro);
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

    public List<MatrizRiesgo> getListMatrizInId(List<Long> in) {
        try{
            return matrizRiesgoRepository.matrizInById(in);
        }catch (Exception e){
            Log.log("Error en getListMatrizInId : ", e);
        }

        return new ArrayList<>();
    }


}
