package ciro.atc.dao.service;

import ciro.atc.config.log.Log;
import ciro.atc.exception.DBException;
import ciro.atc.model.dto.Seguridad.SeguridadGetDTO;
import ciro.atc.model.dto.Seguridad.SeguridadPostDTO;
import ciro.atc.model.dto.Seguridad.SeguridadPutDTO;
import ciro.atc.model.entity.Seguridad;
import ciro.atc.model.entity.TablaDescripcion;
import ciro.atc.model.entity.TablaDescripcionSeguridad;
import ciro.atc.model.entity.TablaDescripcionMatrizRiesgo;
import ciro.atc.model.repository.SeguridadRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeguridadServiceImpl implements SeguridadService {

    @Autowired
    SeguridadRepository seguridadRepository;
    @Autowired
    TablaDescripcionService tablaDescripcionService;
    @Autowired
    TablaDescripcionMatrizRiesgoService tablaDescripcionMatrizRiesgoService;
    @Autowired
    TablaDescripcionSeguridadService tablaDescripcionSeguridadService;

    public ResponseEntity<Seguridad> create(SeguridadPostDTO data) {
        HttpHeaders responseHeaders = new HttpHeaders();
        Seguridad seguridad = new Seguridad();
        try{
            BeanUtils.copyProperties(data, seguridad);

            TablaDescripcionSeguridad tablaTipoActivoId = tablaDescripcionSeguridadService.findByIdTablaDesc(data.getTipoActivoId());
            seguridad.setTipoActivoId(tablaTipoActivoId);

            TablaDescripcionSeguridad tablaEstadoId = tablaDescripcionSeguridadService.findByIdTablaDesc(data.getEstadoId());
            seguridad.setEstadoId(tablaEstadoId);

            TablaDescripcionMatrizRiesgo tablaNivelRiesgoId = tablaDescripcionMatrizRiesgoService.findByIdTablaDesc(data.getNivelRiesgoId());
            seguridad.setNivelRiesgoId(tablaNivelRiesgoId);

            TablaDescripcion tablaAreaId = tablaDescripcionService.findByIdTablaDesc(data.getAreaId());
            seguridad.setAreaId(tablaAreaId);

            seguridadRepository.save(seguridad);
        }catch (Exception e) {
            Log.log("Error al guardar registro de Seguridad =>", e);
            return ResponseEntity.badRequest().headers(responseHeaders).body(null);
        }
        return ResponseEntity.ok().headers(responseHeaders).body(seguridad);
    }


    public List<Seguridad> listSeguridad(){
        return seguridadRepository.findAllByDeleted(false);
    }


    public SeguridadGetDTO findSeguridadByID(Long id){
        Optional<Seguridad> seguridad = seguridadRepository.findById(id);
        SeguridadGetDTO seguridadGetDTO = new SeguridadGetDTO();
        BeanUtils.copyProperties(seguridad.get(), seguridadGetDTO);
        return seguridadGetDTO;
    }


    public Seguridad findByIdSeguridad(Long id){
        Optional<Seguridad> founded = seguridadRepository.findById(id);
        return founded.get();
    }


    public SeguridadGetDTO updateById (Long id, SeguridadPutDTO data){
        Seguridad seguridad = seguridadRepository.findById(id).orElseThrow(()-> new DBException("Error al obtener registro de Seguridad: ", id));
        SeguridadGetDTO seguridadGetDTO = new SeguridadGetDTO();
        try{
            TablaDescripcionSeguridad tablaTipoActivoId = tablaDescripcionSeguridadService.findByIdTablaDesc(data.getTipoActivoId());
            seguridad.setTipoActivoId(tablaTipoActivoId);

            TablaDescripcionSeguridad tablaEstadoId = tablaDescripcionSeguridadService.findByIdTablaDesc(data.getEstadoId());
            seguridad.setEstadoId(tablaEstadoId);

            TablaDescripcionMatrizRiesgo tablaNivelRiesgoId = tablaDescripcionMatrizRiesgoService.findByIdTablaDesc(data.getNivelRiesgoId());
            seguridad.setNivelRiesgoId(tablaNivelRiesgoId);

            TablaDescripcion tablaAreaId = tablaDescripcionService.findByIdTablaDesc(data.getAreaId());
            seguridad.setAreaId(tablaAreaId);

            BeanUtils.copyProperties(data, seguridad);
            seguridadRepository.save(seguridad);
            BeanUtils.copyProperties(seguridad, seguridadGetDTO);
        } catch (Exception e){
            Log.log("Error al actualizar registro de Seguridad: ", e);
        }
        return seguridadGetDTO;
    }

    public List<Seguridad> groupByArea(){
        System.out.println("resultado: "+  seguridadRepository.groupByArea());
        return seguridadRepository.groupByArea();
    }

}
