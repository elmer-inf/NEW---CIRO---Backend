package atc.riesgos.model.repository;

import atc.riesgos.model.entity.Seguridad;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeguridadRepository extends BaseRepository<Seguridad> {

    List<Seguridad> findAllByDeleted(Boolean deleted);

    @Query(value="SELECT count(*) FROM Seguridad s GROUP BY areaId ")
    List<Seguridad> groupByArea();
}