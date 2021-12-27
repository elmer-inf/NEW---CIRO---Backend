package atc.riesgos.model.repository;

import atc.riesgos.model.entity.Observacion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObservacionRepository extends BaseRepository<Observacion>{

    List<Observacion> findAllByDeleted(Boolean deleted);

    @Query(value = "SELECT COUNT(*) FROM Observacion o WHERE o.eventoId.id = ?1")
    Integer countObservacionEvento(Long id);
    //List<Observacion> countObservacionEvento(Long id);

    @Query(value = "SELECT MAX(o.id) FROM Observacion o WHERE o.eventoId.id = ?1")
    Long ultimaObservacionEvento(Long id);

    @Query(value = "SELECT COUNT(*) FROM Observacion o WHERE o.matrizRiesgoId.id = ?1")
    Integer countObservacionRiesgo(Long id);

    @Query(value = "SELECT MAX(o.id) FROM Observacion o WHERE o.matrizRiesgoId.id = ?1")
    Long ultimaObservacionRiesgo(Long id);

    @Query(value = "SELECT COUNT(*) FROM Observacion o WHERE o.matrizOportunidadId.id = ?1")
    Integer countObservacionOportunidad(Long id);

    @Query(value = "SELECT MAX(o.id) FROM Observacion o WHERE o.matrizOportunidadId.id = ?1")
    Long ultimaObservacionOportunidad(Long id);
}
