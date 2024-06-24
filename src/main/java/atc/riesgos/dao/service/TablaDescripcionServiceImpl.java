package atc.riesgos.dao.service;

//import atc.riesgos.config.log.Log;
import atc.riesgos.exception.DBException;
import atc.riesgos.model.dto.TablaDescripcion.TablaDescripcionGetDTO;
import atc.riesgos.model.dto.TablaDescripcion.TablaDescripcionGetDTO2;
import atc.riesgos.model.dto.TablaDescripcion.TablaDescripcionPostDTO;
import atc.riesgos.model.dto.TablaDescripcion.TablaDescripcionPutDTO;
import atc.riesgos.model.entity.MatrizRiesgo;
import atc.riesgos.model.entity.TablaDescripcion;
import atc.riesgos.model.entity.TablaLista;
import atc.riesgos.model.repository.TablaDescripcionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
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
            //Log.log("Tabla Descripcion actualizada =>", e);
            System.out.println("Tabla Descripcion actualizada: "+ e);
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

    public TablaDescripcion findByIdTablaDesc(Long id){
        //System.out.println("ID: " + id);
        try {
            if(id!=null && id!=0){
                Optional<TablaDescripcion> founded = tablaDescripcionRepository.findById(id);
                //System.out.printf("ENCONTRADO :: " + Log.toJSON(founded.get()));
                return founded.get();
            }

        }catch (Exception e){
            System.out.println("Error: " + e);
        }
        return null;
    }

    public List<TablaDescripcion> getListDescripcionInId(List<Long> in) {
        try{
            return tablaDescripcionRepository.descripcionInById(in);
        }catch (Exception e){
            System.out.println("Error: " + e);
        }

        return new ArrayList<>();
    }

}



