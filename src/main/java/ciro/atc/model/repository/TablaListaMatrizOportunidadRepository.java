package ciro.atc.model.repository;

import ciro.atc.model.entity.TablaListaMatrizOportunidad;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TablaListaMatrizOportunidadRepository extends BaseRepository<TablaListaMatrizOportunidad> {

    List<TablaListaMatrizOportunidad> findAllByDeleted(Boolean deleted);
}
