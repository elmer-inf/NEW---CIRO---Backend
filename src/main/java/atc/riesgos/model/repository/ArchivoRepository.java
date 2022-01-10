package atc.riesgos.model.repository;

import atc.riesgos.model.entity.Archivo;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ArchivoRepository extends BaseRepository<Archivo> {

    List<Archivo> findAllByDeleted(Boolean deleted);
    List<Archivo> findByEventoId(Long idEvento);

}