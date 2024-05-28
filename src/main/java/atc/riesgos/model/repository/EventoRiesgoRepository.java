package atc.riesgos.model.repository;

import atc.riesgos.model.dto.report.ciro.eventos.FiltroReporteConfigEvento;
import atc.riesgos.model.dto.report.ciro.eventos.ReporteEventoGralDTO;
import atc.riesgos.model.entity.EventoRiesgo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

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


    // REPORTES DE EVENTOS DE RIESGO

    @Query("SELECT new atc.riesgos.model.dto.report.ciro.eventos.ReporteEventoGralDTO(e.codigo, e.descripcion, e.estadoEvento, e.fechaDesc, e.fechaFin) " +
            "FROM EventoRiesgo e " +
            "WHERE e.fechaDesc >= :fechaDesde AND e.fechaDesc <= :fechaHasta AND e.estadoEvento in :estadoEvento " +
            "ORDER BY eve_id ASC")
    List<ReporteEventoGralDTO> getReporteGralEvento(@Param("fechaDesde") Date fechaDesde, @Param("fechaHasta") Date fechaHasta, @Param("estadoEvento") List<String> estadoEventos);


    @Query("SELECT e " +
            "FROM EventoRiesgo e " +
            "WHERE e.fechaDesc >= :fechaDesde AND e.fechaDesc <= :fechaHasta " +
            "ORDER BY eve_id ASC")
    List<EventoRiesgo> getReporteAuditoriaExterna(@Param("fechaDesde") Date fechaDesde, @Param("fechaHasta") Date fechaHasta);


    // Obtiene descripcion de Tipo de Evento
    @Query("SELECT t.nombre FROM TablaDescripcion t WHERE t.tablaLista = 6 AND t.clave = :tipo")
    String getDescripcionTipoEvento(@Param("tipo") String tipo);






}