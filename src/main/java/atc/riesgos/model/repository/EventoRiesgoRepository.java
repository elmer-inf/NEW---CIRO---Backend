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

    @Query(value = "SELECT MAX(e.fechaDescAux) FROM EventoRiesgo e WHERE e.codigo LIKE ?1%")
    Integer findUltimoAnioDesc(String sigla);

    @Query(nativeQuery=true, value = "select MAx(x.eve_id_area_correlativo)\n" +
            "from (\n" +
            "select eve_id, eve_codigo, eve_area_id, eve_id_area_correlativo, eve_fecha_desc, eve_fecha_desc_aux,eve_estado_registro\n" +
            "from riesgos.tbl_evento_riesgo e\n" +
            "WHERE e.eve_codigo LIKE ?1% and  e.eve_fecha_desc_aux = (SELECT MAX(eve_fecha_desc_aux)  eve_fecha_desc_aux \n" +
            "FROM riesgos.tbl_evento_riesgo ter \n" +
            "WHERE eve_codigo LIKE ?1%)\n" +
            ") x")
    Integer findUltimoIdArea(String sigla);

    /*@Query(value = "SELECT MAX(e.idAreaCorrelativo) FROM EventoRiesgo e WHERE e.codigo LIKE ?1% and e.fechaDescAux = ?2")
    Integer findUltimoIdArea(String sigla, int ultimoAnio);*/

    /*@Query(value = "SELECT MAX(e.idAreaCorrelativo) FROM EventoRiesgo e WHERE e.codigo LIKE ?1% and e.fechaDescAux = ?2")
    Integer findUltimoIdAreaYanio(String sigla, int ultimoAnio);*/

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