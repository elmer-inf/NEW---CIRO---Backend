package atc.riesgos.dao.service;

import atc.riesgos.config.log.Log;
import atc.riesgos.model.dto.TablaListaMatrizRiesgo.TablaListaMatrizRiesgoPostDTO;
import atc.riesgos.model.entity.TablaDescripcion;
import atc.riesgos.model.entity.TablaListaMatrizRiesgo;
import atc.riesgos.model.repository.TablaListaMatrizRiesgoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TablaListaMatrizRiesgoServiceImpl implements TablaListaMatrizRiesgoService {

    @Autowired
    TablaListaMatrizRiesgoRepository tablaListaMatrizRiesgoRepository;

    public ResponseEntity<TablaListaMatrizRiesgo> create (TablaListaMatrizRiesgoPostDTO data){
        HttpHeaders responseHeaders = new HttpHeaders();
        TablaListaMatrizRiesgo tablaListaMatrizRiesgo = new TablaListaMatrizRiesgo();
        try {
            BeanUtils.copyProperties(data, tablaListaMatrizRiesgo);
            tablaListaMatrizRiesgoRepository.save(tablaListaMatrizRiesgo);
        } catch (Exception e) {
        Log.log("Guardar Tabla lista Matriz fr riesgo =>", e);
        return ResponseEntity.badRequest().headers(responseHeaders).body(null);

        }

        return ResponseEntity.ok().headers(responseHeaders).body(tablaListaMatrizRiesgo);
    }

    public TablaListaMatrizRiesgo findByIdTabla(Long id){
        try {
            Optional<TablaListaMatrizRiesgo> founded = tablaListaMatrizRiesgoRepository.findById(id);
            return founded.get();
        }catch (Exception e){
            Log.log("Error : " , e);
        }
        return null;
    }

    public List<TablaListaMatrizRiesgo> listTabla(){
        return tablaListaMatrizRiesgoRepository.findAllByDeleted(false);
    }

  /*

    public TablaListaGetDTO updateById (Long id, TablaListaPutDTO data){
        TablaLista tablaLista = tablaListaRepository.findById(id).orElseThrow(()-> new DBException("Tabla Lista", id));
        TablaListaGetDTO tablaListaGetDTO = new TablaListaGetDTO();
        try{
            BeanUtils.copyProperties(data, tablaLista);
            tablaListaRepository.save(tablaLista);
            BeanUtils.copyProperties(tablaLista, tablaListaGetDTO);
        } catch (Exception e){
            Log.log("Tabla Lista actualizada =>", e);
        }
        return tablaListaGetDTO;
    }

    public TablaListaGetDTO findTablaListaByID(Long id){
        Optional<TablaLista> tablaLista = tablaListaRepository.findById(id);
        TablaListaGetDTO tablaListaGetDTO = new TablaListaGetDTO();
        BeanUtils.copyProperties(tablaLista.get(), tablaListaGetDTO);
        return tablaListaGetDTO;
    }

    public TablaLista deleteById(Long id) {
        TablaLista tablaListaToDelete = tablaListaRepository.findByIdAndDeleted(id, false).get();
        return tablaListaRepository.save(delete(tablaListaToDelete));
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
    }*/

}



