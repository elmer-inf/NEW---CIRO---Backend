package ciro.atc.dao.service;

import ciro.atc.model.dto.EventoRiesgo.EventoRiesgoPostDTO;
import ciro.atc.model.dto.TablaDescripcion.TablaDescripcionPostDTO;
import ciro.atc.model.entity.EventoRiesgo;
import ciro.atc.model.entity.TablaDescripcion;
import ciro.atc.model.entity.TablaLista;
import ciro.atc.model.repository.EventoRiesgoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventoRiesgoServiceImpl implements EventoRiesgoService {

    @Autowired
    EventoRiesgoRepository eventoRiesgoRepository;
    @Autowired
    TablaDescripcionService tablaDescripcionService;

    /*public EventoRiesgo create (EventoRiesgoPostDTO data){
        EventoRiesgo eventoRiesgo = new EventoRiesgo();
        BeanUtils.copyProperties(data, eventoRiesgo);
        return eventoRiesgoRepository.save(eventoRiesgo);
    }*/

    public EventoRiesgo create(EventoRiesgoPostDTO data) {
        EventoRiesgo eventoRiesgo = new EventoRiesgo();
        TablaDescripcion tablaDescripcion = tablaDescripcionService.findByIdTablaDesc(data.getAgenciaId());
        BeanUtils.copyProperties(data, tablaDescripcion);
        eventoRiesgo.setAgenciaId(tablaDescripcion);

        return eventoRiesgoRepository.save(eventoRiesgo);
    }

}
