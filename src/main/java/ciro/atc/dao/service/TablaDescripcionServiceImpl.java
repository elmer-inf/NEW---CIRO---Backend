package ciro.atc.dao.service;

import ciro.atc.config.log.Log;
import ciro.atc.exception.DBException;
import ciro.atc.model.dto.TablaDescripcion.TablaDescripcionGetDTO;
import ciro.atc.model.dto.TablaDescripcion.TablaDescripcionPostDTO;
import ciro.atc.model.dto.TablaDescripcion.TablaDescripcionPutDTO;
import ciro.atc.model.dto.TablaLista.TablaListaGetDTO;
import ciro.atc.model.dto.TablaLista.TablaListaPutDTO;
import ciro.atc.model.entity.TablaDescripcion;
import ciro.atc.model.entity.TablaLista;
import ciro.atc.model.repository.TablaDescripcionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@Service
public class TablaDescripcionServiceImpl implements TablaDescripcionService {

    @Autowired
    TablaDescripcionRepository tablaDescripcionRepository;
    @Autowired
    TablaListaService tablaListaService;

    public TablaDescripcion create(TablaDescripcionPostDTO data) {
        TablaDescripcion tablaDescripcion = new TablaDescripcion();
        TablaLista tablaLista = tablaListaService.findByIdTabla(data.getTablaLista());
        BeanUtils.copyProperties(data, tablaDescripcion);
        tablaDescripcion.setTablaLista(tablaLista);

        return tablaDescripcionRepository.save(tablaDescripcion);
    }

    public List<TablaDescripcion> listTablaDescripcion(){
        return tablaDescripcionRepository.findAllByDeleted(false);
        //return tablaDescripcionRepository.findAll();
    }

    public List<TablaDescripcion> findTablaNivel1(Long id){
        return tablaDescripcionRepository.findNivel1(id);
    }

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
}



