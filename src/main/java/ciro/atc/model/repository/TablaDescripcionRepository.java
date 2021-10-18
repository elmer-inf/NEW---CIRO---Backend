package ciro.atc.model.repository;

import ciro.atc.model.entity.TablaDescripcion;
import ciro.atc.model.entity.TablaLista;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TablaDescripcionRepository extends BaseRepository<TablaDescripcion> {
    List<TablaDescripcion> findAllByDeleted(Boolean deleted);

    @Query(value = "Select td from TablaDescripcion td Where tablaLista.id = ?1 and deleted = false")
    List<TablaDescripcion> findNivel1(Long id);

    @Query(value = "Select td from TablaDescripcion td Where tablaLista.id = ?1 and nivel2_id = ?2 and deleted = false")
    List<TablaDescripcion> findNivel2(Long id, int id2);

    @Query(value = "Select td from TablaDescripcion td Where tablaLista.id = ?1 and nivel2_id = ?2 and nivel3_id = ?3 and deleted = false")
    List<TablaDescripcion> findNivel3(Long id, int id2, int id3);




}

