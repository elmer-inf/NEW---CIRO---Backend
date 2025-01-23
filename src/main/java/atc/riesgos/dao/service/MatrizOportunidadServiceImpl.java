package atc.riesgos.dao.service;

import atc.riesgos.model.dto.Observacion.ObservacionPostDTO;
import atc.riesgos.model.entity.MatrizOportunidad;
import atc.riesgos.model.entity.TablaDescripcionMatrizRiesgo;
import atc.riesgos.exception.DBException;
import atc.riesgos.model.dto.MatrizOportunidad.MatrizOportunidadGetDTO;
import atc.riesgos.model.dto.MatrizOportunidad.MatrizOportunidadPostDTO;
import atc.riesgos.model.dto.MatrizOportunidad.MatrizOportunidadPutDTO;
import atc.riesgos.model.dto.MatrizOportunidad.MatrizOportunidadPutDTOevaluacion;
import atc.riesgos.model.entity.TablaDescripcion;
import atc.riesgos.model.entity.TablaDescripcionMatrizOportunidad;
import atc.riesgos.model.repository.MatrizOportunidadRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatrizOportunidadServiceImpl implements MatrizOportunidadService {

    @Autowired
    MatrizOportunidadRepository matrizOportunidadRepository;
    @Autowired
    TablaDescripcionService tablaDescripcionService;
    @Autowired
    TablaDescripcionMatrizRiesgoService tablaDescripcionMatrizRiesgoService;
    @Autowired
    TablaDescripcionMatrizOportunidadService tablaDescripcionMatrizOportunidadService;
    @Autowired
    ObservacionService observacionService;

    public ResponseEntity<MatrizOportunidad> create(MatrizOportunidadPostDTO data) {
        HttpHeaders responseHeaders = new HttpHeaders();
        MatrizOportunidad matrizOportunidad = new MatrizOportunidad();
        try{
            BeanUtils.copyProperties(data, matrizOportunidad);

            TablaDescripcion tablaAreaId = tablaDescripcionService.findByIdTablaDesc(data.getAreaId());
            matrizOportunidad.setAreaId(tablaAreaId);

            TablaDescripcion tablaUnidadId = tablaDescripcionService.findByIdTablaDesc(data.getUnidadId());
            matrizOportunidad.setUnidadId(tablaUnidadId);

            TablaDescripcion tablaProcesoId = tablaDescripcionService.findByIdTablaDesc(data.getProcesoId());
            matrizOportunidad.setProcesoId(tablaProcesoId);

            TablaDescripcion tablaProcedimientoId = tablaDescripcionService.findByIdTablaDesc(data.getProcedimientoId());
            matrizOportunidad.setProcedimientoId(tablaProcedimientoId);

            TablaDescripcion tablaDuenoCargoId = tablaDescripcionService.findByIdTablaDesc(data.getDuenoCargoId());
            matrizOportunidad.setDuenoCargoId(tablaDuenoCargoId);

            TablaDescripcion tablaResponsableCargoId = tablaDescripcionService.findByIdTablaDesc(data.getResponsableCargoId());
            matrizOportunidad.setResponsableCargoId(tablaResponsableCargoId);

            TablaDescripcionMatrizOportunidad tablaFodaId = tablaDescripcionMatrizOportunidadService.findByIdTablaDesc(data.getFodaId());
            matrizOportunidad.setFodaId(tablaFodaId);

            TablaDescripcionMatrizOportunidad tablaFodaDescId = tablaDescripcionMatrizOportunidadService.findByIdTablaDesc(data.getFodaDescripcionId());
            matrizOportunidad.setFodaDescripcionId(tablaFodaDescId);

            TablaDescripcionMatrizOportunidad tablaGrupoInteresId = tablaDescripcionMatrizOportunidadService.findByIdTablaDesc(data.getGrupoInteresId());
            matrizOportunidad.setGrupoInteresId(tablaGrupoInteresId);

            TablaDescripcionMatrizRiesgo tablaProbabilidadId = tablaDescripcionMatrizRiesgoService.findByIdTablaDesc(data.getProbabilidadId());
            matrizOportunidad.setProbabilidadId(tablaProbabilidadId);

            TablaDescripcionMatrizOportunidad tablaImpactoOporId = tablaDescripcionMatrizOportunidadService.findByIdTablaDesc(data.getImpactoOporId());
            matrizOportunidad.setImpactoOporId(tablaImpactoOporId);

            TablaDescripcionMatrizOportunidad tablaFortalezaId = tablaDescripcionMatrizOportunidadService.findByIdTablaDesc(data.getFortalezaId());
            matrizOportunidad.setFortalezaId(tablaFortalezaId);

            matrizOportunidadRepository.save(matrizOportunidad);
        }catch (Exception e) {
            return ResponseEntity.badRequest().headers(responseHeaders).body(null);
        }
        return ResponseEntity.ok().headers(responseHeaders).body(matrizOportunidad);
    }


    public List<MatrizOportunidad> listMatrizOportunidad(){
        return matrizOportunidadRepository.findAllByDeleted(false);
    }


    public MatrizOportunidadGetDTO findMatrizOportunidadByID(Long id){
        Optional<MatrizOportunidad> matrizOportunidad = matrizOportunidadRepository.findById(id);
        MatrizOportunidadGetDTO matrizOportunidadGetDTO = new MatrizOportunidadGetDTO();
        BeanUtils.copyProperties(matrizOportunidad.get(), matrizOportunidadGetDTO);
        return matrizOportunidadGetDTO;
    }


    public MatrizOportunidad findByIdOportunidad(Long id){
        Optional<MatrizOportunidad> founded = matrizOportunidadRepository.findById(id);
        return founded.get();
    }


    public MatrizOportunidadGetDTO updateById (Long id, MatrizOportunidadPutDTO data){
        MatrizOportunidad matrizOportunidad = matrizOportunidadRepository.findById(id).orElseThrow(()-> new DBException("Matriz de oportunidad: ", id));
        MatrizOportunidadGetDTO matrizOportunidadGetDTO = new MatrizOportunidadGetDTO();
        try{
            TablaDescripcion tablaAreaId = tablaDescripcionService.findByIdTablaDesc(data.getAreaId());
            matrizOportunidad.setAreaId(tablaAreaId);

            TablaDescripcion tablaUnidadId = tablaDescripcionService.findByIdTablaDesc(data.getUnidadId());
            matrizOportunidad.setUnidadId(tablaUnidadId);

            TablaDescripcion tablaProcesoId = tablaDescripcionService.findByIdTablaDesc(data.getProcesoId());
            matrizOportunidad.setProcesoId(tablaProcesoId);

            TablaDescripcion tablaProcedimientoId = tablaDescripcionService.findByIdTablaDesc(data.getProcedimientoId());
            matrizOportunidad.setProcedimientoId(tablaProcedimientoId);

            TablaDescripcion tablaDuenoCargoId = tablaDescripcionService.findByIdTablaDesc(data.getDuenoCargoId());
            matrizOportunidad.setDuenoCargoId(tablaDuenoCargoId);

            TablaDescripcion tablaResponsableCargoId = tablaDescripcionService.findByIdTablaDesc(data.getResponsableCargoId());
            matrizOportunidad.setResponsableCargoId(tablaResponsableCargoId);

            TablaDescripcionMatrizOportunidad tablaFodaId = tablaDescripcionMatrizOportunidadService.findByIdTablaDesc(data.getFodaId());
            matrizOportunidad.setFodaId(tablaFodaId);

            TablaDescripcionMatrizOportunidad tablaFodaDescId = tablaDescripcionMatrizOportunidadService.findByIdTablaDesc(data.getFodaDescripcionId());
            matrizOportunidad.setFodaDescripcionId(tablaFodaDescId);

            TablaDescripcionMatrizOportunidad tablaGrupoInteresId = tablaDescripcionMatrizOportunidadService.findByIdTablaDesc(data.getGrupoInteresId());
            matrizOportunidad.setGrupoInteresId(tablaGrupoInteresId);

            TablaDescripcionMatrizRiesgo tablaProbabilidadId = tablaDescripcionMatrizRiesgoService.findByIdTablaDesc(data.getProbabilidadId());
            matrizOportunidad.setProbabilidadId(tablaProbabilidadId);

            TablaDescripcionMatrizOportunidad tablaImpactoOporId = tablaDescripcionMatrizOportunidadService.findByIdTablaDesc(data.getImpactoOporId());
            matrizOportunidad.setImpactoOporId(tablaImpactoOporId);

            TablaDescripcionMatrizOportunidad tablaFortalezaId = tablaDescripcionMatrizOportunidadService.findByIdTablaDesc(data.getFortalezaId());
            matrizOportunidad.setFortalezaId(tablaFortalezaId);

            BeanUtils.copyProperties(data, matrizOportunidad);
            matrizOportunidadRepository.save(matrizOportunidad);
            BeanUtils.copyProperties(matrizOportunidad, matrizOportunidadGetDTO);
        } catch (Exception e){
            System.out.println("Error: " + e);
        }
        return matrizOportunidadGetDTO;
    }


   public ResponseEntity<MatrizOportunidad> evaluaOportunidad (Long id, MatrizOportunidadPutDTOevaluacion data){
        HttpHeaders responseHeaders = new HttpHeaders();
        MatrizOportunidad matrizOportunidad = matrizOportunidadRepository.findById(id).get();

        try {
            if(data.getEstadoRegistro().equals("Autorizado")){
                if(matrizOportunidad.getCodigo() == null || matrizOportunidad.getCodigo().isEmpty()){
                    String codigo = generaCodigo(id);
                    //System.out.println("CODIGO DE MATRIZ DE RIESGO :   " + codigo);
                    int ultimoIdMacro = 0;
                    int countCodigoMacro = matrizOportunidadRepository.countMatrizOportunidadCodigo(matrizOportunidad.getProcesoId().getClave());
                    if(countCodigoMacro > 0){
                        ultimoIdMacro = matrizOportunidadRepository.findUltimoIdMacro(matrizOportunidad.getProcesoId().getClave());
                        matrizOportunidad.setIdMacroCorrelativo(ultimoIdMacro + 1); // Se guarda el incremento del ID correlativo del Evento al autorizar
                        matrizOportunidad.setCodigo(codigo);
                    }else{
                        ultimoIdMacro = 1;
                        matrizOportunidad.setIdMacroCorrelativo(ultimoIdMacro); // Se guarda el ID correlativo con 1 del Evento al autorizar
                        matrizOportunidad.setCodigo(codigo);
                    }
                }
                matrizOportunidad.setEstadoRegistro(data.getEstadoRegistro());
                matrizOportunidadRepository.save(matrizOportunidad);
            }
            if(data.getEstadoRegistro().equals("Observado")){
                if(!matrizOportunidad.getEstadoRegistro().equals("Autorizado")){
                    if(data.getListaObservacion() != null && data.getNota() != null && data.getEstado() != null){
                        ObservacionPostDTO observacionPostDTO = new ObservacionPostDTO();
                        observacionPostDTO.setListaObservacion(data.getListaObservacion());
                        observacionPostDTO.setNota(data.getNota());
                        observacionPostDTO.setEstado(data.getEstado());
                        observacionPostDTO.setModulo(data.getModulo());
                        observacionService.create(observacionPostDTO, id);
                        matrizOportunidad.setEstadoRegistro(data.getEstadoRegistro());
                        matrizOportunidadRepository.save(matrizOportunidad);
                    }
                }
            }
            if(data.getEstadoRegistro().equals("Pendiente") || data.getEstadoRegistro().equals("Descartado")){
                if(!matrizOportunidad.getEstadoRegistro().equals("Autorizado")){
                    matrizOportunidad.setEstadoRegistro(data.getEstadoRegistro());
                    matrizOportunidadRepository.save(matrizOportunidad);
                }
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().headers(responseHeaders).body(null);
        }
        return ResponseEntity.ok().headers(responseHeaders).body(matrizOportunidad);
    }


    public String generaCodigo (Long id){
        MatrizOportunidad matrizOportunidad = matrizOportunidadRepository.findById(id).get();

        String codMacro = matrizOportunidad.getProcesoId().getClave();
        String codigo = "OP-".concat(codMacro);

        //System.out.println("COUNT:  " + matrizOportunidadRepository.countMatrizOportunidadCodigo(matrizOportunidad.getUnidadId().getClave()));
        int countCodigoMacro = matrizOportunidadRepository.countMatrizOportunidadCodigo(matrizOportunidad.getProcesoId().getClave());

        if(countCodigoMacro > 0){
            int ultimoIdUnidad = matrizOportunidadRepository.findUltimoIdMacro(matrizOportunidad.getProcesoId().getClave());
            int idIncrementado = ultimoIdUnidad + 1; // Id correlativo de la Oportunidad para el codigo al autorizar
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

}
