package atc.riesgos.model.repository;

import atc.riesgos.model.entity.EventoRiesgo;
import atc.riesgos.model.entity.MatrizRiesgo;
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

    @Query(value = "SELECT e " +
                    "FROM EventoRiesgo e " +
                    "WHERE e.fechaFin is not null and e.horaFin is not null and " +
                            "e.fechaFinPlan is not null and " +
                            "e.fechaFinPlan - current_date() <= 10 and " +
                            "e.fechaFinPlan - current_date() >5")
    List<EventoRiesgo> diezDiasAntes();

    @Query(value = "SELECT e " +
                    "FROM EventoRiesgo e " +
                    "WHERE e.fechaFin is not null and e.horaFin is not null and " +
                            "e.fechaFinPlan is not null and " +
                            "e.fechaFinPlan - current_date() <= 5 and " +
                            "e.fechaFinPlan - current_date() > 0")
    List<EventoRiesgo> cincoDiasAntes();

    @Query(value = "SELECT e " +
                    "FROM EventoRiesgo e " +
                    "WHERE e.fechaFin is not null and e.horaFin is not null and " +
                            "e.fechaFinPlan is not null and " +
                            "current_date() > e.fechaFinPlan")
    List<EventoRiesgo> planVencido();
}