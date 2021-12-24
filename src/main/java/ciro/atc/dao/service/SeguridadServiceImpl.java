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

            TablaDescripcionSeguridad tablaTipoActivoInteresId = tablaDescripcionSeguridadService.findByIdTablaDesc(data.getTipoActivoId());
            seguridad.setTipoActivoId(tablaTipoActivoInteresId);

            TablaDescripcionSeguridad tablaEstadoId = tablaDescripcionSeguridadService.findByIdTablaDesc(data.getEstadoId());
            seguridad.setEstadoId(tablaEstadoId);

            TablaDescripcionMatrizRiesgo tablaImpactoOporId = tablaDescripcionMatrizRiesgoService.findByIdTablaDesc(data.getNivelRiesgoId());
            seguridad.setNivelRiesgoId(tablaImpactoOporId);

            TablaDescripcion tablaFortalezaId = tablaDescripcionService.findByIdTablaDesc(data.getAreaId());
            seguridad.setAreaId(tablaFortalezaId);

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


    /*public SeguridadGetDTO findSeguridadByID(Long id){
        Optional<Seguridad> seguridad = seguridadRepository.findById(id);
        SeguridadGetDTO seguridadGetDTO = new SeguridadGetDTO();
        BeanUtils.copyProperties(seguridad.get(), seguridadGetDTO);
        return seguridadGetDTO;
    }


    public Seguridad findByIdOportunidad(Long id){
        Optional<Seguridad> founded = seguridadRepository.findById(id);
        return founded.get();
    }


    public SeguridadGetDTO updateById (Long id, SeguridadPutDTO data){
        Seguridad matrizOportunidad = seguridadRepository.findById(id).orElseThrow(()-> new DBException("Matriz de oportunidad: ", id));
        SeguridadGetDTO matrizOportunidadGetDTO = new SeguridadGetDTO();
        try{
            TablaDescripcionSeguridad tablaGrupoInteresId = tablaDescripcionSeguridadService.findByIdTablaDesc(data.getGrupoInteresId());
            matrizOportunidad.setGrupoInteresId(tablaGrupoInteresId);

            TablaDescripcionMatrizRiesgo tablaProbabilidadId = tablaDescripcionMatrizRiesgoService.findByIdTablaDesc(data.getProbabilidadId());
            matrizOportunidad.setProbabilidadId(tablaProbabilidadId);

            TablaDescripcionSeguridad tablaImpactoOporId = tablaDescripcionSeguridadService.findByIdTablaDesc(data.getImpactoOporId());
            matrizOportunidad.setImpactoOporId(tablaImpactoOporId);

            TablaDescripcionSeguridad tablaFortalezaId = tablaDescripcionSeguridadService.findByIdTablaDesc(data.getFortalezaId());
            matrizOportunidad.setFortalezaId(tablaFortalezaId);

            BeanUtils.copyProperties(data, matrizOportunidad);
            seguridadRepository.save(matrizOportunidad);
            BeanUtils.copyProperties(matrizOportunidad, matrizOportunidadGetDTO);
        } catch (Exception e){
            Log.log("Matriz de oportunidad actualizado: ", e);
        }
        return matrizOportunidadGetDTO;
    }*/

}
