package ciro.atc.dao.service;

import ciro.atc.config.log.Log;
import ciro.atc.exception.DBException;
import ciro.atc.model.dto.TablaDescripcionSeguridad.TablaDescripcionSeguridadGetDTO;
import ciro.atc.model.dto.TablaDescripcionSeguridad.TablaDescripcionSeguridadPostDTO;
import ciro.atc.model.dto.TablaDescripcionSeguridad.TablaDescripcionSeguridadPutDTO;
import ciro.atc.model.entity.TablaDescripcionSeguridad;
import ciro.atc.model.entity.TablaListaSeguridad;
import ciro.atc.model.repository.TablaDescripcionSeguridadRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TablaDescripcionSeguridadServiceImpl implements TablaDescripcionSeguridadService {

    @Autowired
    TablaDescripcionSeguridadRepository tablaDescripcionSeguridadRepository;
    @Autowired
    TablaListaSeguridadService tablaListaSeguridadService;

    public ResponseEntity<TablaDescripcionSeguridad> create(TablaDescripcionSeguridadPostDTO data) {
        HttpHeaders responseHeaders = new HttpHeaders();
        TablaDescripcionSeguridad tablaDescripcionSeguridad = new TablaDescripcionSeguridad();
        try{
            TablaListaSeguridad tablaListaSeguridad = tablaListaSeguridadService.findByIdTabla(data.getTablaId());
            BeanUtils.copyProperties(data, tablaDescripcionSeguridad);
            tablaDescripcionSeguridad.setTablaId(tablaListaSeguridad);
            tablaDescripcionSeguridadRepository.save(tablaDescripcionSeguridad);
        } catch (Exception e) {
            Log.log("Error al guardar Tabla descripcion Seguridad =>", e);
            return ResponseEntity.badRequest().headers(responseHeaders).body(null);
        }
        return ResponseEntity.ok().headers(responseHeaders).body(tablaDescripcionSeguridad);
    }

    public List<TablaDescripcionSeguridad> listTablaDescripcionSeguridad(){
        return tablaDescripcionSeguridadRepository.findAllByDeleted(false);
    }

    public List<TablaDescripcionSeguridad> findTablaNivel1(Long id){
        return tablaDescripcionSeguridadRepository.findNivel1(id);
    }

    public ResponseEntity<TablaDescripcionSeguridadGetDTO> updateById (Long id, TablaDescripcionSeguridadPutDTO data){
        HttpHeaders responseHeaders = new HttpHeaders();
        TablaDescripcionSeguridad tablaDescripcionMatrizR = tablaDescripcionSeguridadRepository.findById(id).orElseThrow(()-> new DBException("Tabla Descripcion", id));
        TablaDescripcionSeguridadGetDTO tablaDescripcionMatrizRGetDTO = new TablaDescripcionSeguridadGetDTO();
        try{
            BeanUtils.copyProperties(data, tablaDescripcionMatrizR);
            tablaDescripcionSeguridadRepository.save(tablaDescripcionMatrizR);
            BeanUtils.copyProperties(tablaDescripcionMatrizR, tablaDescripcionMatrizRGetDTO);
        } catch (Exception e){
            Log.log("Tabla Descripcion actualizada =>", e);
            return ResponseEntity.badRequest().headers(responseHeaders).body(null);
        }
        return ResponseEntity.ok().headers(responseHeaders).body(tablaDescripcionMatrizRGetDTO);
    }

    public TablaDescripcionSeguridadGetDTO findTablaDescripcionByID(Long id){
        Optional<TablaDescripcionSeguridad> tablaDescripcionMatrizR = tablaDescripcionSeguridadRepository.findById(id);
        TablaDescripcionSeguridadGetDTO tablaDescripcionMatrizRGetDTO = new TablaDescripcionSeguridadGetDTO();
        BeanUtils.copyProperties(tablaDescripcionMatrizR.get(), tablaDescripcionMatrizRGetDTO);
        return tablaDescripcionMatrizRGetDTO;
    }

    public TablaDescripcionSeguridad findByIdTablaDesc(Long id){
        try {
            Optional<TablaDescripcionSeguridad> founded = tablaDescripcionSeguridadRepository.findById(id);
            return founded.get();
        }catch (Exception e){
            Log.log("Error : " , e);
        }
        return null;
    }

}



