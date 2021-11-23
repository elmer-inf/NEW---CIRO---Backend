package ciro.atc.dao.service;

import ciro.atc.config.log.Log;
import ciro.atc.exception.DBException;
import ciro.atc.model.dto.TablaDescripcion.TablaDescripcionGetDTO;
import ciro.atc.model.dto.TablaDescripcion.TablaDescripcionGetDTO2;
import ciro.atc.model.dto.TablaDescripcion.TablaDescripcionPostDTO;
import ciro.atc.model.dto.TablaDescripcion.TablaDescripcionPutDTO;
import ciro.atc.model.dto.TablaDescripcionMatrizRiesgo.TablaDescripcionMatrizRiesgoPostDTO;
import ciro.atc.model.entity.TablaDescripcion;
import ciro.atc.model.entity.TablaDescripcionMatrizRiesgo;
import ciro.atc.model.entity.TablaLista;
import ciro.atc.model.entity.TablaListaMatrizRiesgo;
import ciro.atc.model.repository.TablaDescripcionMatrizRiesgoRepository;
import ciro.atc.model.repository.TablaDescripcionRepository;
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

   /*



    public List<TablaDescripcion> findTablaNivel2(Long id, int id2){
        return tablaDescripcionRepository.findNivel2(id, id2);
    }

    public List<TablaDescripcion> findTablaNivel3(Long id, int id2, int id3){
        return tablaDescripcionRepository.findNivel3(id, id2, id3);
    }

    public TablaDescripcionGetDTO updateById (Long id, TablaDescripcionPutDTO data){
        TablaDescripcion tablaDescripcion = tablaDescripcionRepository.findById(id).orElseThrow(()-> new DBException("Tabla Descripcion", id));
        TablaDescripcionGetDTO tablaDescripcionGetDTO = new TablaDescripcionGetDTO();
        try{
            BeanUtils.copyProperties(data, tablaDescripcion);
            tablaDescripcionRepository.save(tablaDescripcion);
            BeanUtils.copyProperties(tablaDescripcion, tablaDescripcionGetDTO);
        } catch (Exception e){
            Log.log("Tabla Descripcion actualizada =>", e);
        }
        return tablaDescripcionGetDTO;
    }

    public TablaDescripcionGetDTO findTablaDescripcionByID(Long id){
        Optional<TablaDescripcion> tablaDescripcion = tablaDescripcionRepository.findById(id);
        TablaDescripcionGetDTO tablaDescripcionGetDTO = new TablaDescripcionGetDTO();
        BeanUtils.copyProperties(tablaDescripcion.get(), tablaDescripcionGetDTO);
        return tablaDescripcionGetDTO;
    }

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

    public TablaDescripcionMatrizRiesgo findByIdTablaDesc(Long id){
        try {
            Optional<TablaDescripcionMatrizRiesgo> founded = tablaDescripcionMatrizRiesgoRepository.findById(id);
            return founded.get();
        }catch (Exception e){
            Log.log("Error : " , e);
        }
        return null;
    }

}



