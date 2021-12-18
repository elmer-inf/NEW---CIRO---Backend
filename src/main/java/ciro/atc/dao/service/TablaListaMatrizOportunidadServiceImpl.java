package ciro.atc.dao.service;

import ciro.atc.config.log.Log;
import ciro.atc.model.dto.TablaListaMatrizOportunidad.TablaListaMatrizOportunidadPostDTO;
import ciro.atc.model.entity.TablaListaMatrizOportunidad;
import ciro.atc.model.repository.TablaListaMatrizOportunidadRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TablaListaMatrizOportunidadServiceImpl implements TablaListaMatrizOportunidadService {

    @Autowired
    TablaListaMatrizOportunidadRepository tablaListaMatrizOportunidadRepository;

    public ResponseEntity<TablaListaMatrizOportunidad> create (TablaListaMatrizOportunidadPostDTO data){
        HttpHeaders responseHeaders = new HttpHeaders();
        TablaListaMatrizOportunidad tablaListaMatrizOportunidad = new TablaListaMatrizOportunidad();
        try {
            BeanUtils.copyProperties(data, tablaListaMatrizOportunidad);
            tablaListaMatrizOportunidadRepository.save(tablaListaMatrizOportunidad);
        } catch (Exception e) {
        Log.log("Guardar Tabla lista Matriz de oportunidad =>", e);
        return ResponseEntity.badRequest().headers(responseHeaders).body(null);

        }
        return ResponseEntity.ok().headers(responseHeaders).body(tablaListaMatrizOportunidad);
    }

    public TablaListaMatrizOportunidad findByIdTabla(Long id){
        try {
            Optional<TablaListaMatrizOportunidad> founded = tablaListaMatrizOportunidadRepository.findById(id);
            return founded.get();
        }catch (Exception e){
            Log.log("Error : " , e);
        }
        return null;
    }

    public List<TablaListaMatrizOportunidad> listTabla(){
        return tablaListaMatrizOportunidadRepository.findAllByDeleted(false);
    }

}



