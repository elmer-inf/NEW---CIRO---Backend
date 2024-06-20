package atc.riesgos.model.repository;

import atc.riesgos.model.dto.MatrizRiesgo.MatrizRiesgoGetDTOPlanesParaEvento;
import atc.riesgos.model.entity.MatrizRiesgo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;
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
    @Query(value = "SELECT r.rie_id AS idRiesgo, " +
            "CAST(rie_planes_string->>'nroPlan' AS INT) AS nroPlan,  " +
            "r.rie_codigo AS codigo, " +
            "rie_planes_string->>'descripcion' AS descripcion, " +
            "TO_CHAR(CAST(rie_planes_string->>'fechaImpl' AS DATE), 'DD/MM/YYYY') AS fechaImpl, " +
            "rie_planes_string->>'estado' AS estado, " +
            "rie_planes_string->>'cargo' AS cargo, " +
            "rie_planes_string->>'informadoPorCorreo' AS informadoPorCorreo, " +
            "rie_planes_string->>'correoCargo' AS correoCargo " +
            "FROM riesgos.tbl_matriz_riesgo r " +
            "CROSS JOIN LATERAL jsonb_array_elements(CAST(r.rie_planes_accion AS jsonb)) AS rie_planes_string " +
            "WHERE rie_planes_string->>'fechaImpl' != '' " +
            "AND CAST(rie_planes_string->>'fechaImpl' AS DATE) >= CURRENT_DATE " +
            "AND CAST(rie_planes_string->>'fechaImpl' AS DATE) < CURRENT_DATE + INTERVAL '5 days' " +
            "AND rie_planes_string->>'estado' NOT IN ('Concluido', 'No aplicable')", nativeQuery = true)
    List<Object[]> planesAVencer5Dias();

    @Query(value = "SELECT r.rie_id AS idRiesgo, " +
            "CAST(rie_planes_string->>'nroPlan' AS INT) AS nroPlan,  " +
            "r.rie_codigo AS codigo, " +
            "rie_planes_string->>'descripcion' AS descripcion, " +
            "TO_CHAR(CAST(rie_planes_string->>'fechaImpl' AS DATE), 'DD/MM/YYYY') AS fechaImpl, " +
            "rie_planes_string->>'estado' AS estado, " +
            "rie_planes_string->>'cargo' AS cargo, " +
            "rie_planes_string->>'informadoPorCorreo' AS informadoPorCorreo, " +
            "rie_planes_string->>'correoCargo' AS correoCargo " +
            "FROM riesgos.tbl_matriz_riesgo r " +
            "CROSS JOIN LATERAL jsonb_array_elements(CAST(r.rie_planes_accion AS jsonb)) AS rie_planes_string " +
            "WHERE rie_planes_string->>'fechaImpl' != '' " +
            "AND CAST(rie_planes_string->>'fechaImpl' AS DATE) >= CURRENT_DATE " +
            "AND NOT (CAST(rie_planes_string->>'fechaImpl' AS DATE) < CURRENT_DATE + INTERVAL '5 days') " +
            "AND CAST(rie_planes_string->>'fechaImpl' AS DATE) < CURRENT_DATE + INTERVAL '10 days' " +
            "AND rie_planes_string->>'estado' NOT IN ('Concluido','No aplicable')", nativeQuery = true)
    List<Object[]> planesAVencer10Dias();


    @Query(value = "SELECT r.rie_id AS idRiesgo, " +
            "CAST(rie_planes_string->>'nroPlan' AS INT) AS nroPlan,  " +
            "r.rie_codigo AS codigo, " +
            "rie_planes_string->>'descripcion' AS descripcion, " +
            "TO_CHAR(CAST(rie_planes_string->>'fechaImpl' AS DATE), 'DD/MM/YYYY') AS fechaImpl, " +
            "rie_planes_string->>'estado' AS estado, " +
            "rie_planes_string->>'cargo' AS cargo, " +
            "rie_planes_string->>'informadoPorCorreo' AS informadoPorCorreo, " +
            "rie_planes_string->>'correoCargo' AS correoCargo " +
            "FROM riesgos.tbl_matriz_riesgo r " +
            "CROSS JOIN LATERAL jsonb_array_elements(CAST(r.rie_planes_accion AS jsonb)) AS rie_planes_string " +
            "WHERE rie_planes_string->>'fechaImpl' != '' " +
            "AND CAST(rie_planes_string->>'fechaImpl' AS DATE) < CURRENT_DATE " +
            "AND rie_planes_string->>'estado' like 'Vencido'", nativeQuery = true)
    List<Object[]> planesVencidos();


    @Modifying
    @Query(value = "UPDATE riesgos.tbl_matriz_riesgo " +
            "SET rie_planes_accion = ( " +
            "    SELECT CAST(jsonb_agg( " +
            "        CASE " +
            "            WHEN CAST(element ->> 'nroPlan' AS INT) = :nroPlan THEN jsonb_set(element, '{informadoPorCorreo}', '\"SI\"') " +
            "            ELSE element " +
            "        END " +
            "    ) AS TEXT) " +
            "    FROM ( " +
            "        SELECT jsonb_array_elements(CAST(rie_planes_accion AS jsonb)) AS element " +
            "        FROM riesgos.tbl_matriz_riesgo " +
            "        WHERE rie_id = :idRiesgo " +
            "    ) AS elements " +
            ") " +
            "WHERE rie_id = :idRiesgo", nativeQuery = true)
    int updatePlanRiesgoInformado(@Param("idRiesgo") Long idRiesgo, @Param("nroPlan") int nroPlan);

    @Query(value = "SELECT des_descripcion " +
            "FROM riesgos.tbl_tabla_descripcion ttd " +
            "WHERE ttd.des_tabla_id = 7 " +
            "AND regexp_replace(ttd.des_nombre, '\\s+', ' ', 'g') ILIKE regexp_replace(:nombre, '\\s+', ' ', 'g') " +
            "ORDER BY ttd.des_nombre ASC " +
            "LIMIT 1", // Asumimos que solo quieres un resultado
            nativeQuery = true)
    String getCorreoByCargo(@Param("nombre") String nombre);

    @Modifying
    @Query(value = "INSERT INTO riesgos.control_recurrencias_riesgo (rie_id, rie_codigo, eve_id1, eve_codigo1, eve_id2, eve_codigo2, eve_id3, eve_codigo3)\n" +
            "SELECT \n" +
            "    id_matriz_riesgo AS rie_id,\n" +
            "    rie_codigo AS rie_codigo,\n" +
            "    MAX(CASE WHEN rn_mod = 1 THEN id_evento_riesgo END) AS eve_id1,\n" +
            "    MAX(CASE WHEN rn_mod = 1 THEN eve_codigo END) AS eve_codigo1,\n" +
            "    MAX(CASE WHEN rn_mod = 2 THEN id_evento_riesgo END) AS eve_id2,\n" +
            "    MAX(CASE WHEN rn_mod = 2 THEN eve_codigo END) AS eve_codigo2,\n" +
            "    MAX(CASE WHEN rn_mod = 3 THEN id_evento_riesgo END) AS eve_id3,\n" +
            "    MAX(CASE WHEN rn_mod = 3 THEN eve_codigo END) AS eve_codigo3\n" +
            "FROM (\n" +
            "    SELECT\n" +
            "        id_matriz_riesgo,\n" +
            "        rie_codigo,\n" +
            "        id_evento_riesgo,\n" +
            "        eve_codigo,\n" +
            "        ROW_NUMBER() OVER (PARTITION BY id_matriz_riesgo, grupo ORDER BY id_evento_riesgo ASC) AS rn_mod,\n" +
            "        grupo\n" +
            "    FROM (\n" +
            "        SELECT\n" +
            "            Numerados.id_matriz_riesgo,\n" +
            "            Numerados.rie_codigo,\n" +
            "            Numerados.id_evento_riesgo,\n" +
            "            Numerados.eve_codigo,\n" +
            "            CEIL(rn / 3.0) AS grupo\n" +
            "        FROM (\n" +
            "            SELECT \n" +
            "                er.id_matriz_riesgo,\n" +
            "                mr.rie_codigo,\n" +
            "                er.id_evento_riesgo,\n" +
            "                ev.eve_codigo,\n" +
            "                ROW_NUMBER() OVER (PARTITION BY er.id_matriz_riesgo ORDER BY er.id_evento_riesgo ASC) AS rn\n" +
            "            FROM riesgos.eventoriesgo_matriz er\n" +
            "            JOIN riesgos.tbl_evento_riesgo ev ON er.id_evento_riesgo = ev.eve_id\n" +
            "            JOIN riesgos.tbl_matriz_riesgo mr ON er.id_matriz_riesgo = mr.rie_id\n" +
            "            WHERE ev.eve_fecha_desc >= '2024-01-01'\n" +
            "        ) AS Numerados\n" +
            "    ) AS GruposDeTres\n" +
            ") AS DatosAgrupados\n" +
            "GROUP BY id_matriz_riesgo, rie_codigo, grupo\n" +
            "HAVING COUNT(*) = 3\n" +
            "AND NOT EXISTS (\n" +
            "    SELECT 1\n" +
            "    FROM riesgos.control_recurrencias_riesgo ne\n" +
            "    WHERE ne.rie_id = DatosAgrupados.id_matriz_riesgo\n" +
            "      AND ne.eve_id1 = MAX(CASE WHEN rn_mod = 1 THEN id_evento_riesgo END)\n" +
            "      AND ne.eve_id2 = MAX(CASE WHEN rn_mod = 2 THEN id_evento_riesgo END)\n" +
            "      AND ne.eve_id3 = MAX(CASE WHEN rn_mod = 3 THEN id_evento_riesgo END)\n" +
            ")", nativeQuery = true)
    void insertaVerificaRecurrenciasRiesgo();

    @Query(value="SELECT * FROM riesgos.control_recurrencias_riesgo \n" +
                 "WHERE correo_enviado=FALSE ORDER BY id ASC", nativeQuery = true)
    List<Object[]> getRecurrenciasRiesgo();

    @Modifying
    @Query(value = "UPDATE riesgos.control_recurrencias_riesgo \n" +
                   "SET correo_enviado=TRUE, fecha_envio=CURRENT_TIMESTAMP WHERE id=:idRecurrencia ", nativeQuery = true)
    int updateRecurrenciaRiesgoInformado(@Param("idRecurrencia") Long idRecurrencia);

    // Mapas de calor
    @Query(value="SELECT\n" +
            "    CAST(ROW_NUMBER() OVER (\n" +
            "        ORDER BY \n" +
            "            CASE \n" +
            "                WHEN SUBSTRING(mac.des_clave FROM '^[A-Z]+') = 'PO' THEN 1\n" +
            "                WHEN SUBSTRING(mac.des_clave FROM '^[A-Z]+') = 'PA' THEN 2\n" +
            "                WHEN SUBSTRING(mac.des_clave FROM '^[A-Z]+') = 'PE' THEN 3\n" +
            "                ELSE 4\n" +
            "            END,\n" +
            "            CAST(SUBSTRING(mac.des_clave FROM '[0-9]+$') AS INTEGER)\n" +
            "    ) AS int) AS nro,\n" +
            "    mac.des_clave AS codigo,\n" +
            "    mac.des_nombre AS macroproceso,\n" +
            "    CAST(COUNT(r.rie_proceso_id) AS int) AS cantidad,\n" +
            "    COALESCE(\n" +
            "        (\n" +
            "            SELECT des_campo_c\n" +
            "            FROM riesgos.tbl_tabla_descripcion_matriz_riesgo\n" +
            "            WHERE des_tabla_id = 2 \n" +
            "            AND des_campo_a = CAST(ROUND(AVG(CAST(prob.des_campo_a AS int))) AS varchar)\n" +
            "            LIMIT 1\n" +
            "        ), (SELECT des_campo_c FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_tabla_id =2 \n" +
            "            AND des_campo_a = (SELECT MIN(des_campo_a) FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_tabla_id =2))\n" +
            "    ) AS prob,\n" +
            "    COALESCE(CAST(ROUND(AVG(CAST(prob.des_campo_a AS int))) AS int), 1) AS valoración_probabilidad,\n" +
            "    COALESCE (\n" +
            "        (\n" +
            "            SELECT des_campo_e\n" +
            "            FROM riesgos.tbl_tabla_descripcion_matriz_riesgo\n" +
            "            WHERE des_tabla_id = 2 \n" +
            "            AND des_campo_a = CAST(ROUND(AVG(CAST(prob.des_campo_a AS int))) AS varchar)\n" +
            "            LIMIT 1\n" +
            "        ), (SELECT des_campo_e FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_tabla_id =2 \n" +
            "            AND des_campo_a = (SELECT MIN(des_campo_a) FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_tabla_id =2))\n" +
            "    ) AS factor_probabilidad,\n" +
            "    COALESCE (\n" +
            "        (\n" +
            "            SELECT des_nombre\n" +
            "            FROM riesgos.tbl_tabla_descripcion_matriz_riesgo\n" +
            "            WHERE des_tabla_id = 2 \n" +
            "            AND des_campo_a = CAST(ROUND(AVG(CAST(prob.des_campo_a AS int))) AS varchar)\n" +
            "            LIMIT 1\n" +
            "        ), (SELECT des_nombre FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_tabla_id =2 \n" +
            "            AND des_campo_a = (SELECT MIN(des_campo_a) FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_tabla_id =2))\n" +
            "    ) AS probabilidad,\n" +
            "    COALESCE(SUM(r.rie_impacto_usd), 0) AS impacto_por,\n" +
            "    COALESCE(CAST(ROUND(AVG(CAST(imp.des_campo_a AS int))) AS int), 1) AS impacto,\n" +
            "    COALESCE(\n" +
            "        (\n" +
            "            SELECT des_nombre\n" +
            "            FROM riesgos.tbl_tabla_descripcion_matriz_riesgo\n" +
            "            WHERE des_tabla_id = 3\n" +
            "            AND des_campo_a = CAST(ROUND(AVG(CAST(imp.des_campo_a AS int))) AS varchar)\n" +
            "            LIMIT 1\n" +
            "        ), (SELECT des_nombre FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_tabla_id =3 \n" +
            "            AND des_campo_a = (SELECT MIN(des_campo_a) FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_tabla_id =3))\n" +
            "    ) AS valoracion_impacto,\n" +
            "    COALESCE (\n" +
            "        (   \n" +
            "            SELECT des_campo_e\n" +
            "            FROM riesgos.tbl_tabla_descripcion_matriz_riesgo\n" +
            "            WHERE des_tabla_id = 2\n" +
            "            AND des_campo_a = CAST(ROUND(AVG(CAST(prob.des_campo_a AS int))) AS varchar)\n" +
            "            LIMIT 1\n" +
            "        ), (SELECT des_campo_e FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_tabla_id =2 \n" +
            "            AND des_campo_a = (SELECT MIN(des_campo_a) FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_tabla_id =2))\n" +
            "    ) * COALESCE(SUM(r.rie_impacto_usd), 0) AS monto_riesgo,\n" +
            "    riesgos.fc_calcula_valoracion_riesgo(\n" +
            "        CAST(ROUND(AVG(CAST(prob.des_campo_a AS int))) AS int),\n" +
            "        CAST(ROUND(AVG(CAST(imp.des_campo_a AS int))) AS int)\n" +
            "    ) AS valoracion_riesgo,\n" +
            "    (\n" +
            "        SELECT des_nombre\n" +
            "        FROM riesgos.tbl_tabla_descripcion_matriz_riesgo\n" +
            "        WHERE des_tabla_id = 3\n" +
            "        AND CAST(des_campo_a AS int) = riesgos.fc_calcula_valoracion_riesgo(\n" +
            "                            CAST(ROUND(AVG(CAST(prob.des_campo_a AS int))) AS int),\n" +
            "                            CAST(ROUND(AVG(CAST(imp.des_campo_a AS int))) AS int)\n" +
            "                        )\n" +
            "        LIMIT 1\n" +
            "    ) AS riesgo\n" +
            "FROM \n" +
            "    riesgos.tbl_tabla_descripcion mac\n" +
            "LEFT JOIN riesgos.tbl_matriz_riesgo r ON mac.des_id = r.rie_proceso_id\n" +
            "    AND r.rie_fecha_evaluacion >= :fechaDesde AND r.rie_fecha_evaluacion <= :fechaHasta\n" +
            "LEFT JOIN riesgos.tbl_tabla_descripcion_matriz_riesgo prob ON prob.des_id = r.rie_probabilidad_id\n" +
            "LEFT JOIN riesgos.tbl_tabla_descripcion_matriz_riesgo imp ON imp.des_id = r.rie_impacto_id\n" +
            "WHERE mac.des_delete = FALSE \n" +
            "    AND (r.rie_probabilidad_id IS NOT NULL OR r.rie_probabilidad_id IS NULL)\n" +
            "    AND (r.rie_impacto_id IS NOT NULL OR r.rie_impacto_id IS NULL)\n" +
            "    AND mac.des_tabla_id = 15\n" +
            "GROUP BY mac.des_clave, mac.des_nombre, r.rie_proceso_id\n" +
            "ORDER BY \n" +
            "    CASE \n" +
            "        WHEN SUBSTRING(mac.des_clave FROM '^[A-Z]+') = 'PO' THEN 1\n" +
            "        WHEN SUBSTRING(mac.des_clave FROM '^[A-Z]+') = 'PA' THEN 2\n" +
            "        WHEN SUBSTRING(mac.des_clave FROM '^[A-Z]+') = 'PE' THEN 3\n" +
            "        ELSE 4\n" +
            "    END,\n" +
            "    CAST(SUBSTRING(mac.des_clave FROM '[0-9]+$') AS INTEGER)", nativeQuery = true)
    List<Object[]> getValoracionExposicionInherente(@Param("fechaDesde") Date fechaDesde, @Param("fechaHasta") Date fechaHasta);


    @Query(value="SELECT\n" +
            "    CAST(ROW_NUMBER() OVER (\n" +
            "        ORDER BY \n" +
            "            CASE \n" +
            "                WHEN SUBSTRING(mac.des_clave FROM '^[A-Z]+') = 'PO' THEN 1\n" +
            "                WHEN SUBSTRING(mac.des_clave FROM '^[A-Z]+') = 'PA' THEN 2\n" +
            "                WHEN SUBSTRING(mac.des_clave FROM '^[A-Z]+') = 'PE' THEN 3\n" +
            "                ELSE 4\n" +
            "            END,\n" +
            "            CAST(SUBSTRING(mac.des_clave FROM '[0-9]+$') AS INTEGER)\n" +
            "    ) AS int) AS nro,\n" +
            "    mac.des_clave AS codigo,\n" +
            "    mac.des_nombre AS macroproceso,\n" +
            "    CAST(COUNT(r.rie_proceso_id) AS int) AS cantidad,\n" +
            "    COALESCE(\n" +
            "        (\n" +
            "            SELECT des_campo_c\n" +
            "            FROM riesgos.tbl_tabla_descripcion_matriz_riesgo\n" +
            "            WHERE des_tabla_id = 2 \n" +
            "            AND des_campo_a = CAST(ROUND(AVG(r.rie_probabilidad_residual)) AS varchar)\n" +
            "            LIMIT 1\n" +
            "        ), (SELECT des_campo_c FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_tabla_id =2 \n" +
            "            AND des_campo_a = (SELECT MIN(des_campo_a) FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_tabla_id =2))\n" +
            "    ) AS prob,\n" +
            "    COALESCE(CAST(ROUND(AVG(CAST(r.rie_probabilidad_residual AS int))) AS int), 1) AS valoración_probabilidad,\n" +
            "    COALESCE (\n" +
            "        (\n" +
            "            SELECT des_campo_e\n" +
            "            FROM riesgos.tbl_tabla_descripcion_matriz_riesgo\n" +
            "            WHERE des_tabla_id = 2 \n" +
            "            AND des_campo_a = CAST(ROUND(AVG(r.rie_probabilidad_residual)) AS varchar)\n" +
            "            LIMIT 1\n" +
            "        ), (SELECT des_campo_e FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_tabla_id =2 \n" +
            "            AND des_campo_a = (SELECT MIN(des_campo_a) FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_tabla_id =2))\n" +
            "    ) AS factor_probabilidad,\n" +
            "    COALESCE (\n" +
            "        (\n" +
            "            SELECT des_nombre\n" +
            "            FROM riesgos.tbl_tabla_descripcion_matriz_riesgo\n" +
            "            WHERE des_tabla_id = 2 \n" +
            "            AND des_campo_a = CAST(ROUND(AVG(r.rie_probabilidad_residual)) AS varchar)\n" +
            "            LIMIT 1\n" +
            "        ), (SELECT des_nombre FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_tabla_id =2 \n" +
            "            AND des_campo_a = (SELECT MIN(des_campo_a) FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_tabla_id =2))\n" +
            "    ) AS probabilidad,\n" +
            "    COALESCE(SUM(r.rie_impacto_usd), 0) AS impacto_por,\n" +
            "    COALESCE(CAST(ROUND(AVG(r.rie_impacto_residual)) AS int), 1) AS impacto,\n" +
            "    COALESCE(\n" +
            "        (\n" +
            "            SELECT des_nombre\n" +
            "            FROM riesgos.tbl_tabla_descripcion_matriz_riesgo\n" +
            "            WHERE des_tabla_id = 3\n" +
            "            AND des_campo_a = CAST(ROUND(AVG(r.rie_impacto_residual)) AS varchar)\n" +
            "            LIMIT 1\n" +
            "        ), (SELECT des_nombre FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_tabla_id =3 \n" +
            "            AND des_campo_a = (SELECT MIN(des_campo_a) FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_tabla_id =3))\n" +
            "    ) AS valoracion_impacto,\n" +
            "    COALESCE (\n" +
            "        (   \n" +
            "            SELECT des_campo_e\n" +
            "            FROM riesgos.tbl_tabla_descripcion_matriz_riesgo\n" +
            "            WHERE des_tabla_id = 2\n" +
            "            AND des_campo_a = CAST(ROUND(AVG(r.rie_probabilidad_residual)) AS varchar)\n" +
            "            LIMIT 1\n" +
            "        ), (SELECT des_campo_e FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_tabla_id =2 \n" +
            "            AND des_campo_a = (SELECT MIN(des_campo_a) FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_tabla_id =2))\n" +
            "    ) * COALESCE(SUM(r.rie_impacto_usd), 0) AS monto_riesgo,\n" +
            "    riesgos.fc_calcula_valoracion_riesgo(\n" +
            "        CAST(ROUND(AVG(r.rie_probabilidad_residual)) AS int),\n" +
            "        CAST(ROUND(AVG(r.rie_impacto_residual)) AS int)\n" +
            "    ) AS valoracion_riesgo,\n" +
            "    (\n" +
            "        SELECT des_nombre\n" +
            "        FROM riesgos.tbl_tabla_descripcion_matriz_riesgo\n" +
            "        WHERE des_tabla_id = 3\n" +
            "        AND CAST(des_campo_a AS int) = riesgos.fc_calcula_valoracion_riesgo(\n" +
            "                            CAST(ROUND(AVG(r.rie_probabilidad_residual)) AS int),\n" +
            "                            CAST(ROUND(AVG(r.rie_impacto_residual)) AS int)\n" +
            "                        )\n" +
            "        LIMIT 1\n" +
            "    ) AS riesgo\n" +
            "FROM \n" +
            "    riesgos.tbl_tabla_descripcion mac\n" +
            "LEFT JOIN riesgos.tbl_matriz_riesgo r ON mac.des_id = r.rie_proceso_id\n" +
            "    AND r.rie_fecha_evaluacion >= :fechaDesde AND r.rie_fecha_evaluacion <= :fechaHasta\n" +
            "WHERE mac.des_delete = FALSE \n" +
            "    AND (r.rie_probabilidad_id IS NOT NULL OR r.rie_probabilidad_id IS NULL)\n" +
            "    AND (r.rie_impacto_id IS NOT NULL OR r.rie_impacto_id IS NULL)\n" +
            "    AND mac.des_tabla_id = 15\n" +
            "GROUP BY mac.des_clave, mac.des_nombre, r.rie_proceso_id\n" +
            "ORDER BY \n" +
            "    CASE \n" +
            "        WHEN SUBSTRING(mac.des_clave FROM '^[A-Z]+') = 'PO' THEN 1\n" +
            "        WHEN SUBSTRING(mac.des_clave FROM '^[A-Z]+') = 'PA' THEN 2\n" +
            "        WHEN SUBSTRING(mac.des_clave FROM '^[A-Z]+') = 'PE' THEN 3\n" +
            "        ELSE 4\n" +
            "    END,\n" +
            "    CAST(SUBSTRING(mac.des_clave FROM '[0-9]+$') AS INTEGER)", nativeQuery = true)
    List<Object[]> getValoracionExposicionResidual(@Param("fechaDesde") Date fechaDesde, @Param("fechaHasta") Date fechaHasta);


}