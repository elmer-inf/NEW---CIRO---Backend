package atc.riesgos.dao.service;

import atc.riesgos.config.log.Log;
import atc.riesgos.exception.DBException;
import atc.riesgos.model.dto.EventoRiesgo.EventoRiesgoDTO;
import atc.riesgos.model.dto.EventoRiesgo.EventoRiesgoGetDTO;
import atc.riesgos.model.dto.EventoRiesgo.EventoRiesgoPostDTO;
import atc.riesgos.model.dto.EventoRiesgo.EventoRiesgoPutDTOevaluacion;
import atc.riesgos.model.dto.EventoRiesgo.*;
import atc.riesgos.model.dto.Observacion.ObservacionPostDTO;
import atc.riesgos.model.entity.EventoRiesgo;
import atc.riesgos.model.entity.MatrizRiesgo;
import atc.riesgos.model.entity.TablaDescripcion;
import atc.riesgos.model.repository.EventoRiesgoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventoRiesgoServiceImpl implements EventoRiesgoService {

    @Autowired
    EventoRiesgoRepository eventoRiesgoRepository;
    @Autowired
    TablaDescripcionService tablaDescripcionService;
    @Autowired
    ObservacionService observacionService;

    @Autowired
    MatrizRiesgoService matrizRiesgoService;

    @Autowired
    ArchivoService archivoService;

    //  public <R extends Object> Long count(Class<R> model)
    private EventoRiesgo buildEventoToCreateUpdate(EventoRiesgo eventoRiesgo, EventoRiesgoDTO data){
        try{
            TablaDescripcion tablaAgenciaId = tablaDescripcionService.findByIdTablaDesc(data.getAgenciaId());
            eventoRiesgo.setAgenciaId(tablaAgenciaId);

            TablaDescripcion tablaCiudadId = tablaDescripcionService.findByIdTablaDesc(data.getCiudadId());
            eventoRiesgo.setCiudadId(tablaCiudadId);

            TablaDescripcion tablaAreaId = tablaDescripcionService.findByIdTablaDesc(data.getAreaID());
            eventoRiesgo.setAreaID(tablaAreaId);

            TablaDescripcion tablaUnidadId = tablaDescripcionService.findByIdTablaDesc(data.getUnidadId());
            eventoRiesgo.setUnidadId(tablaUnidadId);

            TablaDescripcion tablaEntidadId = tablaDescripcionService.findByIdTablaDesc(data.getEntidadId());
            eventoRiesgo.setEntidadId(tablaEntidadId);

            TablaDescripcion tablaCargoId = tablaDescripcionService.findByIdTablaDesc(data.getCargoId());
            eventoRiesgo.setCargoId(tablaCargoId);

            TablaDescripcion tablaFuenteInfId = tablaDescripcionService.findByIdTablaDesc(data.getFuenteInfId());
            eventoRiesgo.setFuenteInfId(tablaFuenteInfId);

            TablaDescripcion tablaCanalAsfiId = tablaDescripcionService.findByIdTablaDesc(data.getCanalAsfiId());
            eventoRiesgo.setCanalAsfiId(tablaCanalAsfiId);

            TablaDescripcion tablaSubcategorizacionId = tablaDescripcionService.findByIdTablaDesc(data.getSubcategorizacionId());
            eventoRiesgo.setSubcategorizacionId(tablaSubcategorizacionId);

            TablaDescripcion tablaEventoPerdidaId = tablaDescripcionService.findByIdTablaDesc(data.getTipoEventoPerdidaId());
            eventoRiesgo.setTipoEventoPerdidaId(tablaEventoPerdidaId);

            TablaDescripcion tablaSubEventoId = tablaDescripcionService.findByIdTablaDesc(data.getSubEventoId());
            eventoRiesgo.setSubEventoId(tablaSubEventoId);

            TablaDescripcion tablaClaseEventoId = tablaDescripcionService.findByIdTablaDesc(data.getClaseEventoId());
            eventoRiesgo.setClaseEventoId(tablaClaseEventoId);

            TablaDescripcion tablaFactorRiesgoId = tablaDescripcionService.findByIdTablaDesc(data.getFactorRiesgoId());
            eventoRiesgo.setFactorRiesgoId(tablaFactorRiesgoId);

            TablaDescripcion tablaProcesoId = tablaDescripcionService.findByIdTablaDesc(data.getProcesoId());
            eventoRiesgo.setProcesoId(tablaProcesoId);

            TablaDescripcion tablaProcedimientoId = tablaDescripcionService.findByIdTablaDesc(data.getProcedimientoId());
            eventoRiesgo.setProcedimientoId(tablaProcedimientoId);

            TablaDescripcion tablaLineaAsfiId = tablaDescripcionService.findByIdTablaDesc(data.getLineaAsfiId());
            eventoRiesgo.setLineaAsfiId(tablaLineaAsfiId);

            TablaDescripcion tablaOperacionId = tablaDescripcionService.findByIdTablaDesc(data.getOperacionId());
            eventoRiesgo.setOperacionId(tablaOperacionId);

            TablaDescripcion tablaEfectoPerdidaId = tablaDescripcionService.findByIdTablaDesc(data.getEfectoPerdidaId());
            eventoRiesgo.setEfectoPerdidaId(tablaEfectoPerdidaId);

            TablaDescripcion tablaOpeProSerId = tablaDescripcionService.findByIdTablaDesc(data.getOpeProSerId());
            eventoRiesgo.setOpeProSerId(tablaOpeProSerId);

            TablaDescripcion tablaTipoServicioId = tablaDescripcionService.findByIdTablaDesc(data.getTipoServicioId());
            eventoRiesgo.setTipoServicioId(tablaTipoServicioId);

            TablaDescripcion tablaDescServicioId = tablaDescripcionService.findByIdTablaDesc(data.getDescServicioId());
            eventoRiesgo.setDescServicioId(tablaDescServicioId);

            // TablaDescripcion tablaTasaCambioId = tablaDescripcionService.findByIdTablaDesc(data.getTasaCambioId());
            eventoRiesgo.setTasaCambioId(data.getTasaCambioId());

            TablaDescripcion tablaMonedaId = tablaDescripcionService.findByIdTablaDesc(data.getMonedaId());
            eventoRiesgo.setMonedaId(tablaMonedaId);

            TablaDescripcion tablaImpactoId = tablaDescripcionService.findByIdTablaDesc(data.getImpactoId());
            eventoRiesgo.setImpactoId(tablaImpactoId);

            TablaDescripcion tablaPolizaSeguroId = tablaDescripcionService.findByIdTablaDesc(data.getPolizaSeguroId());
            eventoRiesgo.setPolizaSeguroId(tablaPolizaSeguroId);

            TablaDescripcion tablaRecuperacionId = tablaDescripcionService.findByIdTablaDesc(data.getRecuperacionActivoId());
            eventoRiesgo.setRecuperacionActivoId(tablaRecuperacionId);


            TablaDescripcion tablaOperativoId = tablaDescripcionService.findByIdTablaDesc(data.getOperativoId());
            eventoRiesgo.setOperativoId(tablaOperativoId);

            TablaDescripcion tablaLiquidezId = tablaDescripcionService.findByIdTablaDesc(data.getLiquidezId());
            eventoRiesgo.setLiquidezId(tablaLiquidezId);

            TablaDescripcion tablaLgiId = tablaDescripcionService.findByIdTablaDesc(data.getFraudeId());
            eventoRiesgo.setFraudeId(tablaLgiId);

            TablaDescripcion tablaLegalId = tablaDescripcionService.findByIdTablaDesc(data.getLegalId());
            eventoRiesgo.setLegalId(tablaLegalId);

            TablaDescripcion tablaReputacionalId = tablaDescripcionService.findByIdTablaDesc(data.getReputacionalId());
            eventoRiesgo.setReputacionalId(tablaReputacionalId);

            TablaDescripcion tablaCumplimientoId = tablaDescripcionService.findByIdTablaDesc(data.getCumplimientoId());
            eventoRiesgo.setCumplimientoId(tablaCumplimientoId);

            TablaDescripcion tablaEstrategicoId = tablaDescripcionService.findByIdTablaDesc(data.getEstrategicoId());
            eventoRiesgo.setEstrategicoId(tablaEstrategicoId);

            TablaDescripcion tablaGobiernoId = tablaDescripcionService.findByIdTablaDesc(data.getGobiernoId());
            eventoRiesgo.setGobiernoId(tablaGobiernoId);

            TablaDescripcion tablaSeguridadId = tablaDescripcionService.findByIdTablaDesc(data.getSeguridadId());
            eventoRiesgo.setSeguridadId(tablaSeguridadId);

            // Planes
            TablaDescripcion tablaAreaResponsableId = tablaDescripcionService.findByIdTablaDesc(data.getAreaResponsableId());
            eventoRiesgo.setAreaResponsableId(tablaAreaResponsableId);

            TablaDescripcion tablaCargoResponsableId = tablaDescripcionService.findByIdTablaDesc(data.getCargoResponsableId());
            eventoRiesgo.setCargoResponsableId(tablaCargoResponsableId);



            //Guardando la matriz de riesgos
            List<MatrizRiesgo> matrizRiesgos = matrizRiesgoService.getListMatrizInId(data.getListMatrizRiesgo());
            eventoRiesgo.setRiesgoRelacionado(matrizRiesgos);

            return eventoRiesgo;
        }catch (Exception e){
            Log.log("Error al estructurar objeto ", e);
        }

        return null;
    }

    public ResponseEntity<EventoRiesgo> create(EventoRiesgoPostDTO data) {
        EventoRiesgo eventoRiesgo = new EventoRiesgo();
        try {
            BeanUtils.copyProperties(data, eventoRiesgo);
            //eventoRiesgoToSave = buildEventoToCreateUpdate(eventoRiesgoToSave, data);

            TablaDescripcion tablaAgenciaId = tablaDescripcionService.findByIdTablaDesc(data.getAgenciaId());
            eventoRiesgo.setAgenciaId(tablaAgenciaId);

            TablaDescripcion tablaCiudadId = tablaDescripcionService.findByIdTablaDesc(data.getCiudadId());
            eventoRiesgo.setCiudadId(tablaCiudadId);

            TablaDescripcion tablaAreaId = tablaDescripcionService.findByIdTablaDesc(data.getAreaID());
            eventoRiesgo.setAreaID(tablaAreaId);

            TablaDescripcion tablaUnidadId = tablaDescripcionService.findByIdTablaDesc(data.getUnidadId());
            eventoRiesgo.setUnidadId(tablaUnidadId);

            TablaDescripcion tablaEntidadId = tablaDescripcionService.findByIdTablaDesc(data.getEntidadId());
            eventoRiesgo.setEntidadId(tablaEntidadId);

            TablaDescripcion tablaCargoId = tablaDescripcionService.findByIdTablaDesc(data.getCargoId());
            eventoRiesgo.setCargoId(tablaCargoId);

            TablaDescripcion tablaFuenteInfId = tablaDescripcionService.findByIdTablaDesc(data.getFuenteInfId());
            eventoRiesgo.setFuenteInfId(tablaFuenteInfId);

            TablaDescripcion tablaCanalAsfiId = tablaDescripcionService.findByIdTablaDesc(data.getCanalAsfiId());
            eventoRiesgo.setCanalAsfiId(tablaCanalAsfiId);

            TablaDescripcion tablaSubcategorizacionId = tablaDescripcionService.findByIdTablaDesc(data.getSubcategorizacionId());
            eventoRiesgo.setSubcategorizacionId(tablaSubcategorizacionId);

            TablaDescripcion tablaEventoPerdidaId = tablaDescripcionService.findByIdTablaDesc(data.getTipoEventoPerdidaId());
            eventoRiesgo.setTipoEventoPerdidaId(tablaEventoPerdidaId);

            TablaDescripcion tablaSubEventoId = tablaDescripcionService.findByIdTablaDesc(data.getSubEventoId());
            eventoRiesgo.setSubEventoId(tablaSubEventoId);

            TablaDescripcion tablaClaseEventoId = tablaDescripcionService.findByIdTablaDesc(data.getClaseEventoId());
            eventoRiesgo.setClaseEventoId(tablaClaseEventoId);

            TablaDescripcion tablaFactorRiesgoId = tablaDescripcionService.findByIdTablaDesc(data.getFactorRiesgoId());
            eventoRiesgo.setFactorRiesgoId(tablaFactorRiesgoId);

            TablaDescripcion tablaProcesoId = tablaDescripcionService.findByIdTablaDesc(data.getProcesoId());
            eventoRiesgo.setProcesoId(tablaProcesoId);

            TablaDescripcion tablaProcedimientoId = tablaDescripcionService.findByIdTablaDesc(data.getProcedimientoId());
            eventoRiesgo.setProcedimientoId(tablaProcedimientoId);

            TablaDescripcion tablaLineaAsfiId = tablaDescripcionService.findByIdTablaDesc(data.getLineaAsfiId());
            eventoRiesgo.setLineaAsfiId(tablaLineaAsfiId);

            TablaDescripcion tablaOperacionId = tablaDescripcionService.findByIdTablaDesc(data.getOperacionId());
            eventoRiesgo.setOperacionId(tablaOperacionId);

            TablaDescripcion tablaEfectoPerdidaId = tablaDescripcionService.findByIdTablaDesc(data.getEfectoPerdidaId());
            eventoRiesgo.setEfectoPerdidaId(tablaEfectoPerdidaId);

            TablaDescripcion tablaOpeProSerId = tablaDescripcionService.findByIdTablaDesc(data.getOpeProSerId());
            eventoRiesgo.setOpeProSerId(tablaOpeProSerId);

            TablaDescripcion tablaTipoServicioId = tablaDescripcionService.findByIdTablaDesc(data.getTipoServicioId());
            eventoRiesgo.setTipoServicioId(tablaTipoServicioId);

            TablaDescripcion tablaDescServicioId = tablaDescripcionService.findByIdTablaDesc(data.getDescServicioId());
            eventoRiesgo.setDescServicioId(tablaDescServicioId);

            // TablaDescripcion tablaTasaCambioId = tablaDescripcionService.findByIdTablaDesc(data.getTasaCambioId());
            eventoRiesgo.setTasaCambioId(data.getTasaCambioId());

            TablaDescripcion tablaMonedaId = tablaDescripcionService.findByIdTablaDesc(data.getMonedaId());
            eventoRiesgo.setMonedaId(tablaMonedaId);

            TablaDescripcion tablaImpactoId = tablaDescripcionService.findByIdTablaDesc(data.getImpactoId());
            eventoRiesgo.setImpactoId(tablaImpactoId);

            TablaDescripcion tablaPolizaSeguroId = tablaDescripcionService.findByIdTablaDesc(data.getPolizaSeguroId());
            eventoRiesgo.setPolizaSeguroId(tablaPolizaSeguroId);

            TablaDescripcion tablaRecuperacionId = tablaDescripcionService.findByIdTablaDesc(data.getRecuperacionActivoId());
            eventoRiesgo.setRecuperacionActivoId(tablaRecuperacionId);


            TablaDescripcion tablaOperativoId = tablaDescripcionService.findByIdTablaDesc(data.getOperativoId());
            eventoRiesgo.setOperativoId(tablaOperativoId);

            TablaDescripcion tablaLiquidezId = tablaDescripcionService.findByIdTablaDesc(data.getLiquidezId());
            eventoRiesgo.setLiquidezId(tablaLiquidezId);

            TablaDescripcion tablaLgiId = tablaDescripcionService.findByIdTablaDesc(data.getFraudeId());
            eventoRiesgo.setFraudeId(tablaLgiId);

            TablaDescripcion tablaLegalId = tablaDescripcionService.findByIdTablaDesc(data.getLegalId());
            eventoRiesgo.setLegalId(tablaLegalId);

            TablaDescripcion tablaReputacionalId = tablaDescripcionService.findByIdTablaDesc(data.getReputacionalId());
            eventoRiesgo.setReputacionalId(tablaReputacionalId);

            TablaDescripcion tablaCumplimientoId = tablaDescripcionService.findByIdTablaDesc(data.getCumplimientoId());
            eventoRiesgo.setCumplimientoId(tablaCumplimientoId);

            TablaDescripcion tablaEstrategicoId = tablaDescripcionService.findByIdTablaDesc(data.getEstrategicoId());
            eventoRiesgo.setEstrategicoId(tablaEstrategicoId);

            TablaDescripcion tablaGobiernoId = tablaDescripcionService.findByIdTablaDesc(data.getGobiernoId());
            eventoRiesgo.setGobiernoId(tablaGobiernoId);

            TablaDescripcion tablaSeguridadId = tablaDescripcionService.findByIdTablaDesc(data.getSeguridadId());
            eventoRiesgo.setSeguridadId(tablaSeguridadId);

            // Planes
            TablaDescripcion tablaAreaResponsableId = tablaDescripcionService.findByIdTablaDesc(data.getAreaResponsableId());
            eventoRiesgo.setAreaResponsableId(tablaAreaResponsableId);

            TablaDescripcion tablaCargoResponsableId = tablaDescripcionService.findByIdTablaDesc(data.getCargoResponsableId());
            eventoRiesgo.setCargoResponsableId(tablaCargoResponsableId);



            //Guardando la matriz de riesgos
            List<MatrizRiesgo> matrizRiesgos = matrizRiesgoService.getListMatrizInId(data.getListMatrizRiesgo());
            eventoRiesgo.setRiesgoRelacionado(matrizRiesgos);

            eventoRiesgo.setEstadoRegistro("Pendiente");


            //List<Archivo> archivos = archivoService.create(new ArchivoPostDTOv2(data.getFile(), eventoRiesgo.getId()));


          //  eventoRiesgo.setArchivoId(archivos);

            eventoRiesgoRepository.save(eventoRiesgo);
           // List<Archivo> archivos = archivoService.create(new ArchivoPostDTOv2(data.getFile(), eventoRiesgo.getId()));


        }catch (Exception e){
            Log.log("Error en crear evento de riesgo: ", e);
            return ResponseEntity.badRequest().headers(new HttpHeaders()).body(null);
        }
       return ResponseEntity.ok().headers(new HttpHeaders()).body(eventoRiesgo);
    }


    public ResponseEntity<EventoRiesgoGetDTO> updateById(Long id, EventoRiesgoDTO data) {
        EventoRiesgo eventoRiesgoToEdit = eventoRiesgoRepository.findById(id).orElseThrow(() -> new DBException("Evento Riesgo: ", id));
        EventoRiesgoGetDTO eventoRiesgoGetDTO = new EventoRiesgoGetDTO();
        try {
            BeanUtils.copyProperties(data, eventoRiesgoToEdit);
            eventoRiesgoToEdit= buildEventoToCreateUpdate(eventoRiesgoToEdit,data);

            eventoRiesgoRepository.save(eventoRiesgoToEdit);
            BeanUtils.copyProperties(eventoRiesgoToEdit, eventoRiesgoGetDTO);

        } catch (Exception e) {
            Log.log("Error en actualizacion del evento de riesgo: ", e);
            return ResponseEntity.badRequest().headers(new HttpHeaders()).body(null);

        }
        //return eventoRiesgoGetDTO;
        return ResponseEntity.ok().headers(new HttpHeaders()).body(eventoRiesgoGetDTO);
    }


    public EventoRiesgo findByIdEvento(Long id) {
        Optional<EventoRiesgo> founded = eventoRiesgoRepository.findById(id);
        return founded.get();
    }


    /*public EventoRiesgoGetDTO evaluaEvento (Long id, EventoRiesgoPutDTO data){
        EventoRiesgo eventoRiesgo = eventoRiesgoRepository.findById(id).orElseThrow(()-> new DBException("Tabla Descripcion", id));
        EventoRiesgoGetDTO eventoRiesgoGetDTO = new EventoRiesgoGetDTO();
        try{
            BeanUtils.copyProperties(data, eventoRiesgo);
            eventoRiesgoRepository.save(eventoRiesgo);
            BeanUtils.copyProperties(eventoRiesgo, eventoRiesgoGetDTO);
        } catch (Exception e){
            Log.log("Tabla Descripcion actualizada =>", e);
        }
        //return eventoRiesgoGetDTO;
        return null;
    }*/

    public ResponseEntity<EventoRiesgo> evaluaEvento(Long id, EventoRiesgoPutDTOevaluacion data) {
        HttpHeaders responseHeaders = new HttpHeaders();
        EventoRiesgo eventoRiesgo = eventoRiesgoRepository.findById(id).get();

        try {
            if (data.getEstadoRegistro().equals("Autorizado")) {
                if (eventoRiesgo.getCodigo() == null || eventoRiesgo.getCodigo().isEmpty()) {
                    String codigo = generaCodigo(id);
                    //System.out.println("CODIGO DE EVENTO :   " + codigo);
                    int ultimoIdArea = 0;
                    int countCodigoArea = eventoRiesgoRepository.countEventoCodigo(eventoRiesgo.getAreaID().getClave());
                    if (countCodigoArea > 0) {
                        ultimoIdArea = eventoRiesgoRepository.findUltimoIdArea(eventoRiesgo.getAreaID().getClave());
                        eventoRiesgo.setIdAreaCorrelativo(ultimoIdArea + 1); // Se guarda el incremento del ID correlativo del Evento al autorizar
                        eventoRiesgo.setCodigo(codigo);
                    } else {
                        ultimoIdArea = 1;
                        eventoRiesgo.setIdAreaCorrelativo(ultimoIdArea); // Se guarda el ID correlativo con 1 del Evento al autorizar
                        eventoRiesgo.setCodigo(codigo);
                    }
                }
                eventoRiesgo.setEstadoRegistro(data.getEstadoRegistro());
                eventoRiesgoRepository.save(eventoRiesgo);
            }
            if (data.getEstadoRegistro().equals("Observado")) {
                if (!eventoRiesgo.getEstadoRegistro().equals("Autorizado")) {
                    if (data.getListaObservacion() != null && data.getNota() != null && data.getEstado() != null) {
                        ObservacionPostDTO observacionPostDTO = new ObservacionPostDTO();
                        observacionPostDTO.setListaObservacion(data.getListaObservacion());
                        observacionPostDTO.setNota(data.getNota());
                        observacionPostDTO.setEstado(data.getEstado());
                        observacionPostDTO.setModulo(data.getModulo());
                        observacionService.create(observacionPostDTO, id);

                        eventoRiesgo.setEstadoRegistro(data.getEstadoRegistro());
                        eventoRiesgoRepository.save(eventoRiesgo);
                    }
                }
            }

            if (data.getEstadoRegistro().equals("Pendiente") || data.getEstadoRegistro().equals("Descartado")) {
                if (!eventoRiesgo.getEstadoRegistro().equals("Autorizado")) {
                    eventoRiesgo.setEstadoRegistro(data.getEstadoRegistro());
                    eventoRiesgoRepository.save(eventoRiesgo);
                }
            }
        } catch (Exception e) {
            Log.log("Error al evaluar evento =>", e);
            return ResponseEntity.badRequest().headers(responseHeaders).body(null);
        }
        return ResponseEntity.ok().headers(responseHeaders).body(eventoRiesgo);
    }


    public String generaCodigo(Long id) {
        EventoRiesgo eventoRiesgo = eventoRiesgoRepository.findById(id).get();

        String siglaArea = eventoRiesgo.getAreaID().getClave();
        String anio = eventoRiesgo.getFechaIni().toString().substring(2, 4);
        String codigo = siglaArea.concat('-' + anio);

        //System.out.println("COUNT:  " + eventoRiesgoRepository.countEventoCodigo(eventoRiesgo.getAreaID().getClave()));
        int countCodigoArea = eventoRiesgoRepository.countEventoCodigo(eventoRiesgo.getAreaID().getClave());

        if (countCodigoArea > 0) {
            int ultimoIdArea = eventoRiesgoRepository.findUltimoIdArea(eventoRiesgo.getAreaID().getClave());
            int idIncrementado = ultimoIdArea + 1; // Id correlativo del Evento para el codigo al autorizar
            String idGenerado = "";

            if (idIncrementado < 10) {
                idGenerado = "00" + idIncrementado;
            }
            if (idIncrementado < 100 && idIncrementado > 9) {
                idGenerado = "0" + idIncrementado;
            }
            if (idIncrementado > 99) {
                idGenerado = Integer.toString(idIncrementado);
            }
            codigo = codigo.concat('-' + idGenerado);
        } else {
            String idGenerado = "001";
            codigo = codigo.concat('-' + idGenerado);
        }
        return codigo;
    }

    public EventoRiesgoGetDTO findEventoByID(Long id) {
        Optional<EventoRiesgo> eventoRiesgo = eventoRiesgoRepository.findById(id);
        EventoRiesgoGetDTO eventoRiesgoGetDTO = new EventoRiesgoGetDTO();
        BeanUtils.copyProperties(eventoRiesgo.get(), eventoRiesgoGetDTO);
        return eventoRiesgoGetDTO;
    }

    public List<EventoRiesgo> listEventoRiesgo() {
        return eventoRiesgoRepository.findAllByDeleted(false);
    }

    public List<EventoRiesgo> diezDiasAntes() {
        try{
            return eventoRiesgoRepository.diezDiasAntes();
        }catch (Exception e){
            Log.log("Error en Funcion diezDiasAntes : ", e);
        }
        return new ArrayList<>();
    }

    public List<EventoRiesgo> cincoDiasAntes() {
        try{
            return eventoRiesgoRepository.cincoDiasAntes();
        }catch (Exception e){
            Log.log("Error en Funcion cincoDiasAntes : ", e);
        }
        return new ArrayList<>();
    }

    public List<EventoRiesgo> planVencido() {
        try{
            return eventoRiesgoRepository.planVencido();
        }catch (Exception e){
            Log.log("Error en Funcion planVencido : ", e);
        }
        return new ArrayList<>();
    }


}
