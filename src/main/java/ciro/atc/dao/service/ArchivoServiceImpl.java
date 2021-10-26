package ciro.atc.dao.service;

import ciro.atc.model.dto.Archivo.ArchivoPostDTO;
import ciro.atc.model.dto.TablaDescripcion.TablaDescripcionPostDTO;
import ciro.atc.model.entity.Archivo;
import ciro.atc.model.entity.EventoRiesgo;
import ciro.atc.model.repository.ArchivoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArchivoServiceImpl implements ArchivoService{

    @Autowired
    ArchivoRepository archivoRepository;
    @Autowired
    EventoRiesgoService eventoRiesgoService;

    public Archivo create(ArchivoPostDTO data) {
        Archivo archivo = new Archivo();
        EventoRiesgo eventoRiesgo = eventoRiesgoService.findByIdEvento(data.getEventoId());
        BeanUtils.copyProperties(data, archivo);
        archivo.setEventoId(eventoRiesgo);

        return archivoRepository.save(archivo);
    }
}
