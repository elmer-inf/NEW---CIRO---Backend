package ciro.atc.model.repository;

import ciro.atc.model.entity.TablaDescripcion;
import ciro.atc.model.entity.TablaDescripcionMatrizOportunidad;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TablaDescripcionMatrizOportunidadRepository extends BaseRepository<TablaDescripcionMatrizOportunidad> {
    List<TablaDescripcionMatrizOportunidad> findAllByDeleted(Boolean deleted);

    @Query(value = "Select td from TablaDescripcionMatrizOportunidad td Where tablaId.id = ?1 and deleted = false")
    List<TablaDescripcionMatrizOportunidad> findNivel1(Long id);

    @Query(value = "Select td from TablaDescripcionMatrizOportunidad td Where tablaId.id = ?1 and nivel2Id = ?2 and deleted = false")
    List<TablaDescripcionMatrizOportunidad> findNivel2(Long id, int id2);

}

