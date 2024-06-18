package atc.riesgos.dao.service.report;

import atc.riesgos.model.dto.MatrizRiesgo.mapas.MapaInherenteDTO;
import atc.riesgos.model.dto.MatrizRiesgo.mapas.PerfilRiesgoInherenteDTO;
import atc.riesgos.model.dto.MatrizRiesgo.mapas.ValoracionExposicionDTO;
import atc.riesgos.model.repository.MatrizRiesgoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

    public MapaInherenteDTO mapaInherente1() {
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

        PerfilRiesgoInherenteDTO perfilRiesgoInherenteDTO = new PerfilRiesgoInherenteDTO(
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

        return new MapaInherenteDTO(valoracionesExposicionDTO, perfilRiesgoInherenteDTO);
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


    public Object[][] mapaInherente2() {
        Object[][] matrix = new Object[8][8];

        // Llenar la matriz con valores por defecto o vacíos
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                matrix[i][j] = "";
            }
        }

        // Llamar a los servicios necesarios y llenar la matriz
        // LLENA COLUMNA 1 Y 2 CON DATOS DE "PROB" Y "PROBABILIDAD"
        String sqlProb = "SELECT des_campo_c as prob, des_nombre as probabilidad FROM riesgos.tbl_tabla_descripcion_matriz_riesgo  WHERE des_tabla_id = 2 ORDER BY des_id ASC LIMIT 5";
        List<Object[]> resultsProb = jdbcTemplate.query(sqlProb, (rs, rowNum) -> new Object[]{rs.getString("prob"), rs.getString("probabilidad")});

        for (int i = 0; i < 5; i++) {
            Object[] row = resultsProb.get(i);
            matrix[i][0] = row[0];
            matrix[i][1] = row[1];
        }

        // LLENA TOTALES DE PROBABILIDAD E IMPACTO

        List<Object[]> results = matrizRiesgoRepository.getListProbImp(); // Obtiene lista de probabilidad e impacto
//        StringBuilder sql = new StringBuilder();
//        sql.append("SELECT ");
//        sql.append("(SELECT CAST(des_campo_a AS int) as prob FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_id = rie_probabilidad_id), ");
//        sql.append("(SELECT CAST(des_campo_a AS int) as imp FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_id = rie_impacto_id) ");
//        sql.append("FROM riesgos.tbl_matriz_riesgo r ");
//        sql.append("WHERE rie_delete=false ");
//
//        if (procesoId != null) {
//            sql.append("AND rie_proceso_id = :procesoId ");
//        }
//
//        Query query = entityManager.createNativeQuery(sql.toString());
//
//        if (procesoId != null) {
//            query.setParameter("procesoId", procesoId);
//        }
//
//        List<Object[]> results  = query.getResultList();

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

}
