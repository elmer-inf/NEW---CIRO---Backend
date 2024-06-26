package atc.riesgos.model.repository;

import atc.riesgos.model.entity.TablaListaSeguridad;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TablaListaSeguridadRepository extends BaseRepository<TablaListaSeguridad> {

    List<TablaListaSeguridad> findAllByDeleted(Boolean deleted);
}
