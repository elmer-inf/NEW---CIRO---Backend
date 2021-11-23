package ciro.atc.model.repository;

import ciro.atc.model.entity.TablaDescripcion;
import ciro.atc.model.entity.TablaDescripcionMatrizRiesgo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TablaDescripcionMatrizRiesgoRepository extends BaseRepository<TablaDescripcionMatrizRiesgo> {
    List<TablaDescripcionMatrizRiesgo> findAllByDeleted(Boolean deleted);

    @Query(value = "Select td from TablaDescripcionMatrizRiesgo td Where tablaId.id = ?1 and deleted = false")
    List<TablaDescripcionMatrizRiesgo> findNivel1(Long id);

}

