package atc.riesgos.dao.service;

import atc.riesgos.model.dto.TablaDescripcionMatrizOportunidad.TablaDescripcionMatrizOportunidadPutDTO;
import atc.riesgos.model.entity.TablaDescripcionMatrizRiesgo;
import atc.riesgos.model.entity.TablaListaMatrizOportunidad;
import atc.riesgos.exception.DBException;
import atc.riesgos.model.dto.TablaDescripcionMatrizOportunidad.TablaDescripcionMatrizOportunidadGetDTO;
import atc.riesgos.model.dto.TablaDescripcionMatrizOportunidad.TablaDescripcionMatrizOportunidadGetDTO2;
import atc.riesgos.model.dto.TablaDescripcionMatrizOportunidad.TablaDescripcionMatrizOportunidadPostDTO;
import atc.riesgos.model.entity.TablaDescripcionMatrizOportunidad;
import atc.riesgos.model.repository.TablaDescripcionMatrizOportunidadRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@Service
public class TablaDescripcionMatrizOportunidadServiceImpl implements TablaDescripcionMatrizOportunidadService {

    @Autowired
    TablaDescripcionMatrizOportunidadRepository tablaDescripcionMatrizOportunidadRepository;
    @Autowired
    TablaListaMatrizOportunidadService tablaListaMatrizOportunidadService;

    public ResponseEntity<TablaDescripcionMatrizOportunidad> create(TablaDescripcionMatrizOportunidadPostDTO data) {
        HttpHeaders responseHeaders = new HttpHeaders();
        TablaDescripcionMatrizOportunidad tablaDescripcionMatrizOportunidad = new TablaDescripcionMatrizOportunidad();
        try{
            TablaListaMatrizOportunidad tablaListaMatrizOportunidad = tablaListaMatrizOportunidadService.findByIdTabla(data.getTablaId());
            BeanUtils.copyProperties(data, tablaDescripcionMatrizOportunidad);
            tablaDescripcionMatrizOportunidad.setTablaId(tablaListaMatrizOportunidad);
            tablaDescripcionMatrizOportunidadRepository.save(tablaDescripcionMatrizOportunidad);
        } catch (Exception e) {
            return ResponseEntity.badRequest().headers(responseHeaders).body(null);
        }
        return ResponseEntity.ok().headers(responseHeaders).body(tablaDescripcionMatrizOportunidad);
    }

    public List<TablaDescripcionMatrizOportunidad> listTablaDescripcionMatrizO(){
        return tablaDescripcionMatrizOportunidadRepository.findAllByDeleted(false);
    }

    public List<TablaDescripcionMatrizOportunidad> findTablaNivel1(Long id){
        return tablaDescripcionMatrizOportunidadRepository.findNivel1(id);
    }

    public List<TablaDescripcionMatrizOportunidad> findTablaNivel2(Long id, int id2){
        return tablaDescripcionMatrizOportunidadRepository.findNivel2(id, id2);
    }

    public ResponseEntity<TablaDescripcionMatrizOportunidadGetDTO> updateById (Long id, TablaDescripcionMatrizOportunidadPutDTO data){
        HttpHeaders responseHeaders = new HttpHeaders();
        TablaDescripcionMatrizOportunidad tablaDescripcionMatrizO = tablaDescripcionMatrizOportunidadRepository.findById(id).orElseThrow(()-> new DBException("Tabla Descripcion", id));
        TablaDescripcionMatrizOportunidadGetDTO tablaDescripcionMatrizOGetDTO = new TablaDescripcionMatrizOportunidadGetDTO();
        try{
            BeanUtils.copyProperties(data, tablaDescripcionMatrizO);
            tablaDescripcionMatrizOportunidadRepository.save(tablaDescripcionMatrizO);
            BeanUtils.copyProperties(tablaDescripcionMatrizO, tablaDescripcionMatrizOGetDTO);
        } catch (Exception e){
            return ResponseEntity.badRequest().headers(responseHeaders).body(null);
        }
        return ResponseEntity.ok().headers(responseHeaders).body(tablaDescripcionMatrizOGetDTO);
    }

    public TablaDescripcionMatrizOportunidadGetDTO findTablaDescripcionByID(Long id){
        Optional<TablaDescripcionMatrizOportunidad> tablaDescripcionMatrizO = tablaDescripcionMatrizOportunidadRepository.findById(id);
        TablaDescripcionMatrizOportunidadGetDTO tablaDescripcionMatrizOGetDTO = new TablaDescripcionMatrizOportunidadGetDTO();
        BeanUtils.copyProperties(tablaDescripcionMatrizO.get(), tablaDescripcionMatrizOGetDTO);
        return tablaDescripcionMatrizOGetDTO;
    }

    public TablaDescripcionMatrizOportunidadGetDTO2 findTablaDescripcionByID2(Long id){

        Optional<TablaDescripcionMatrizOportunidad> tablaDescripcionMatrizOportunidad = tablaDescripcionMatrizOportunidadRepository.findById(id);
        TablaDescripcionMatrizOportunidadGetDTO tablaDescripcionMatrizOportunidadGetDTO = new TablaDescripcionMatrizOportunidadGetDTO();
        BeanUtils.copyProperties(tablaDescripcionMatrizOportunidad.get(), tablaDescripcionMatrizOportunidadGetDTO);

        TablaDescripcionMatrizOportunidadGetDTO2  objeto = new TablaDescripcionMatrizOportunidadGetDTO2();

        int nivel2 = tablaDescripcionMatrizOportunidadGetDTO.getNivel2Id();
        if(nivel2 != 0) {
            Optional<TablaDescripcionMatrizOportunidad> findNivel2 = tablaDescripcionMatrizOportunidadRepository.findById(new Long(nivel2));
            objeto.setNivel2Id(findNivel2.get());
        }
        BeanUtils.copyProperties(tablaDescripcionMatrizOportunidad.get(), objeto);
        return objeto;
    }

    public TablaDescripcionMatrizOportunidad findByIdTablaDesc(Long id){
        //try {
            //return repository.findById(id).orElseThrow(() -> new DBException("Rol", id));
            //TablaDescripcionMatrizOportunidad founded =
              return tablaDescripcionMatrizOportunidadRepository.findById(id).orElseThrow(() -> new DBException("Tabla oportunudad", id));
         //   return founded;
      //  }catch (Exception e){
          //  Log.error("Error : " , e);
        //}
       // return null;
    }


    public TablaDescripcionMatrizOportunidad deleteById(Long id) {
        TablaDescripcionMatrizOportunidad tablaDescripcionToDelete = tablaDescripcionMatrizOportunidadRepository.findByIdAndDeleted(id, false).get();
        return tablaDescripcionMatrizOportunidadRepository.save(delete(tablaDescripcionToDelete));
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



