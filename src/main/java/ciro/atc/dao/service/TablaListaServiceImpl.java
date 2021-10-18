package ciro.atc.dao.service;

import ciro.atc.config.log.Log;
import ciro.atc.exception.DBException;
import ciro.atc.model.dto.TablaLista.TablaListaGetDTO;
import ciro.atc.model.dto.TablaLista.TablaListaPostDTO;
import ciro.atc.model.dto.TablaLista.TablaListaPutDTO;
import ciro.atc.model.entity.TablaDescripcion;
import ciro.atc.model.entity.TablaLista;
import ciro.atc.model.repository.TablaListaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@Service
public class TablaListaServiceImpl implements TablaListaService {

    @Autowired
    TablaListaRepository tablaListaRepository;

    public TablaLista create (TablaListaPostDTO data){
        TablaLista tablaLista = new TablaLista();
        BeanUtils.copyProperties(data, tablaLista);
        return tablaListaRepository.save(tablaLista);
    }

    public TablaLista findByIdTabla(Long id){
        Optional<TablaLista> founded = tablaListaRepository.findById(id);
        return founded.get();
    }

    public List<TablaLista> listTabla(){
        return tablaListaRepository.findAllByDeleted(false);
        //return tablaListaRepository.findAll();
    }


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
    }

}



