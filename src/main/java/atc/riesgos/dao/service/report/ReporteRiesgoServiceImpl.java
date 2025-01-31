package atc.riesgos.dao.service.report;

import atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa1.*;
import atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa2.MapaInherente2DTO;
import atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa2.MapaInherenteResidual2DTO;
import atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa2.MapaResidual2DTO;
import atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa2.MapaResumenDTO;
import atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa2.conRiesgos.MapaInherente2ConRiesgosDTO;
import atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa2.conRiesgos.MapaInherente2ConRiesgosListDTO;
import atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa2.conRiesgos.MapaResidual2ConRiesgosDTO;
import atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa2.conRiesgos.MapaResidual2ConRiesgosListDTO;
import atc.riesgos.model.dto.report.ciro.riesgos.FiltroReporteConfigRiesgo;
import atc.riesgos.model.dto.report.ciro.riesgos.FiltroReporteGerencialDTO;
import atc.riesgos.model.dto.report.ciro.riesgos.ResponseReporteGerencialDTO;
import atc.riesgos.model.repository.EventoRiesgoRepository;
import atc.riesgos.model.repository.MatrizRiesgoRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReporteRiesgoServiceImpl implements ReporteRiesgoService {

    @Autowired
    MatrizRiesgoRepository matrizRiesgoRepository;
    @Autowired
    EventoRiesgoRepository eventoRiesgoRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @PersistenceContext
    private EntityManager entityManager;

    // MAPA 1
    public MapaInherente1DTO mapaInherente1(Date fechaDesde, Date fechaHasta) {

        List<Object[]> results = matrizRiesgoRepository.getValoracionExposicionInherente(fechaDesde, fechaHasta);

        /* ---------- PERFIL DE RIESGO ATC ----------- */
        // obtiene: Valoracion probabilidad (Calcula el promedio de probabilidad)
        double probabilidadProm = results.stream()
                .mapToInt(result -> (int) result[5])
                .average()
                .orElse(0);
        int perfilProbabilidad = (int) Math.round(probabilidadProm);

        // Obtiene: Prob
        String sql1 = "SELECT des_campo_c FROM riesgos.tbl_tabla_descripcion_matriz_riesgo " +
                "WHERE des_tabla_id = 2 AND des_campo_a = ? LIMIT 1";
        String perfilProb = jdbcTemplate.queryForObject(sql1, new Object[]{String.valueOf(perfilProbabilidad)}, String.class);

        // Obtiene: Factor probabilidad
        String sql2 = "SELECT des_campo_e FROM riesgos.tbl_tabla_descripcion_matriz_riesgo " +
                "WHERE des_tabla_id = 2 AND des_campo_a = ? LIMIT 1";
        float perfilFactorProbabilidad = jdbcTemplate.queryForObject(sql2, new Object[]{String.valueOf(perfilProbabilidad)}, Float.class);

        // Obtiene: Probabilidad Desc
        String sql3 = "SELECT des_nombre FROM riesgos.tbl_tabla_descripcion_matriz_riesgo " +
                "WHERE des_tabla_id = 2 AND des_campo_a = ? LIMIT 1";
        String perfilProbabilidadDesc = jdbcTemplate.queryForObject(sql3, new Object[]{String.valueOf(perfilProbabilidad)}, String.class);

        // obtiene: Impacto por cada vez que ocurre el evento (USD)
        double perfilImpactoPorCadaSuma = results.stream()
                .mapToDouble(result -> (Float) result[8])
                .sum();

        // obtiene: Impacto
        String sql6 = "SELECT CAST(des_campo_a AS int)\n" +
                "FROM riesgos.tbl_tabla_descripcion_matriz_riesgo\n" +
                "WHERE des_tabla_id = 3 \n" +
                "  AND des_campo_e <= ? \n" +
                "  AND des_campo_f >= ?\n" +
                "UNION ALL\n" +
                "SELECT CAST(des_campo_a AS int)\n" +
                "FROM riesgos.tbl_tabla_descripcion_matriz_riesgo\n" +
                "WHERE des_tabla_id = 3\n" +
                "  AND des_campo_f = (SELECT MAX(des_campo_f) FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_tabla_id = 3)\n" +
                "  AND ? > (SELECT MAX(des_campo_f) FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_tabla_id = 3)\n" +
                "UNION ALL\n" +
                "SELECT CAST(des_campo_a AS int)\n" +
                "FROM riesgos.tbl_tabla_descripcion_matriz_riesgo\n" +
                "WHERE des_tabla_id = 3\n" +
                "  AND ? < 0\n" +
                "  AND des_campo_e = (\n" +
                "    SELECT MIN(des_campo_e)\n" +
                "    FROM riesgos.tbl_tabla_descripcion_matriz_riesgo\n" +
                "    WHERE des_tabla_id = 3\n" +
                "  );";
        int perfilImpacto = jdbcTemplate.queryForObject(sql6, new Object[]{
                perfilImpactoPorCadaSuma,
                perfilImpactoPorCadaSuma,
                perfilImpactoPorCadaSuma,
                perfilImpactoPorCadaSuma
        }, int.class);

        // Obtiene: Valoracion impacto
        String sql4 = "SELECT des_nombre FROM riesgos.tbl_tabla_descripcion_matriz_riesgo " +
                "WHERE des_tabla_id = 3 AND des_campo_a = ? LIMIT 1";
        String perfilValoracionImpacto = jdbcTemplate.queryForObject(sql4, new Object[]{String.valueOf(perfilImpacto)}, String.class);

        // obtiene: Monto Riesgo de Pérdida (Anual)
        double perfilMontoRiesgoPerdida = perfilFactorProbabilidad * perfilImpactoPorCadaSuma;

        // obtiene: Valoración Riesgo (Matriz de Riesgo)
        int perfilValoracionRiesgo = calcularValoracionRiesgo(perfilProbabilidad, perfilImpacto);

        // Obtiene: Riesgo (Matriz de Riesgo)
        String sql5 = "SELECT des_nombre FROM riesgos.tbl_tabla_descripcion_matriz_riesgo " +
                "WHERE des_tabla_id = 3 AND des_campo_a = ? LIMIT 1";
        String perfilRiesgo = jdbcTemplate.queryForObject(sql5, new Object[]{String.valueOf(perfilValoracionRiesgo)}, String.class);
        /* ---------- FIN PERFIL DE RIESGO ATC ----------- */


        // Crear lista de ValoracionExposicionDTO
        List<ValoracionExposicionDTO> valoracionesExposicionDTO = results.stream()
                .map(result -> new ValoracionExposicionDTO(
                        (int) result[0],
                        (String) result[1],
                        (String) result[2],
                        (int) result[3],
                        (String) result[4],
                        (int) result[5],
                        (Float) result[6],
                        (String) result[7],
                        (Float) result[8],
                        (int) result[9],
                        (String) result[10],
                        (Float) result[11],
                        (int) result[12],
                        (String) result[13]
                ))
                .collect(Collectors.toList());

        PerfilRiesgoDTO perfilRiesgoInherenteDTO = new PerfilRiesgoDTO(
                perfilProb,
                perfilProbabilidad,
                perfilFactorProbabilidad,
                perfilProbabilidadDesc,
                (float) perfilImpactoPorCadaSuma,
                perfilImpacto,
                perfilValoracionImpacto,
                (float) perfilMontoRiesgoPerdida,
                perfilValoracionRiesgo,
                perfilRiesgo
        );

        return new MapaInherente1DTO(valoracionesExposicionDTO, perfilRiesgoInherenteDTO);
    }

    public MapaResidual1DTO mapaResidual1(Date fechaDesde, Date fechaHasta) {

        List<Object[]> results = matrizRiesgoRepository.getValoracionExposicionResidual(fechaDesde, fechaHasta);

        /* ---------- PERFIL DE RIESGO ATC ----------- */
        // obtiene: Valoracion probabilidad (Calcula el promedio de probabilidad)
        double probabilidadProm = results.stream()
                .mapToInt(result -> (int) result[5])
                .average()
                .orElse(0);
        int perfilProbabilidad = (int) Math.round(probabilidadProm);

        // Obtiene: Prob
        String sql1 = "SELECT des_campo_c FROM riesgos.tbl_tabla_descripcion_matriz_riesgo " +
                "WHERE des_tabla_id = 2 AND des_campo_a = ? LIMIT 1";
        String perfilProb = jdbcTemplate.queryForObject(sql1, new Object[]{String.valueOf(perfilProbabilidad)}, String.class);

        // Obtiene: Factor probabilidad
        String sql2 = "SELECT des_campo_e FROM riesgos.tbl_tabla_descripcion_matriz_riesgo " +
                "WHERE des_tabla_id = 2 AND des_campo_a = ? LIMIT 1";
        float perfilFactorProbabilidad = jdbcTemplate.queryForObject(sql2, new Object[]{String.valueOf(perfilProbabilidad)}, Float.class);

        // Obtiene: Probabilidad Desc
        String sql3 = "SELECT des_nombre FROM riesgos.tbl_tabla_descripcion_matriz_riesgo " +
                "WHERE des_tabla_id = 2 AND des_campo_a = ? LIMIT 1";
        String perfilProbabilidadDesc = jdbcTemplate.queryForObject(sql3, new Object[]{String.valueOf(perfilProbabilidad)}, String.class);

        // obtiene: Impacto por cada vez que ocurre el evento (USD)
        double perfilImpactoPorCadaSuma = results.stream()
                .mapToDouble(result -> (Float) result[8])
                .sum();

        // obtiene: Impacto
        String sql6 = "SELECT CAST(des_campo_a AS int)\n" +
                "FROM riesgos.tbl_tabla_descripcion_matriz_riesgo\n" +
                "WHERE des_tabla_id = 3 \n" +
                "  AND des_campo_e <= ? \n" +
                "  AND des_campo_f >= ?\n" +
                "UNION ALL\n" +
                "SELECT CAST(des_campo_a AS int)\n" +
                "FROM riesgos.tbl_tabla_descripcion_matriz_riesgo\n" +
                "WHERE des_tabla_id = 3\n" +
                "  AND des_campo_f = (SELECT MAX(des_campo_f) FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_tabla_id = 3)\n" +
                "  AND ? > (SELECT MAX(des_campo_f) FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_tabla_id = 3)\n" +
                "UNION ALL\n" +
                "SELECT CAST(des_campo_a AS int)\n" +
                "FROM riesgos.tbl_tabla_descripcion_matriz_riesgo\n" +
                "WHERE des_tabla_id = 3\n" +
                "  AND ? < 0\n" +
                "  AND des_campo_e = (\n" +
                "    SELECT MIN(des_campo_e)\n" +
                "    FROM riesgos.tbl_tabla_descripcion_matriz_riesgo\n" +
                "    WHERE des_tabla_id = 3\n" +
                "  );";
        int perfilImpacto = jdbcTemplate.queryForObject(sql6, new Object[]{
                perfilImpactoPorCadaSuma,
                perfilImpactoPorCadaSuma,
                perfilImpactoPorCadaSuma,
                perfilImpactoPorCadaSuma
        }, int.class);

        // Obtiene: Valoracion impacto
        String sql4 = "SELECT des_nombre FROM riesgos.tbl_tabla_descripcion_matriz_riesgo " +
                "WHERE des_tabla_id = 3 AND des_campo_a = ? LIMIT 1";
        String perfilValoracionImpacto = jdbcTemplate.queryForObject(sql4, new Object[]{String.valueOf(perfilImpacto)}, String.class);

        // obtiene: Monto Riesgo de Pérdida (Anual)
        double perfilMontoRiesgoPerdida = perfilFactorProbabilidad * perfilImpactoPorCadaSuma;

        // obtiene: Valoración Riesgo (Matriz de Riesgo)
        int perfilValoracionRiesgo = calcularValoracionRiesgo(perfilProbabilidad, perfilImpacto);

        // Obtiene: Riesgo (Matriz de Riesgo)
        String sql5 = "SELECT des_nombre FROM riesgos.tbl_tabla_descripcion_matriz_riesgo " +
                "WHERE des_tabla_id = 3 AND des_campo_a = ? LIMIT 1";
        String perfilRiesgo = jdbcTemplate.queryForObject(sql5, new Object[]{String.valueOf(perfilValoracionRiesgo)}, String.class);
        /* ---------- FIN PERFIL DE RIESGO ATC ----------- */


        // Crear lista de ValoracionExposicionDTO
        List<ValoracionExposicionDTO> valoracionesExposicionDTO = results.stream()
                .map(result -> new ValoracionExposicionDTO(
                        (int) result[0],
                        (String) result[1],
                        (String) result[2],
                        (int) result[3],
                        (String) result[4],
                        (int) result[5],
                        (Float) result[6],
                        (String) result[7],
                        (Float) result[8],
                        (int) result[9],
                        (String) result[10],
                        (Float) result[11],
                        (int) result[12],
                        (String) result[13]
                ))
                .collect(Collectors.toList());

        PerfilRiesgoDTO perfilRiesgoInherenteDTO = new PerfilRiesgoDTO(
                perfilProb,
                perfilProbabilidad,
                perfilFactorProbabilidad,
                perfilProbabilidadDesc,
                (float) perfilImpactoPorCadaSuma,
                perfilImpacto,
                perfilValoracionImpacto,
                (float) perfilMontoRiesgoPerdida,
                perfilValoracionRiesgo,
                perfilRiesgo
        );

        return new MapaResidual1DTO(valoracionesExposicionDTO, perfilRiesgoInherenteDTO);
    }

    public int calcularValoracionRiesgo(int probabilidad, int impacto) {
        if (probabilidad == 1) {
            switch (impacto) {
                case 1: case 2: return 1;
                case 3: return 2;
                case 4: case 5: return 3;
                default: return 0;
            }
        } else if (probabilidad == 2) {
            switch (impacto) {
                case 1: return 1;
                case 2: return 2;
                case 3: case 4: return 3;
                case 5: return 4;
                default: return 0;
            }
        } else if (probabilidad == 3) {
            switch (impacto) {
                case 1: case 2: return 2;
                case 3: return 3;
                case 4: case 5: return 4;
                default: return 0;
            }
        } else if (probabilidad == 4) {
            switch (impacto) {
                case 1: return 2;
                case 2: case 3: return 3;
                case 4: case 5: return 4;
                default: return 0;
            }
        } else if (probabilidad == 5) {
            switch (impacto) {
                case 1: case 2: return 3;
                case 3: return 4;
                case 4: case 5: return 5;
                default: return 0;
            }
        } else {
            return 0;
        }
    }

    public MapaInherenteResidual1DTO mapaInherenteResidual1(Date fechaDesde, Date fechaHasta) {
        MapaInherente1DTO mapaInherente1DTO = mapaInherente1(fechaDesde, fechaHasta);
        MapaResidual1DTO mapaResidual1DTO = mapaResidual1(fechaDesde, fechaHasta);

        MapaInherenteResidual1DTO mapaInherenteResidual1DTO = new MapaInherenteResidual1DTO(mapaInherente1DTO, mapaResidual1DTO);
        return mapaInherenteResidual1DTO;
    }


    // MAPA 2
    public Object[][] mapaInherente2(Long procesoId) {
        Object[][] matrix = new Object[8][8];

        // Llenar la matriz con valores por defecto o vacíos
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                matrix[i][j] = "";
            }
        }

        // LLENA COLUMNA 1 Y 2 CON DATOS DE "PROB" Y "PROBABILIDAD"
        String sqlProb = "SELECT des_campo_c as prob, des_nombre as probabilidad FROM riesgos.tbl_tabla_descripcion_matriz_riesgo  WHERE des_tabla_id = 2 ORDER BY des_id ASC LIMIT 5";
        List<Object[]> resultsProb = jdbcTemplate.query(sqlProb, (rs, rowNum) -> new Object[]{rs.getString("prob"), rs.getString("probabilidad")});

        for (int i = 0; i < 5; i++) {
            Object[] row = resultsProb.get(i);
            matrix[i][0] = row[0];
            matrix[i][1] = row[1];
        }

        // LLENA TOTALES DE PROBABILIDAD E IMPACTO
        String baseSql = "SELECT " +
                "(SELECT CAST(des_campo_a AS int) as prob FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_id = rie_probabilidad_id), " +
                "(SELECT CAST(des_campo_a AS int) as imp FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_id = rie_impacto_id) " +
                "FROM riesgos.tbl_matriz_riesgo r " +
                "WHERE r.rie_delete = FALSE ";

        if (procesoId != null) {
            baseSql += "AND r.rie_proceso_id = :procesoId ";
        }

        Query query = entityManager.createNativeQuery(baseSql);

        if (procesoId != null) {
            query.setParameter("procesoId", procesoId);
        }

        List<Object[]> results = query.getResultList();

        // Inicializar la sección de la matriz que almacena los contadores
        for (int i = 0; i < 5; i++) {
            for (int j = 2; j < 7; j++) {
                matrix[i][j] = 0;  // Inicializa con 0
            }
        }

        // Sumar las coincidencias para cada combinación de probabilidad e impacto
        for (Object[] result : results) {
            if (result[0] != null && result[1] != null) {
                int probabilidad = (int) result[0];
                int impacto = (int) result[1];

                // Asegurar que probabilidad e impacto estén en el rango [1,5]
                if (probabilidad >= 1 && probabilidad <= 5 && impacto >= 1 && impacto <= 5) {
                    // Calcula los índices para la matriz basados en la especificación dada
                    int rowIndex = 5 - probabilidad; // Convierte probabilidad 5 a índice 0, probabilidad 1 a índice 4
                    int columnIndex = impacto + 1; // Convierte impacto 1 a columna 2, impacto 5 a columna 6

                    // Incrementar el contador en la matriz en la posición adecuada
                    matrix[rowIndex][columnIndex] = (int) matrix[rowIndex][columnIndex] + 1;
                }
            }
        }

        // LLENA FILA 5 Y 6 CON DATOS DE "IMPACTO" y "LIMITE "SUPERIOR DE IMPACTO"
        String sqlImp = "SELECT des_nombre AS impacto, des_campo_f AS limite FROM riesgos.tbl_tabla_descripcion_matriz_riesgo  WHERE des_tabla_id = 3 ORDER BY des_id DESC LIMIT 5";
        List<Object[]> resultsImp = jdbcTemplate.query(sqlImp, (rs, rowNum) -> new Object[]{rs.getString("impacto"), rs.getString("limite")});
        // Asignar valores de "impacto"
        for (int i = 0; i < resultsImp.size(); i++) {
            matrix[5][i + 2] = resultsImp.get(i)[0]; // Valores de "impacto" en la fila 5, columnas de 2 a 6
        }
        // Asignar valores de "limite"
        for (int i = 0; i < resultsImp.size(); i++) {
            matrix[6][i + 2] = resultsImp.get(i)[1]; // Valores de "limite" en la fila 6, columnas de 2 a 6
        }

        // LLENA VALORES FIJOS
        matrix[5][0] = "Probabilidad";
        matrix[6][1] = "Impacto en USD";
        matrix[7][0] = "TOTAL IMPACTO";

        // LLENA CALCULOS DE SUMA
        // Calcular sumas de "Probabilidad e Impacto" (horizontal)
        for (int i = 0; i < 5; i++) {
            int sum = 0;
            for (int j = 2; j < 7; j++) {
                sum += (Integer) matrix[i][j];
            }
            matrix[i][7] = sum;
        }

        // Calcular sumas de "Probabilidad e Impacto" (vertical)
        for (int j = 2; j < 7; j++) {
            int sum = 0;
            for (int i = 0; i < 5; i++) {
                sum += (Integer) matrix[i][j];
            }
            matrix[7][j] = sum;
        }

        // Calcula suma de las sumas de "Probabilidad e Impacto" (vertical)
        int totalImpact = 0;
        for (int i = 0; i < 5; i++) {
            totalImpact += (Integer) matrix[i][7];
        }
        matrix[7][7] = totalImpact;

        return matrix;
    }

    public Object[][] mapaResidual2(Long procesoId) {
        Object[][] matrix = new Object[8][8];
        // Llenar la matriz con valores por defecto o vacíos
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                matrix[i][j] = "";
            }
        }

        // LLENA COLUMNA 1 Y 2 CON DATOS DE "PROB" Y "PROBABILIDAD"
        String sqlProb = "SELECT des_campo_c as prob, des_nombre as probabilidad FROM riesgos.tbl_tabla_descripcion_matriz_riesgo  WHERE des_tabla_id = 2 ORDER BY des_id ASC LIMIT 5";
        List<Object[]> resultsProb = jdbcTemplate.query(sqlProb, (rs, rowNum) -> new Object[]{rs.getString("prob"), rs.getString("probabilidad")});

        for (int i = 0; i < 5; i++) {
            Object[] row = resultsProb.get(i);
            matrix[i][0] = row[0];
            matrix[i][1] = row[1];
        }

        // LLENA TOTALES DE PROBABILIDAD E IMPACTO
        String baseSql = "SELECT r.rie_probabilidad_residual as probabilidad_residual, r.rie_impacto_residual as impacto_residual\n" +
                            "FROM riesgos.tbl_matriz_riesgo r\n" +
                            "WHERE r.rie_delete = FALSE ";

        if (procesoId != null) {
            baseSql += " AND r.rie_proceso_id = :procesoId ";
        }

        Query query = entityManager.createNativeQuery(baseSql);

        if (procesoId != null) {
            query.setParameter("procesoId", procesoId);
        }

        List<Object[]> results = query.getResultList();

        // Inicializar la sección de la matriz que almacena los contadores
        for (int i = 0; i < 5; i++) {
            for (int j = 2; j < 7; j++) {
                matrix[i][j] = 0;  // Inicializa con 0
            }
        }

        // Sumar las coincidencias para cada combinación de probabilidad e impacto
        for (Object[] result : results) {
            if (result[0] != null && result[1] != null) {
                int probabilidad = ((Number) result[0]).intValue();
                int impacto = ((Number) result[1]).intValue();

                // Asegurar que probabilidad e impacto estén en el rango [1,5]
                if (probabilidad >= 1 && probabilidad <= 5 && impacto >= 1 && impacto <= 5) {
                    // Calcula los índices para la matriz basados en la especificación dada
                    int rowIndex = 5 - probabilidad; // Convierte probabilidad 5 a índice 0, probabilidad 1 a índice 4
                    int columnIndex = impacto + 1; // Convierte impacto 1 a columna 2, impacto 5 a columna 6

                    // Incrementar el contador en la matriz en la posición adecuada
                    matrix[rowIndex][columnIndex] = (int) matrix[rowIndex][columnIndex] + 1;
                }
            }
        }

        // LLENA FILA 5 Y 6 CON DATOS DE "IMPACTO" y "LIMITE "SUPERIOR DE IMPACTO"
        String sqlImp = "SELECT des_nombre AS impacto, des_campo_f AS limite FROM riesgos.tbl_tabla_descripcion_matriz_riesgo  WHERE des_tabla_id = 3 ORDER BY des_id DESC LIMIT 5";
        List<Object[]> resultsImp = jdbcTemplate.query(sqlImp, (rs, rowNum) -> new Object[]{rs.getString("impacto"), rs.getString("limite")});
        // Asignar valores de "impacto"
        for (int i = 0; i < resultsImp.size(); i++) {
            matrix[5][i + 2] = resultsImp.get(i)[0]; // Valores de "impacto" en la fila 5, columnas de 2 a 6
        }
        // Asignar valores de "limite"
        for (int i = 0; i < resultsImp.size(); i++) {
            matrix[6][i + 2] = resultsImp.get(i)[1]; // Valores de "limite" en la fila 6, columnas de 2 a 6
        }

        // LLENA VALORES FIJOS
        matrix[5][0] = "Probabilidad";
        matrix[6][1] = "Impacto en USD";
        matrix[7][0] = "TOTAL IMPACTO";

        // LLENA CALCULOS DE SUMA
        // Calcular sumas de "Probabilidad e Impacto" (horizontal)
        for (int i = 0; i < 5; i++) {
            int sum = 0;
            for (int j = 2; j < 7; j++) {
                sum += (Integer) matrix[i][j];
            }
            matrix[i][7] = sum;
        }

        // Calcular sumas de "Probabilidad e Impacto" (vertical)
        for (int j = 2; j < 7; j++) {
            int sum = 0;
            for (int i = 0; i < 5; i++) {
                sum += (Integer) matrix[i][j];
            }
            matrix[7][j] = sum;
        }

        // Calcula suma de las sumas de "Probabilidad e Impacto" (vertical)
        int totalImpact = 0;
        for (int i = 0; i < 5; i++) {
            totalImpact += (Integer) matrix[i][7];
        }
        matrix[7][7] = totalImpact;

        return matrix;
    }


    public MapaInherenteResidual2DTO mapaInherenteResidual2 (Long procesoId){
        Object[][] matrizInherente = mapaInherente2(procesoId);
        Object[][] matrizResidual = mapaResidual2(procesoId);

        // Vaciado de la matriz inherente a DTO
        List<MapaInherente2DTO> listMapaInherente2DTO = new ArrayList<>();
        for (Object[] row : matrizInherente) {
            if (row != null) {
                MapaInherente2DTO dto = new MapaInherente2DTO(
                        row[0].toString(), row[1].toString(), row[2].toString(), row[3].toString(), row[4].toString(), row[5].toString(), row[6].toString(), row[7].toString()
                );
                listMapaInherente2DTO.add(dto);
            }
        }

        // Vaciado de la matriz residual a DTO
        List<MapaResidual2DTO> listMapaResidual2DTO = new ArrayList<>();
        for (Object[] row : matrizResidual) {
            if (row != null) {
                MapaResidual2DTO dto = new MapaResidual2DTO(
                        row[0].toString(), row[1].toString(), row[2].toString(), row[3].toString(), row[4].toString(), row[5].toString(), row[6].toString(), row[7].toString()
                );
                listMapaResidual2DTO.add(dto);
            }
        }

        // Crear matriz resumen
        Object[][] matrizResumen = new Object[7][5];

        matrizResumen[0][0] = "Calificación";
        matrizResumen[0][1] = "NIVEL";
        matrizResumen[0][2] = "DESCRIPTIVO";
        matrizResumen[0][3] = "Descripción";
        matrizResumen[0][4] = "Descripción";

        matrizResumen[1][0] = "5";
        matrizResumen[2][0] = "4";
        matrizResumen[3][0] = "3";
        matrizResumen[4][0] = "2";
        matrizResumen[5][0] = "1";

        matrizResumen[1][1] = "Cuadrante I";
        matrizResumen[2][1] = "Cuadrante II";
        matrizResumen[3][1] = "Cuadrante III";
        matrizResumen[4][1] = "Cuadrante IV";
        matrizResumen[5][1] = "Cuadrante V";

        matrizResumen[1][2] = "Alto";
        matrizResumen[2][2] = "Medio Alto";
        matrizResumen[3][2] = "Medio";
        matrizResumen[4][2] = "Medio Bajo";
        matrizResumen[5][2] = "Bajo";

        // Suma de las posiciones para matrizInherente
        matrizResumen[1][3] = String.valueOf((Integer) matrizInherente[0][5] + (Integer) matrizInherente[0][6] + (Integer) matrizInherente[1][6]);
        matrizResumen[2][3] = String.valueOf((Integer) matrizInherente[0][4] + (Integer) matrizInherente[1][5] + (Integer) matrizInherente[2][5] + (Integer) matrizInherente[2][6] + (Integer) matrizInherente[3][6]);
        matrizResumen[3][3] = String.valueOf((Integer) matrizInherente[0][2] + (Integer) matrizInherente[0][3] + (Integer) matrizInherente[1][3] + (Integer) matrizInherente[1][4] + (Integer) matrizInherente[2][4] + (Integer) matrizInherente[3][4] + (Integer) matrizInherente[3][5] + (Integer) matrizInherente[4][5] + (Integer) matrizInherente[4][6]);
        matrizResumen[4][3] = String.valueOf((Integer) matrizInherente[1][2] + (Integer) matrizInherente[2][2] + (Integer) matrizInherente[2][3] + (Integer) matrizInherente[3][3] + (Integer) matrizInherente[4][4]);
        matrizResumen[5][3] = String.valueOf((Integer) matrizInherente[3][2] + (Integer) matrizInherente[4][2] + (Integer) matrizInherente[4][3]);

        // Suma de las posiciones para matrizResidual
        matrizResumen[1][4] = String.valueOf((Integer) matrizResidual[0][5] + (Integer) matrizResidual[0][6] + (Integer) matrizResidual[1][6]);
        matrizResumen[2][4] = String.valueOf((Integer) matrizResidual[0][4] + (Integer) matrizResidual[1][5] + (Integer) matrizResidual[2][5] + (Integer) matrizResidual[2][6] + (Integer) matrizResidual[3][6]);
        matrizResumen[3][4] = String.valueOf((Integer) matrizResidual[0][2] + (Integer) matrizResidual[0][3] + (Integer) matrizResidual[1][3] + (Integer) matrizResidual[1][4] + (Integer) matrizResidual[2][4] + (Integer) matrizResidual[3][4] + (Integer) matrizResidual[3][5] + (Integer) matrizResidual[4][5] + (Integer) matrizResidual[4][6]);
        matrizResumen[4][4] = String.valueOf((Integer) matrizResidual[1][2] + (Integer) matrizResidual[2][2] + (Integer) matrizResidual[2][3] + (Integer) matrizResidual[3][3] + (Integer) matrizResidual[4][4]);
        matrizResumen[5][4] = String.valueOf((Integer) matrizResidual[3][2] + (Integer) matrizResidual[4][2] + (Integer) matrizResidual[4][3]);

        // Totales
        matrizResumen[6][0] = "Total";
        matrizResumen[6][1] = "";
        matrizResumen[6][2] = "";
        matrizResumen[6][3] = String.valueOf(
                Integer.parseInt((String) matrizResumen[1][3]) + Integer.parseInt((String) matrizResumen[2][3]) + Integer.parseInt((String) matrizResumen[3][3]) + Integer.parseInt((String) matrizResumen[4][3]) + Integer.parseInt((String) matrizResumen[5][3])
        );
        matrizResumen[6][4] = String.valueOf(
                Integer.parseInt((String) matrizResumen[1][4]) + Integer.parseInt((String) matrizResumen[2][4]) + Integer.parseInt((String) matrizResumen[3][4]) + Integer.parseInt((String) matrizResumen[4][4]) + Integer.parseInt((String) matrizResumen[5][4])
        );

        // Vaciado de la matriz resumen a DTO
        List<MapaResumenDTO> listMapaResumenDTO = new ArrayList<>();
        for (Object[] row : matrizResumen) {
            if (row != null) {
                MapaResumenDTO dto = new MapaResumenDTO(
                        row[0].toString(), row[1].toString(), row[2].toString(), row[3].toString(), row[4].toString()
                );
                listMapaResumenDTO.add(dto);
            }
        }

        // Crear y devolver el DTO combinado
        return new MapaInherenteResidual2DTO(listMapaInherente2DTO, listMapaResidual2DTO, listMapaResumenDTO);
    }


    // Mapa inherente con Riesgos
    public MapaInherente2ConRiesgosListDTO getMapaInherente2ConRiesgos(Long procesoId) {
        MapaInherente2ConRiesgosListDTO finalListDTO = new MapaInherente2ConRiesgosListDTO();

        // Definir los IDs de probabilidad e impacto
        int[] probabilidadIds = {5, 6, 7, 8, 9};  // Probabilidad 5 a 1 (ids en tabla parametrizada)
        int[] impactoIds = {14, 13, 12, 11, 10};  // Impacto de 1 a 5 (ids en tabla parametrizada)

        // Iterar sobre cada probabilidad
        for (int probId : probabilidadIds) {
            MapaInherente2ConRiesgosDTO dto = new MapaInherente2ConRiesgosDTO();

            // Iterar sobre cada impacto para la probabilidad actual
            for (int i = 0; i < impactoIds.length; i++) {
                int impId = impactoIds[i];
                String sql = "SELECT rie_id, rie_codigo FROM riesgos.tbl_matriz_riesgo WHERE rie_probabilidad_id = ? AND rie_impacto_id = ?";
                List<Object> params = new ArrayList<>(Arrays.asList(probId, impId));

                if (procesoId != null) {
                    sql += " AND rie_proceso_id = ?";
                    params.add(procesoId);
                }

                List<String> results = jdbcTemplate.query(sql, params.toArray(), (rs, rowNum) ->
                        rs.getLong("rie_id") + ", " + rs.getString("rie_codigo")
                );

                // Asignar los resultados a la columna correspondiente basada en el índice del impacto
                switch (i) {
                    case 0: dto.getCol1().addAll(results); break;
                    case 1: dto.getCol2().addAll(results); break;
                    case 2: dto.getCol3().addAll(results); break;
                    case 3: dto.getCol4().addAll(results); break;
                    case 4: dto.getCol5().addAll(results); break;
                }
            }
            finalListDTO.getListMapaInherente2ConRiesgosDTO().add(dto);
        }

        return finalListDTO;
    }

    // Mapa residual con Riesgos
    public MapaResidual2ConRiesgosListDTO getMapaResidual2ConRiesgos(Long procesoId) {
        MapaResidual2ConRiesgosListDTO finalListDTO = new MapaResidual2ConRiesgosListDTO();

        // Definir los IDs de probabilidad e impacto
        int[] probabilidadIds = {5, 4, 3, 2, 1};  // Probabilidad 5 a 1
        int[] impactoIds = {1, 2, 3, 4, 5};  // Impacto de 1 a 5

        // Iterar sobre cada probabilidad
        for (int probId : probabilidadIds) {
            MapaResidual2ConRiesgosDTO dto = new MapaResidual2ConRiesgosDTO();

            // Iterar sobre cada impacto para la probabilidad actual
            for (int i = 0; i < impactoIds.length; i++) {
                int impId = impactoIds[i];
                String sql = "SELECT rie_id, rie_codigo FROM riesgos.tbl_matriz_riesgo WHERE rie_probabilidad_residual = ? AND rie_impacto_residual = ?";
                List<Object> params = new ArrayList<>(Arrays.asList(probId, impId));

                if (procesoId != null) {
                    sql += " AND rie_proceso_id = ?";
                    params.add(procesoId);
                }

                List<String> results = jdbcTemplate.query(sql, params.toArray(), (rs, rowNum) ->
                        rs.getLong("rie_id") + ", " + rs.getString("rie_codigo")
                );

                // Asignar los resultados a la columna correspondiente basada en el índice del impacto
                switch (i) {
                    case 0: dto.getCol1().addAll(results); break;
                    case 1: dto.getCol2().addAll(results); break;
                    case 2: dto.getCol3().addAll(results); break;
                    case 3: dto.getCol4().addAll(results); break;
                    case 4: dto.getCol5().addAll(results); break;
                }
            }
            finalListDTO.getListMapaResidual2ConRiesgosDTO().add(dto);
        }

        return finalListDTO;
    }


    // REPORTE DINAMICO MATRIZ DE RIESGOS
    @Override
    public byte[] reporteConfigRiesgo(FiltroReporteConfigRiesgo filter)  {
        List<Map<String, Object>> results = getDataRiesgosColumns(filter);

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Matriz de Riesgos");

            // Estilo para cabecera: texto en negrita, fondo azul claro
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.BLACK.getIndex());
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            headerCellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
            headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerCellStyle.setBorderTop(BorderStyle.THIN);
            headerCellStyle.setBorderBottom(BorderStyle.THIN);
            headerCellStyle.setBorderLeft(BorderStyle.THIN);
            headerCellStyle.setBorderRight(BorderStyle.THIN);
            headerCellStyle.setWrapText(true); // Habilitar salto de línea en la cabecera

            // Estilo para las celdas de datos: con bordes
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);

            // Crear fila de cabecera y establecer ancho fijo para todas las columnas
            Row headerRow = sheet.createRow(0);
            for (int colIdx = 0; colIdx < filter.getDataColumns().size(); colIdx++) {
                FiltroReporteConfigRiesgo.DataColumn column = filter.getDataColumns().get(colIdx);
                Cell cell = headerRow.createCell(colIdx);
                cell.setCellValue(column.getLabel());
                cell.setCellStyle(headerCellStyle);
                sheet.setColumnWidth(colIdx, 20 * 256); // Ancho fijo (20 caracteres)
            }

            // Llenado de datos
            int rowIdx = 1;
            for (Map<String, Object> result : results) {
                Row row = sheet.createRow(rowIdx++);
                int colIdx = 0;
                for (FiltroReporteConfigRiesgo.DataColumn column : filter.getDataColumns()) {
                    Cell cell = row.createCell(colIdx++);
                    Object value = result.get(column.getLabel());
                    cell.setCellValue(value != null ? value.toString() : "");
                    cell.setCellStyle(cellStyle);
                }
            }

            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el reporte", e);
        }
    }

    public List<Map<String, Object>> getDataRiesgosColumns(FiltroReporteConfigRiesgo filter) {
        StringBuilder query = new StringBuilder("SELECT ");
        List<String> columns = filter.getDataColumns().stream()
                .map(column -> {
                    switch (column.getId()) {
                        case 1:
                            return "(SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion d WHERE r.rie_area_id = d.des_id) AS \"" + column.getLabel() + "\"";
                        case 2:
                            return "(SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion d WHERE r.rie_unidad_id = d.des_id) AS \"" + column.getLabel() + "\"";
                        case 3:
                            return "(SELECT d.des_clave FROM riesgos.tbl_tabla_descripcion d WHERE r.rie_proceso_id = d.des_id) AS \"" + column.getLabel() + "\"";
                        case 4:
                            return "(SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion d WHERE r.rie_proceso_id = d.des_id) AS \"" + column.getLabel() + "\"";
                        case 5:
                            return "(SELECT d.des_campo_a FROM riesgos.tbl_tabla_descripcion d WHERE r.rie_procedimiento_id = d.des_id) AS \"" + column.getLabel() + "\"";
                        case 6:
                            return "(SELECT ROUND(CAST(REPLACE(d.des_descripcion, ',', '.') AS NUMERIC)) FROM riesgos.tbl_tabla_descripcion d WHERE r.rie_proceso_id = d.des_id) AS \"" + column.getLabel() + "\"";
                        case 7:
                            return "(SELECT d.des_campo_a FROM riesgos.tbl_tabla_descripcion d WHERE r.rie_proceso_id = d.des_id) AS \"" + column.getLabel() + "\"";
                        case 8:
                            return "(SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion d WHERE r.rie_dueno_cargo_id = d.des_id) AS \"" + column.getLabel() + "\"";
                        case 9:
                            return "(SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion d WHERE r.rie_responsable_cargo_id = d.des_id) AS \"" + column.getLabel() + "\"";
                        case 10:
                            return "TO_CHAR(r.rie_fecha_evaluacion, 'DD/MM/YYYY') AS \"" + column.getLabel() + "\"";
                        case 11:
                            return "CASE WHEN rie_evento_materializado = TRUE THEN 'SI' ELSE 'NO' END AS \"" + column.getLabel() + "\"";
                        case 12:
                            return "COALESCE((SELECT eve_codigo FROM riesgos.tbl_evento_riesgo e WHERE e.eve_id = (SELECT rie_evento_riesgo_id FROM riesgos.tbl_matriz_riesgo WHERE rie_id = r.rie_id)), '') AS \"" + column.getLabel() + "\"";
                        case 13:
                            return "COALESCE((SELECT eve_descripcion FROM riesgos.tbl_evento_riesgo e WHERE e.eve_id = (SELECT rie_evento_riesgo_id FROM riesgos.tbl_matriz_riesgo WHERE rie_id = r.rie_id)), '') AS \"" + column.getLabel() + "\"";
                        case 14:
                            return "COALESCE((SELECT TO_CHAR(eve_fecha_desc, 'DD/MM/YYYY') FROM riesgos.tbl_evento_riesgo e WHERE e.eve_id = (SELECT rie_evento_riesgo_id FROM riesgos.tbl_matriz_riesgo WHERE rie_id = r.rie_id)), '') AS \"" + column.getLabel() + "\"";
                        case 15:
                            return "rie_codigo AS \"" + column.getLabel() + "\"";
                        case 16:
                            return "rie_definicion AS \"" + column.getLabel() + "\"";
                        case 17:
                            return "rie_causa AS \"" + column.getLabel() + "\"";
                        case 18:
                            return "rie_consecuencia AS \"" + column.getLabel() + "\"";
                        case 19:
                            return "COALESCE((SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion d WHERE r.rie_efecto_perdida_id = d.des_id), rie_efecto_perdida_otro) AS \"" + column.getLabel() + "\"";
                        case 20:
                            return "(SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion_matriz_riesgo d WHERE r.rie_perdida_asfi_id = d.des_id) AS \"" + column.getLabel() + "\"";
                        case 21:
                            return "CASE WHEN rie_monetario = TRUE THEN 'Monetario' ELSE 'No monetario' END AS \"" + column.getLabel() + "\"";
                        case 22:
                            return "'Riesgo de ' || rie_definicion || ' debido a ' || rie_causa || ' puede ocasionar ' || rie_consecuencia AS \"" + column.getLabel() + "\"";
                        case 23:
                            return "(SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion d WHERE r.rie_factor_riesgo_id = d.des_id) AS \"" + column.getLabel() + "\"";
                        case 24:
                            return "CASE WHEN (SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion_matriz_riesgo d WHERE r.rie_tipo_fraude_interno = d.des_id) is null THEN 'NO' ELSE 'SI' END AS \"" + column.getLabel() + "\"";
                        case 25:
                            return "COALESCE((SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion_matriz_riesgo d WHERE r.rie_tipo_fraude_interno = d.des_id), '') AS \"" + column.getLabel() + "\"";
                        case 26:
                            return "COALESCE((SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion_matriz_riesgo d WHERE r.rie_subtipo_fraude_interno = d.des_id), '') AS \"" + column.getLabel() + "\"";
                        case 27:
                            return "(SELECT d.des_campo_d FROM riesgos.tbl_tabla_descripcion_matriz_riesgo d WHERE r.rie_probabilidad_id = d.des_id) AS \"" + column.getLabel() + "\"";
                        case 28:
                            return "(SELECT d.des_campo_a FROM riesgos.tbl_tabla_descripcion_matriz_riesgo d WHERE r.rie_probabilidad_id = d.des_id) AS \"" + column.getLabel() + "\"";
                        case 29:
                            return "(SELECT d.des_campo_g FROM riesgos.tbl_tabla_descripcion_matriz_riesgo d WHERE r.rie_probabilidad_id = d.des_id) || '%' AS \"" + column.getLabel() + "\"";
                        case 30:
                            return "(SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion_matriz_riesgo d WHERE r.rie_probabilidad_id = d.des_id) AS \"" + column.getLabel() + "\"";
                        case 31:
                            return "(SELECT d.des_campo_d FROM riesgos.tbl_tabla_descripcion_matriz_riesgo d WHERE r.rie_impacto_id = d.des_id) AS \"" + column.getLabel() + "\"";
                        case 32:
                            return "(SELECT d.des_campo_a FROM riesgos.tbl_tabla_descripcion_matriz_riesgo d WHERE r.rie_impacto_id = d.des_id) AS \"" + column.getLabel() + "\"";
                        case 33:
                            return "(SELECT d.des_campo_g FROM riesgos.tbl_tabla_descripcion_matriz_riesgo d WHERE r.rie_impacto_id = d.des_id) || '%' AS \"" + column.getLabel() + "\"";
                        case 34:
                            return "(SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion_matriz_riesgo d WHERE r.rie_impacto_id = d.des_id) AS \"" + column.getLabel() + "\"";
                        case 35:
                            return "riesgos.fc_calcula_valoracion_riesgo(CAST((SELECT d.des_campo_a FROM riesgos.tbl_tabla_descripcion_matriz_riesgo d WHERE r.rie_probabilidad_id = d.des_id) AS INTEGER), CAST((SELECT d.des_campo_a FROM riesgos.tbl_tabla_descripcion_matriz_riesgo d WHERE r.rie_impacto_id = d.des_id) AS INTEGER)) AS \"" + column.getLabel() + "\"";
                        case 36:
                            return "(SELECT des_nombre FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_tabla_id = 3 AND CAST(des_campo_a AS INTEGER) = riesgos.fc_calcula_valoracion_riesgo(CAST((SELECT d.des_campo_a FROM riesgos.tbl_tabla_descripcion_matriz_riesgo d WHERE r.rie_probabilidad_id = d.des_id) AS INTEGER), CAST((SELECT d.des_campo_a FROM riesgos.tbl_tabla_descripcion_matriz_riesgo d WHERE r.rie_impacto_id = d.des_id) AS INTEGER))) AS \"" + column.getLabel() + "\"";
                        case 37:
                            return "CASE WHEN rie_controles_tiene = TRUE THEN 'SI' ELSE 'NO' END AS \"" + column.getLabel() + "\"";
                        case 38:
                            return "(SELECT string_agg(format('%s. %s', rn, campo), E'\n' ORDER BY rn) AS lista FROM (SELECT elem->>'descripcion' AS campo, row_number() OVER (ORDER BY elem->>'nroControl') AS rn FROM jsonb_array_elements(r.rie_controles::jsonb) AS elem) sub) AS \"" + column.getLabel() + "\"";
                        case 39:
                            return "(SELECT string_agg(format('%s. %s', rn, campo), E'\n' ORDER BY rn) AS lista FROM (SELECT CASE WHEN elem->>'formalizado'='true' THEN 'SI' ELSE 'NO' END AS campo, row_number() OVER (ORDER BY elem->>'nroControl') AS rn FROM jsonb_array_elements(r.rie_controles::jsonb) AS elem) sub) AS \"" + column.getLabel() + "\"";
                        case 40:
                            return "(SELECT string_agg(format('%s. %s', rn, campo), E'\n' ORDER BY rn) AS lista FROM (SELECT elem->>'norma' AS campo, row_number() OVER (ORDER BY elem->>'nroControl') AS rn FROM jsonb_array_elements(r.rie_controles::jsonb) AS elem) sub) AS \"" + column.getLabel() + "\"";
                        case 41:
                            return "(SELECT string_agg(format('%s. %s', rn, campo), E'\n' ORDER BY rn) AS lista FROM (SELECT elem->>'tipo' AS campo, row_number() OVER (ORDER BY elem->>'nroControl') AS rn FROM jsonb_array_elements(r.rie_controles::jsonb) AS elem) sub) AS \"" + column.getLabel() + "\"";
                        case 42:
                            return "(SELECT d.des_campo_a FROM riesgos.tbl_tabla_descripcion_matriz_riesgo d WHERE r.rie_control_id = d.des_id) AS \"" + column.getLabel() + "\"";
                        case 43:
                            return "(SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion_matriz_riesgo d WHERE r.rie_control_id = d.des_id) AS \"" + column.getLabel() + "\"";
                        case 44:
                            return "(SELECT d.des_campo_b || '%' FROM riesgos.tbl_tabla_descripcion_matriz_riesgo d WHERE r.rie_control_id = d.des_id) AS \"" + column.getLabel() + "\"";
                        case 45:
                            return "rie_control_objetivo AS \"" + column.getLabel() + "\"";
                        case 46:
                            return "rie_probabilidad_residual AS \"" + column.getLabel() + "\"";
                        case 47:
                            return "(SELECT des_nombre FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_tabla_id = 2 AND CAST(des_campo_a AS integer) =r.rie_probabilidad_residual) AS \"" + column.getLabel() + "\"";
                        case 48:
                            return "(SELECT des_campo_g || '%' FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_tabla_id = 2 AND CAST(des_campo_a AS integer) =r.rie_probabilidad_residual) AS \"" + column.getLabel() + "\"";
                        case 49:
                            return "rie_impacto_residual AS \"" + column.getLabel() + "\"";
                        case 50:
                            return "(SELECT des_nombre FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_tabla_id = 3 AND CAST(des_campo_a AS integer) =r.rie_impacto_residual) AS \"" + column.getLabel() + "\"";
                        case 51:
                            return "(SELECT des_campo_g || '%' FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_tabla_id = 3 AND CAST(des_campo_a AS integer) =r.rie_impacto_residual) AS \"" + column.getLabel() + "\"";
                        case 52:
                            return "riesgos.fc_calcula_valoracion_riesgo(rie_probabilidad_residual, rie_impacto_residual) AS \"" + column.getLabel() + "\"";
                        case 53:
                            return "(SELECT des_nombre FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_tabla_id = 3 AND CAST(des_campo_a AS INTEGER) = riesgos.fc_calcula_valoracion_riesgo(rie_probabilidad_residual, rie_impacto_residual)) AS \"" + column.getLabel() + "\"";
                        case 54:
                            return "jsonb_array_length(rie_planes_accion::jsonb) AS \"" + column.getLabel() + "\"";
                        case 55:
                            return "(SELECT string_agg(format('%s. %s', rn, campo), E'\n' ORDER BY rn) AS lista FROM (SELECT elem->>'estrategia' AS campo, row_number() OVER (ORDER BY elem->>'nroPlan') AS rn FROM jsonb_array_elements(r.rie_planes_accion::jsonb) AS elem) sub) AS \"" + column.getLabel() + "\"";
                        case 56:
                            return "(SELECT string_agg(format('%s. %s', rn, campo), E'\n' ORDER BY rn) AS lista FROM (SELECT elem->>'descripcion' AS campo, row_number() OVER (ORDER BY elem->>'nroPlan') AS rn FROM jsonb_array_elements(r.rie_planes_accion::jsonb) AS elem) sub) AS \"" + column.getLabel() + "\"";
                        case 57:
                            return "(SELECT string_agg(format('%s. %s', rn, campo), E'\n' ORDER BY rn) AS lista FROM (SELECT elem->>'cargo' AS campo, row_number() OVER (ORDER BY elem->>'nroPlan') AS rn FROM jsonb_array_elements(r.rie_planes_accion::jsonb) AS elem) sub) AS \"" + column.getLabel() + "\"";
                        case 58:
                            return "(SELECT string_agg(format('%s. %s', rn, campo), E'\n' ORDER BY rn) AS lista FROM (SELECT elem->>'fechaImpl' AS campo, row_number() OVER (ORDER BY elem->>'nroPlan') AS rn FROM jsonb_array_elements(r.rie_planes_accion::jsonb) AS elem) sub) AS \"" + column.getLabel() + "\"";
                        case 59:
                            return "riesgos.fc_obtiene_fecha_implementacion(r.rie_fecha_evaluacion, r.rie_id) AS \"" + column.getLabel() + "\"";
                        case 60:
                            return "(SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion d WHERE r.rie_efecto_perdida_id = d.des_id) AS \"" + column.getLabel() + "\"";
                        case 61:
                            return "(SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion_matriz_riesgo d WHERE r.rie_perdida_asfi_id = d.des_id) AS \"" + column.getLabel() + "\"";
                        case 62:
                            return "rie_criterio_impacto AS \"" + column.getLabel() + "\"";
                        case 63:
                            return "rie_criterio_probabilidad AS \"" + column.getLabel() + "\"";
                        case 64:
                            return "(SELECT des_campo_e FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_tabla_id = 2 AND CAST(des_campo_a AS integer) =r.rie_probabilidad_residual) AS \"" + column.getLabel() + "\"";
                        case 65:
                            return "rie_probabilidad_residual AS \"" + column.getLabel() + "\"";
                        case 66:
                            return "(SELECT des_nombre FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_tabla_id = 2 AND CAST(des_campo_a AS integer) =r.rie_probabilidad_residual) AS \"" + column.getLabel() + "\"";
                        case 67:
                            return "rie_impacto_usd AS \"" + column.getLabel() + "\"";
                        case 68:
                            return "rie_impacto_residual AS \"" + column.getLabel() + "\"";
                        case 69:
                            return "(SELECT des_nombre FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_tabla_id = 3 AND CAST(des_campo_a AS integer) =r.rie_impacto_residual) AS \"" + column.getLabel() + "\"";
                        case 70:
                            return "(SELECT des_campo_e FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_tabla_id = 2 AND CAST(des_campo_a AS integer) =r.rie_probabilidad_residual) * rie_impacto_usd AS \"" + column.getLabel() + "\"";
                        case 71:
                            return "(SELECT riesgos.fc_calcula_valoracion_cuantitativa(CAST((SELECT des_campo_e FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_tabla_id = 2 AND CAST(des_campo_a AS integer) = r.rie_probabilidad_residual) * rie_impacto_usd AS numeric), 'valoracionRiesgo'::varchar)) AS \"" + column.getLabel() + "\"";
                        case 72:
                            return "(SELECT riesgos.fc_calcula_valoracion_cuantitativa(CAST((SELECT des_campo_e FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_tabla_id = 2 AND CAST(des_campo_a AS integer) = r.rie_probabilidad_residual) * rie_impacto_usd AS numeric), 'riesgo'::varchar)) AS \"" + column.getLabel() + "\"";
                        case 73:
                            return "jsonb_array_length(r.rie_planes_accion::jsonb) AS \"" + column.getLabel() + "\"";
                        case 74:
                            return "(SELECT (SELECT COUNT(*) FROM jsonb_array_elements(r.rie_planes_accion::jsonb) AS elem WHERE elem->>'estado' = 'No iniciado')) AS \"" + column.getLabel() + "\"";
                        case 75:
                            return "(SELECT (SELECT COUNT(*) FROM jsonb_array_elements(r.rie_planes_accion::jsonb) AS elem WHERE elem->>'estado' = 'En proceso')) AS \"" + column.getLabel() + "\"";
                        case 76:
                            return "(SELECT (SELECT COUNT(*) FROM jsonb_array_elements(r.rie_planes_accion::jsonb) AS elem WHERE elem->>'estado' = 'Concluido')) AS \"" + column.getLabel() + "\"";
                        case 77:
                            return "rie_planes_accion_avance AS \"" + column.getLabel() + "\"";
                        case 78:
                            return "COALESCE(rie_planes_accion_estado, '') AS \"" + column.getLabel() + "\"";
                        case 79:
                            return "(SELECT string_agg(format('%s. %s', rn, campo), E'\n' ORDER BY rn) AS lista FROM (SELECT elem->>'fechaSeg' AS campo, row_number() OVER (ORDER BY elem->>'nroPlan') AS rn FROM jsonb_array_elements(r.rie_planes_accion::jsonb) AS elem) sub) AS \"" + column.getLabel() + "\"";
                        case 80:
                            return "(SELECT string_agg(format('%s. %s', rn, campo), E'\n' ORDER BY rn) AS lista FROM (SELECT elem->>'comenPropuesta' AS campo, row_number() OVER (ORDER BY elem->>'nroPlan') AS rn FROM jsonb_array_elements(r.rie_planes_accion::jsonb) AS elem) sub) AS \"" + column.getLabel() + "\"";
                        case 81:
                            return "(SELECT string_agg(format('%s. %s', rn, campo), E'\n' ORDER BY rn) AS lista FROM (SELECT elem->>'comenEnProceso' AS campo, row_number() OVER (ORDER BY elem->>'nroPlan') AS rn FROM jsonb_array_elements(r.rie_planes_accion::jsonb) AS elem) sub) AS \"" + column.getLabel() + "\"";
                        case 82:
                            return "(SELECT string_agg(format('%s. %s', rn, campo), E'\n' ORDER BY rn) AS lista FROM (SELECT elem->>'fechaImpl' AS campo, row_number() OVER (ORDER BY elem->>'nroPlan') AS rn FROM jsonb_array_elements(r.rie_planes_accion::jsonb) AS elem) sub) AS \"" + column.getLabel() + "\"";
                        default:
                            return null;
                    }
                })
                .filter(column -> column != null)
                .collect(Collectors.toList());

        query.append(String.join(", ", columns));
        query.append(" FROM riesgos.tbl_matriz_riesgo r ");
        query.append("WHERE r.rie_fecha_evaluacion >= ? AND r.rie_fecha_evaluacion <= ? ");

        if (filter.getEstadoPlan() != null && !filter.getEstadoPlan().equalsIgnoreCase("Todos")) {
            query.append("AND r.rie_planes_accion_estado = ? ");
        }
        query.append("ORDER BY r.rie_id ASC");

        if (filter.getEstadoPlan() != null && !filter.getEstadoPlan().equalsIgnoreCase("Todos")) {
            return jdbcTemplate.queryForList(query.toString(), filter.getDataFilter().getFechaDesde(), filter.getDataFilter().getFechaHasta(), filter.getEstadoPlan());
        } else {
            return jdbcTemplate.queryForList(query.toString(), filter.getDataFilter().getFechaDesde(), filter.getDataFilter().getFechaHasta());
        }
    }

    // REPORTE GERENCIAL
    public ResponseReporteGerencialDTO generarReporteGerencial(Date fechaDesde, Date fechaHasta) {
        List<Object[]> results = eventoRiesgoRepository.getTotalPerdidaUsdPorcentaje(fechaDesde, fechaHasta);
        ResponseReporteGerencialDTO response = new ResponseReporteGerencialDTO();
        List<ResponseReporteGerencialDTO.DatoMeses> datoMesesList = new ArrayList<>();
        for (Object[] result : results) {
            ResponseReporteGerencialDTO.DatoMeses datoMes = new ResponseReporteGerencialDTO.DatoMeses();
            datoMes.setAnio(((Integer) result[0]));
            datoMes.setMes(getNombreMes(((Integer) result[1])));
            datoMes.setTotalPerdida((BigDecimal) result[2]);
            datoMes.setPorcentajeTotalPerdida((BigDecimal) result[3]);
            datoMesesList.add(datoMes);
        }
        response.setDatoMeses(datoMesesList);
        response.setApetitoRiesgo(eventoRiesgoRepository.getApetitoRiesgo());
        return response;
    }

    public static String getNombreMes(int numeroMes) {
        switch (numeroMes) {
            case 1: return "Enero";
            case 2: return "Febrero";
            case 3: return "Marzo";
            case 4: return "Abril";
            case 5: return "Mayo";
            case 6: return "Junio";
            case 7: return "Julio";
            case 8: return "Agosto";
            case 9: return "Septiembre";
            case 10: return "Octubre";
            case 11: return "Noviembre";
            case 12: return "Diciembre";
            default: return "Desconocido";
        }
    }




}
