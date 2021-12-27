package atc.riesgos.model.repository;

import atc.riesgos.model.entity.TablaLista;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TablaListaRepository extends BaseRepository<TablaLista> {

    List<TablaLista> findAllByDeleted(Boolean deleted);
}
