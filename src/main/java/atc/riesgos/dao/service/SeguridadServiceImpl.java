package atc.riesgos.dao.service;

import atc.riesgos.model.dto.Seguridad.SeguridadGetDTO;
import atc.riesgos.model.dto.Seguridad.SeguridadPostDTO;
import atc.riesgos.model.dto.Seguridad.SeguridadPutDTO;
import atc.riesgos.model.entity.Seguridad;
import atc.riesgos.model.entity.TablaDescripcionMatrizRiesgo;
import atc.riesgos.model.entity.TablaDescripcionSeguridad;
import atc.riesgos.model.repository.SeguridadRepository;
//import atc.riesgos.config.log.Log;
import atc.riesgos.exception.DBException;
import atc.riesgos.model.entity.TablaDescripcion;
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
            //Log.log("Error al guardar registro de Seguridad =>", e);
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
            //Log.log("Error al actualizar registro de Seguridad: ", e);
            System.out.println("Error al actualizar registro de Seguridad: " + e);
        }
        return seguridadGetDTO;
    }

    public List<Seguridad> groupByArea(){
        //System.out.println("resultado: "+  seguridadRepository.groupByArea());
        return seguridadRepository.groupByArea();
    }

}
