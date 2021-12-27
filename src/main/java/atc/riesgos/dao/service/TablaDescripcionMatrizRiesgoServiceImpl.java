package atc.riesgos.dao.service;

import atc.riesgos.config.log.Log;
import atc.riesgos.exception.DBException;
import atc.riesgos.model.dto.TablaDescripcionMatrizRiesgo.TablaDescripcionMatrizRiesgoGetDTO;
import atc.riesgos.model.dto.TablaDescripcionMatrizRiesgo.TablaDescripcionMatrizRiesgoPostDTO;
import atc.riesgos.model.dto.TablaDescripcionMatrizRiesgo.TablaDescripcionMatrizRiesgoPutDTO;
import atc.riesgos.model.entity.TablaDescripcionMatrizRiesgo;
import atc.riesgos.model.entity.TablaListaMatrizRiesgo;
import atc.riesgos.model.repository.TablaDescripcionMatrizRiesgoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
            Log.log("Guardar Tabla descripcion Matriz de riesgo =>", e);
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

    public ResponseEntity<TablaDescripcionMatrizRiesgoGetDTO> updateById (Long id, TablaDescripcionMatrizRiesgoPutDTO data){
        HttpHeaders responseHeaders = new HttpHeaders();
        TablaDescripcionMatrizRiesgo tablaDescripcionMatrizR = tablaDescripcionMatrizRiesgoRepository.findById(id).orElseThrow(()-> new DBException("Tabla Descripcion", id));
        TablaDescripcionMatrizRiesgoGetDTO tablaDescripcionMatrizRGetDTO = new TablaDescripcionMatrizRiesgoGetDTO();
        try{
            BeanUtils.copyProperties(data, tablaDescripcionMatrizR);
            tablaDescripcionMatrizRiesgoRepository.save(tablaDescripcionMatrizR);
            BeanUtils.copyProperties(tablaDescripcionMatrizR, tablaDescripcionMatrizRGetDTO);
        } catch (Exception e){
            Log.log("Tabla Descripcion actualizada =>", e);
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
            Log.log("Error : " , e);
        }
        return null;
    }


   /*

    public TablaDescripcionGetDTO2 findTablaDescripcionByID2(Long id){

        Optional<TablaDescripcion> tablaDescripcion = tablaDescripcionRepository.findById(id);
        TablaDescripcionGetDTO tablaDescripcionGetDTO = new TablaDescripcionGetDTO();
        BeanUtils.copyProperties(tablaDescripcion.get(), tablaDescripcionGetDTO);

        TablaDescripcionGetDTO2  objeto = new TablaDescripcionGetDTO2();

        int nivel2 = tablaDescripcionGetDTO.getNivel2_id();
        if(nivel2 != 0) {
            Optional<TablaDescripcion> findNivel2 = tablaDescripcionRepository.findById(new Long(nivel2));
            objeto.setNivel2_id(findNivel2.get());
        }

        int nivel3 = tablaDescripcionGetDTO.getNivel3_id();
        if(nivel3 != 0) {
            Optional<TablaDescripcion> findNivel3 = tablaDescripcionRepository.findById(new Long(nivel3));
            objeto.setNivel3_id(findNivel3.get());
        }
        BeanUtils.copyProperties(tablaDescripcion.get(), objeto);
        return objeto;
    }


    public TablaDescripcion deleteById(Long id) {
        TablaDescripcion tablaDescripcionToDelete = tablaDescripcionRepository.findByIdAndDeleted(id, false).get();
        return tablaDescripcionRepository.save(delete(tablaDescripcionToDelete));
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

    */



}



