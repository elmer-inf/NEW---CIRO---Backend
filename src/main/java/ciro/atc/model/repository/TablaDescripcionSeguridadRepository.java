package ciro.atc.model.repository;

import ciro.atc.model.entity.TablaDescripcionSeguridad;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TablaDescripcionSeguridadRepository extends BaseRepository<TablaDescripcionSeguridad> {
    List<TablaDescripcionSeguridad> findAllByDeleted(Boolean deleted);

    @Query(value = "Select td from TablaDescripcionSeguridad td Where tablaId.id = ?1 and deleted = false")
    List<TablaDescripcionSeguridad> findNivel1(Long id);

}

