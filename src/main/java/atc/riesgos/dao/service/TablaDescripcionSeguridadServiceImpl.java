package atc.riesgos.dao.service;

import atc.riesgos.model.dto.TablaDescripcionSeguridad.TablaDescripcionSeguridadGetDTO;
import atc.riesgos.model.dto.TablaDescripcionSeguridad.TablaDescripcionSeguridadPostDTO;
import atc.riesgos.model.dto.TablaDescripcionSeguridad.TablaDescripcionSeguridadPutDTO;
import atc.riesgos.model.entity.TablaDescripcionMatrizRiesgo;
import atc.riesgos.model.entity.TablaDescripcionSeguridad;
import atc.riesgos.model.entity.TablaListaSeguridad;
import atc.riesgos.model.repository.TablaDescripcionSeguridadRepository;
import atc.riesgos.exception.DBException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
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
            System.out.println("Error: " + e);
        }
        return null;
    }

    public TablaDescripcionSeguridad deleteById(Long id) {
        TablaDescripcionSeguridad tablaDescripcionToDelete = tablaDescripcionSeguridadRepository.findByIdAndDeleted(id, false).get();
        return tablaDescripcionSeguridadRepository.save(delete(tablaDescripcionToDelete));
    }

    final public <T extends Object> T delete(T obj) {
        try {
            Class<?> cls = obj.getClass();
            Field field = cls.getDeclaredField("deleted");
            field.setAccessible(true);
            field.set(obj, true);
        } catch (IllegalAccessException | IllegalArgumentException
                | NoSuchFieldException | SecurityException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return obj;
    }

}



