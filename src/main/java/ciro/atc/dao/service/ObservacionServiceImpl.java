package ciro.atc.dao.service;

import ciro.atc.config.log.Log;
import ciro.atc.model.dto.Observacion.ObservacionGetDTO;
import ciro.atc.model.dto.Observacion.ObservacionPostDTO;
import ciro.atc.model.entity.EventoRiesgo;
import ciro.atc.model.entity.MatrizOportunidad;
import ciro.atc.model.entity.MatrizRiesgo;
import ciro.atc.model.entity.Observacion;
import ciro.atc.model.repository.EventoRiesgoRepository;
import ciro.atc.model.repository.ObservacionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ObservacionServiceImpl implements ObservacionService {

    @Autowired
    ObservacionRepository observacionRepository;
    @Autowired
    EventoRiesgoService eventoRiesgoService;
    @Autowired
    MatrizRiesgoService matrizRiesgoService;
    @Autowired
    MatrizOportunidadService matrizOportunidadService;
    @Autowired
    EventoRiesgoRepository eventoRiesgoRepository;

    public Observacion create(ObservacionPostDTO data, Long id) {
        System.out.println("daata modulo: "+ data.getModulo());
        Observacion observacion = new Observacion();
        BeanUtils.copyProperties(data, observacion);

        if(data.getModulo().equals("Evento")){
            EventoRiesgo eventoRiesgo = eventoRiesgoService.findByIdEvento(id);
            observacion.setEventoId(eventoRiesgo);
        }
        if(data.getModulo().equals("Riesgo")){
            MatrizRiesgo matrizRiesgo = matrizRiesgoService.findByIdRiesgo(id);
            observacion.setMatrizRiesgoId(matrizRiesgo);
        }
        if(data.getModulo().equals("Oportunidad")){
            MatrizOportunidad matrizOportunidad = matrizOportunidadService.findByIdOportunidad(id);
            observacion.setMatrizOportunidadId(matrizOportunidad);
        }
        return observacionRepository.save(observacion);
    }

    /*public ObservacionGetDTO findUltimaObservacionEvento(Long id){
        //EventoRiesgo eventoRiesgo = eventoRiesgoRepository.findById(id).get();

        int nroObservaciones = observacionRepository.countObservacionEvento(id);
        System.out.println("count::::::::::::::: " + nroObservaciones);

        return null;
    }*/

    public ObservacionGetDTO ultimaObservacionEvento(Long id){
        try{
            Integer nroObservaciones = observacionRepository.countObservacionEvento(id);
            if(nroObservaciones > 0){
                Long idUltimaObsEvento = observacionRepository.ultimaObservacionEvento(id);

                Optional<Observacion> observacion = observacionRepository.findById(idUltimaObsEvento);
                ObservacionGetDTO observacionGetDTO = new ObservacionGetDTO();
                BeanUtils.copyProperties(observacion.get(), observacionGetDTO);
                EventoRiesgo x = observacion.get().getEventoId();
                observacionGetDTO.setEventoId(x.getId());

                return observacionGetDTO;
            }
        }catch(Exception e){
            Log.log("Error al obtener ID de observacion =>", e);
        }
        return null;
    }

    public ObservacionGetDTO ultimaObservacionRiesgo(Long id){
        try{
            Integer nroObservaciones = observacionRepository.countObservacionRiesgo(id);
            if(nroObservaciones > 0){
                Long idUltimaObsRiesgo = observacionRepository.ultimaObservacionRiesgo(id);

                Optional<Observacion> observacion = observacionRepository.findById(idUltimaObsRiesgo);
                ObservacionGetDTO observacionGetDTO = new ObservacionGetDTO();
                BeanUtils.copyProperties(observacion.get(), observacionGetDTO);
                MatrizRiesgo x = observacion.get().getMatrizRiesgoId();
                observacionGetDTO.setMatrizRiesgoId(x.getId());

                return observacionGetDTO;
            }
        }catch(Exception e){
            Log.log("Error al obtener ID de observacion =>", e);
        }
        return null;
    }

    public ObservacionGetDTO ultimaObservacionOportunidad(Long id){
        try{
            Integer nroObservaciones = observacionRepository.countObservacionOportunidad(id);
            if(nroObservaciones > 0){
                Long idUltimaObsOportunidad = observacionRepository.ultimaObservacionOportunidad(id);

                Optional<Observacion> observacion = observacionRepository.findById(idUltimaObsOportunidad);
                ObservacionGetDTO observacionGetDTO = new ObservacionGetDTO();
                BeanUtils.copyProperties(observacion.get(), observacionGetDTO);
                MatrizOportunidad x = observacion.get().getMatrizOportunidadId();
                observacionGetDTO.setMatrizOportunidadId(x.getId());

                return observacionGetDTO;
            }
        }catch(Exception e){
            Log.log("Error al obtener ID de observacion =>", e);
        }
        return null;
    }

   /* public ObservacionGetDTO findByIdObservacion(Long id){
        try{
            Optional<Observacion> observacion = observacionRepository.findById(id);
            ObservacionGetDTO observacionGetDTO = new ObservacionGetDTO();
            BeanUtils.copyProperties(observacion.get(), observacionGetDTO);
            EventoRiesgo  x = observacion.get().getEventoId();
            observacionGetDTO.setEventoId(x.getId());
            return observacionGetDTO;
        }catch(Exception e){
            Log.log("Error al obtener ID de observacion =>", e);
        }
        return null;

    }*/

    /*public Observacion create(ObservacionPostDTO data) {
        System.out.println("entra a funcion crear observacion: ");
        Observacion observacion = new Observacion();
        EventoRiesgo eventoRiesgo = eventoRiesgoService.findByIdEvento(data.getEventoId());
        BeanUtils.copyProperties(data, observacion);
        observacion.setEventoId(eventoRiesgo);
        return observacionRepository.save(observacion);
    }*/
}
