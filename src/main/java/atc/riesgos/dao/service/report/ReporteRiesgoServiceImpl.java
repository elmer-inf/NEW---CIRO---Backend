package atc.riesgos.dao.service.report;

import atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa1.MapaInherenteResidual1DTO;
import atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa1.PerfilRiesgoDTO;
import atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa1.ValoracionExposicionDTO;
import atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa2.MapaInherente2DTO;
import atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa2.MapaInherenteResidual2DTO;
import atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa2.MapaResidual2DTO;
import atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa2.MapaResumenDTO;
import atc.riesgos.model.repository.MatrizRiesgoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteRiesgoServiceImpl implements ReporteRiesgoService {

    @Autowired
    MatrizRiesgoRepository matrizRiesgoRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @PersistenceContext
    private EntityManager entityManager;

    public MapaInherenteResidual1DTO mapaInherenteResidual1() {
        List<Object[]> results = matrizRiesgoRepository.getValoracionExposicionInherente();

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

        return new MapaInherenteResidual1DTO(valoracionesExposicionDTO, perfilRiesgoInherenteDTO);
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
        String baseSql = "SELECT " +
                "CASE " +
                "WHEN r.rie_control_objetivo = 'Ambos' THEN GREATEST(1, ROUND(CAST(p.des_campo_a AS int) - (CAST(p.des_campo_a AS int) * CAST(c.des_campo_b AS int) / 100))) " +
                "WHEN r.rie_control_objetivo = 'Probabilidad' THEN GREATEST(1, ROUND(CAST(p.des_campo_a AS int) - (CAST(p.des_campo_a AS int) * CAST(c.des_campo_b AS int) / 100))) " +
                "ELSE CAST(p.des_campo_a AS int) " +
                "END AS probabilidad_residual, " +
                "CASE " +
                "WHEN r.rie_control_objetivo = 'Ambos' THEN GREATEST(1, ROUND(CAST(i.des_campo_a AS int) - (CAST(i.des_campo_a AS int) * CAST(c.des_campo_b AS int) / 100))) " +
                "WHEN r.rie_control_objetivo = 'Impacto' THEN GREATEST(1, ROUND(CAST(i.des_campo_a AS int) - (CAST(i.des_campo_a AS int) * CAST(c.des_campo_b AS int) / 100))) " +
                "ELSE CAST(i.des_campo_a AS int) " +
                "END AS impacto_residual " +
                "FROM riesgos.tbl_matriz_riesgo r " +
                "INNER JOIN riesgos.tbl_tabla_descripcion_matriz_riesgo p ON r.rie_probabilidad_id = p.des_id " +
                "INNER JOIN riesgos.tbl_tabla_descripcion_matriz_riesgo i ON r.rie_impacto_id = i.des_id " +
                "INNER JOIN riesgos.tbl_tabla_descripcion_matriz_riesgo c ON r.rie_control_id = c.des_id " +
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

}
