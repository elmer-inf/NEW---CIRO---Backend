package ciro.atc.dao.service;

import ciro.atc.model.dto.Archivo.ArchivoPostDTO;
import ciro.atc.model.dto.Observacion.ObservacionPostDTO;
import ciro.atc.model.entity.Archivo;
import ciro.atc.model.entity.EventoRiesgo;
import ciro.atc.model.entity.Observacion;
import ciro.atc.model.repository.ObservacionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObservacionServiceImpl implements ObservacionService {

    @Autowired
    ObservacionRepository observacionRepository;
    @Autowired
    EventoRiesgoService eventoRiesgoService;

    public Observacion create(ObservacionPostDTO data, Long id) {
        Observacion observacion = new Observacion();
        BeanUtils.copyProperties(data, observacion);
        EventoRiesgo eventoRiesgo = eventoRiesgoService.findByIdEvento(id);
        observacion.setEventoId(eventoRiesgo);
        return observacionRepository.save(observacion);
    }

    /*public Observacion create(ObservacionPostDTO data) {
        System.out.println("entra a funcion crear observacion: ");
        Observacion observacion = new Observacion();
        EventoRiesgo eventoRiesgo = eventoRiesgoService.findByIdEvento(data.getEventoId());
        BeanUtils.copyProperties(data, observacion);
        observacion.setEventoId(eventoRiesgo);
        return observacionRepository.save(observacion);
    }*/
}
