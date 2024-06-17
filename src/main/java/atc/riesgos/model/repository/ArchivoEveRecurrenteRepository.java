package atc.riesgos.model.repository;

import atc.riesgos.model.entity.Archivo;
import atc.riesgos.model.entity.ArchivoEveRecurrente;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArchivoEveRecurrenteRepository extends BaseRepository<ArchivoEveRecurrente> {

    List<ArchivoEveRecurrente> findAllByDeleted(Boolean deleted);
    List<ArchivoEveRecurrente> findByEventoId(Long idEvento);

}