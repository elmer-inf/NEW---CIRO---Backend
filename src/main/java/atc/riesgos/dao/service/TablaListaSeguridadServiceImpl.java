package atc.riesgos.dao.service;

import atc.riesgos.model.dto.TablaListaSeguridad.TablaListaSeguridadPostDTO;
import atc.riesgos.model.entity.TablaListaSeguridad;
import atc.riesgos.model.repository.TablaListaSeguridadRepository;
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
            return ResponseEntity.badRequest().headers(responseHeaders).body(null);
        }

        return ResponseEntity.ok().headers(responseHeaders).body(tablaListaSeguridad);
    }

    public TablaListaSeguridad findByIdTabla(Long id){
        try {
            Optional<TablaListaSeguridad> founded = tablaListaSeguridadRepository.findById(id);
            return founded.get();
        }catch (Exception e){
            System.out.println("Error: " + e);
        }
        return null;
    }

    public List<TablaListaSeguridad> listTabla(){
        return tablaListaSeguridadRepository.findAllByDeleted(false);
    }

}



