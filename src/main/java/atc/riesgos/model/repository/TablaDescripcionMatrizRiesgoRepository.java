package atc.riesgos.model.repository;

import atc.riesgos.model.entity.TablaDescripcion;
import atc.riesgos.model.entity.TablaDescripcionMatrizRiesgo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TablaDescripcionMatrizRiesgoRepository extends BaseRepository<TablaDescripcionMatrizRiesgo> {
    List<TablaDescripcionMatrizRiesgo> findAllByDeleted(Boolean deleted);

    @Query(value = "Select td from TablaDescripcionMatrizRiesgo td Where tablaId.id = ?1 and deleted = false")
    List<TablaDescripcionMatrizRiesgo> findNivel1(Long id);

    @Query(value = "Select td from TablaDescripcionMatrizRiesgo td Where tablaId.id = ?1 and nivel2_id = ?2 and deleted = false")
    List<TablaDescripcionMatrizRiesgo> findNivel2(Long id, int id2);

}

