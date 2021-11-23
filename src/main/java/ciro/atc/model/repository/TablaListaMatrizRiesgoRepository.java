package ciro.atc.model.repository;

import ciro.atc.model.entity.TablaListaMatrizRiesgo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TablaListaMatrizRiesgoRepository extends BaseRepository<TablaListaMatrizRiesgo> {

    List<TablaListaMatrizRiesgo> findAllByDeleted(Boolean deleted);
}
