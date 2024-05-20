package atc.riesgos.model.repository;

import atc.riesgos.model.dto.MatrizRiesgo.MatrizRiesgoGetDTOPlanesParaEvento;
import atc.riesgos.model.entity.MatrizRiesgo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatrizRiesgoRepository extends BaseRepository<MatrizRiesgo> {

    List<MatrizRiesgo> findAllByDeleted(Boolean deleted);

    @Query(nativeQuery=true, value = "SELECT COUNT(*) FROM riesgos.tbl_matriz_riesgo WHERE rie_codigo LIKE 'RO-' || ?1 || '\\_%' ESCAPE '\\'")
    Integer countMatrizRiesgoCodigo(String sigla);

    @Query(nativeQuery=true, value = "SELECT MAX(rie_id_unidad_correlativo) FROM riesgos.tbl_matriz_riesgo WHERE rie_codigo LIKE 'RO-' || ?1 || '\\_%' ESCAPE '\\'")
    Integer findUltimoIdUnidad(String sigla);


    @Query(value="Select c from MatrizRiesgo c where c.deleted is false and c.id IN (?1) ")
    List<MatrizRiesgo> matrizInById(List<Long> in);

    @Query("SELECT new atc.riesgos.model.dto.MatrizRiesgo.MatrizRiesgoGetDTOPlanesParaEvento(e.id, e.codigo, e.areaId, e.planesAccion) " +
            "FROM MatrizRiesgo e where e.id IN :filter")
    List<MatrizRiesgoGetDTOPlanesParaEvento> riesgosByIds(@Param("filter") List<Long> filter);

    // NOTIFICACIONES PARA PLANES VENCIDOS
    @Query(value = "SELECT r.rie_id AS id, r.rie_codigo AS codigo, " +
            "rie_planes_string->>'descripcion' AS descripcion, " +
            "TO_CHAR(CAST(rie_planes_string->>'fechaImpl' AS DATE), 'DD/MM/YYYY') AS fechaImpl, " +
            "rie_planes_string->>'estado' AS estado " +
            "FROM riesgos.tbl_matriz_riesgo r " +
            "CROSS JOIN LATERAL jsonb_array_elements(CAST(r.rie_planes_accion AS jsonb)) AS rie_planes_string " +
            "WHERE rie_planes_string->>'fechaImpl' != '' " +
            "AND CAST(rie_planes_string->>'fechaImpl' AS DATE) <= CURRENT_DATE " +
            "AND CAST(rie_planes_string->>'fechaImpl' AS DATE) > CURRENT_DATE - INTERVAL '5 days' " +
            "AND rie_planes_string->>'estado' != 'Concluido'", nativeQuery = true)
    List<Object[]> planesAVencer5Dias();

    @Query(value = "SELECT r.rie_id AS id, r.rie_codigo AS codigo, " +
            "rie_planes_string->>'descripcion' AS descripcion, " +
            "TO_CHAR(CAST(rie_planes_string->>'fechaImpl' AS DATE), 'DD/MM/YYYY') AS fechaImpl, " +
            "rie_planes_string->>'estado' AS estado " +
            "FROM riesgos.tbl_matriz_riesgo r " +
            "CROSS JOIN LATERAL jsonb_array_elements(CAST(r.rie_planes_accion AS jsonb)) AS rie_planes_string " +
            "WHERE rie_planes_string->>'fechaImpl' != '' " +
            "AND CAST(rie_planes_string->>'fechaImpl' AS DATE) <= CURRENT_DATE " +
            "AND NOT (CAST(rie_planes_string->>'fechaImpl' AS DATE) > CURRENT_DATE - INTERVAL '5 days') " +
            "AND CAST(rie_planes_string->>'fechaImpl' AS DATE) > CURRENT_DATE - INTERVAL '10 days' " +
            "AND rie_planes_string->>'estado' != 'Concluido'", nativeQuery = true)
    List<Object[]> planesAVencer10Dias();


    @Query(value = "SELECT r.rie_id AS id, " +
            "r.rie_codigo AS codigo, " +
            "rie_planes_string->>'descripcion' AS descripcion, " +
            "TO_CHAR(CAST(rie_planes_string->>'fechaImpl' AS DATE), 'DD/MM/YYYY') AS fechaImpl, " +
            "rie_planes_string->>'estado' AS estado  " +
            "FROM riesgos.tbl_matriz_riesgo r " +
            "CROSS JOIN LATERAL jsonb_array_elements(CAST(r.rie_planes_accion AS jsonb)) AS rie_planes_string " +
            "WHERE rie_planes_string->>'fechaImpl' != '' " +
            "AND CAST(rie_planes_string->>'fechaImpl' AS DATE) <= CURRENT_DATE " +
            "AND rie_planes_string->>'estado' != 'Concluido'", nativeQuery = true)
    List<Object[]> planesVencidos();



}