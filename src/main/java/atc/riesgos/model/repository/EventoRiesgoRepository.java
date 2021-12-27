package atc.riesgos.model.repository;

import atc.riesgos.model.entity.EventoRiesgo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventoRiesgoRepository extends BaseRepository<EventoRiesgo> {

    List<EventoRiesgo> findAllByDeleted(Boolean deleted);

    @Query(value = "SELECT COUNT(*) FROM EventoRiesgo e WHERE e.codigo LIKE ?1%")
    Integer countEventoCodigo(String sigla);

    @Query(value = "SELECT MAX(e.idAreaCorrelativo) FROM EventoRiesgo e WHERE e.codigo LIKE ?1%")
    Integer findUltimoIdArea(String sigla);
}