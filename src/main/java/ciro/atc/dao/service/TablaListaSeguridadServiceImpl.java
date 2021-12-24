package ciro.atc.dao.service;

import ciro.atc.config.log.Log;
import ciro.atc.model.dto.TablaListaSeguridad.TablaListaSeguridadPostDTO;
import ciro.atc.model.entity.TablaListaSeguridad;
import ciro.atc.model.repository.TablaListaSeguridadRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TablaListaSeguridadServiceImpl implements TablaListaSeguridadService {

    @Autowired
    TablaListaSeguridadRepository tablaListaSeguridadRepository;

    public ResponseEntity<TablaListaSeguridad> create (TablaListaSeguridadPostDTO data){
        HttpHeaders responseHeaders = new HttpHeaders();
        TablaListaSeguridad tablaListaSeguridad = new TablaListaSeguridad();
        try {
            BeanUtils.copyProperties(data, tablaListaSeguridad);
            tablaListaSeguridadRepository.save(tablaListaSeguridad);
        } catch (Exception e) {
        Log.log("Error al guardar Tabla lista Seguridad =>", e);
        return ResponseEntity.badRequest().headers(responseHeaders).body(null);
        }
        return ResponseEntity.ok().headers(responseHeaders).body(tablaListaSeguridad);
    }

    public TablaListaSeguridad findByIdTabla(Long id){
        try {
            Optional<TablaListaSeguridad> founded = tablaListaSeguridadRepository.findById(id);
            return founded.get();
        }catch (Exception e){
            Log.log("Error : " , e);
        }
        return null;
    }

    public List<TablaListaSeguridad> listTabla(){
        return tablaListaSeguridadRepository.findAllByDeleted(false);
    }

}



