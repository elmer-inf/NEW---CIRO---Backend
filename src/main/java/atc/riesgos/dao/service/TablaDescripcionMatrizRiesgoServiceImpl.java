package atc.riesgos.dao.service;

//import atc.riesgos.config.log.Log;
import atc.riesgos.exception.DBException;
import atc.riesgos.model.dto.TablaDescripcionMatrizRiesgo.TablaDescripcionMatrizRiesgoGetDTO;
import atc.riesgos.model.dto.TablaDescripcionMatrizRiesgo.TablaDescripcionMatrizRiesgoPostDTO;
import atc.riesgos.model.dto.TablaDescripcionMatrizRiesgo.TablaDescripcionMatrizRiesgoPutDTO;
import atc.riesgos.model.entity.TablaDescripcion;
import atc.riesgos.model.entity.TablaDescripcionMatrizRiesgo;
import atc.riesgos.model.entity.TablaListaMatrizRiesgo;
import atc.riesgos.model.repository.TablaDescripcionMatrizRiesgoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@Service
public class TablaDescripcionMatrizRiesgoServiceImpl implements TablaDescripcionMatrizRiesgoService {

    @Autowired
    TablaDescripcionMatrizRiesgoRepository tablaDescripcionMatrizRiesgoRepository;
    @Autowired
    TablaListaMatrizRiesgoService tablaListaMatrizRiesgoService;

    public ResponseEntity<TablaDescripcionMatrizRiesgo> create(TablaDescripcionMatrizRiesgoPostDTO data) {
        HttpHeaders responseHeaders = new HttpHeaders();
        TablaDescripcionMatrizRiesgo tablaDescripcionMatrizRiesgo = new TablaDescripcionMatrizRiesgo();
        try{
            TablaListaMatrizRiesgo tablaListaMatrizRiesgo = tablaListaMatrizRiesgoService.findByIdTabla(data.getTablaId());
            BeanUtils.copyProperties(data, tablaDescripcionMatrizRiesgo);
            tablaDescripcionMatrizRiesgo.setTablaId(tablaListaMatrizRiesgo);
            tablaDescripcionMatrizRiesgoRepository.save(tablaDescripcionMatrizRiesgo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().headers(responseHeaders).body(null);
        }
        return ResponseEntity.ok().headers(responseHeaders).body(tablaDescripcionMatrizRiesgo);
    }

    public List<TablaDescripcionMatrizRiesgo> listTablaDescripcionMatrizR(){
        return tablaDescripcionMatrizRiesgoRepository.findAllByDeleted(false);
    }

    public List<TablaDescripcionMatrizRiesgo> findTablaNivel1(Long id){
        return tablaDescripcionMatrizRiesgoRepository.findNivel1(id);
    }

    public List<TablaDescripcionMatrizRiesgo> findTablaNivel2(Long id, int id2){
        return tablaDescripcionMatrizRiesgoRepository.findNivel2(id, id2);
    }

    public ResponseEntity<TablaDescripcionMatrizRiesgoGetDTO> updateById (Long id, TablaDescripcionMatrizRiesgoPutDTO data){
        HttpHeaders responseHeaders = new HttpHeaders();
        TablaDescripcionMatrizRiesgo tablaDescripcionMatrizR = tablaDescripcionMatrizRiesgoRepository.findById(id).orElseThrow(()-> new DBException("Tabla Descripcion", id));
        TablaDescripcionMatrizRiesgoGetDTO tablaDescripcionMatrizRGetDTO = new TablaDescripcionMatrizRiesgoGetDTO();
        try{
            BeanUtils.copyProperties(data, tablaDescripcionMatrizR);
            tablaDescripcionMatrizRiesgoRepository.save(tablaDescripcionMatrizR);
            BeanUtils.copyProperties(tablaDescripcionMatrizR, tablaDescripcionMatrizRGetDTO);
        } catch (Exception e){
            return ResponseEntity.badRequest().headers(responseHeaders).body(null);
        }
        return ResponseEntity.ok().headers(responseHeaders).body(tablaDescripcionMatrizRGetDTO);
    }

    public TablaDescripcionMatrizRiesgoGetDTO findTablaDescripcionByID(Long id){
        Optional<TablaDescripcionMatrizRiesgo> tablaDescripcionMatrizR = tablaDescripcionMatrizRiesgoRepository.findById(id);
        TablaDescripcionMatrizRiesgoGetDTO tablaDescripcionMatrizRGetDTO = new TablaDescripcionMatrizRiesgoGetDTO();
        BeanUtils.copyProperties(tablaDescripcionMatrizR.get(), tablaDescripcionMatrizRGetDTO);
        return tablaDescripcionMatrizRGetDTO;
    }

    public TablaDescripcionMatrizRiesgo findByIdTablaDesc(Long id){
        try {
            Optional<TablaDescripcionMatrizRiesgo> founded = tablaDescripcionMatrizRiesgoRepository.findById(id);
            return founded.get();
        }catch (Exception e){
            System.out.println("Error: "+ e);
        }
        return null;
    }

    public TablaDescripcionMatrizRiesgo deleteById(Long id) {
        TablaDescripcionMatrizRiesgo tablaDescripcionToDelete = tablaDescripcionMatrizRiesgoRepository.findByIdAndDeleted(id, false).get();
        return tablaDescripcionMatrizRiesgoRepository.save(delete(tablaDescripcionToDelete));
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



