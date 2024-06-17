package atc.riesgos.dao.service.report;

import atc.riesgos.model.dto.MatrizRiesgo.mapas.MapaInherenteDTO;
import atc.riesgos.model.dto.MatrizRiesgo.mapas.PerfilRiesgoInherenteDTO;
import atc.riesgos.model.dto.MatrizRiesgo.mapas.ValoracionExposicionDTO;
import atc.riesgos.model.repository.MatrizRiesgoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteRiesgoServiceImpl implements ReporteRiesgoService {

    @Autowired
    MatrizRiesgoRepository matrizRiesgoRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public MapaInherenteDTO getValoracionExposicionInherente() {
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


    public Object[][] createRiskMatrix() {
        Object[][] matrix = new Object[8][8];

        // Llenar la matriz con valores por defecto o vacíos
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                matrix[i][j] = ""; // Inicializa con un valor vacío o adecuado
            }
        }

        // Llamar a los servicios necesarios y llenar la matriz
        fillMatrixWithData(matrix);

        return matrix;
    }

    private void fillMatrixWithData(Object[][] matrix) {
        // Llenar las primeras dos filas con datos de servicios "probabilidad - prob" y "probabilidad"
        for (int i = 0; i < 5; i++) {
            matrix[i][0] = "Probabilidad - prob " + (i + 1);  // Datos de "probabilidad - prob"
            matrix[i][1] = "Probabilidad " + (i + 1);  // Datos de "probabilidad"
        }

        // Llenar las columnas con valores numéricos aleatorios y realizar las sumas
        for (int i = 0; i < 5; i++) {
            for (int j = 2; j < 7; j++) {
                matrix[i][j] = (int) (Math.random() * 100); // Datos numéricos aleatorios
            }
        }

        // Establecer valores estáticos para filas específicas
        matrix[5][0] = "Probabilidad";
        matrix[6][1] = "Impacto en USD";
        matrix[7][0] = "TOTAL IMPACTO";

        // Realizar cálculos de sumas
        calculateSums(matrix);
    }

    private void calculateSums(Object[][] matrix) {
        // Calcular sumas horizontales para las filas con datos numéricos
        for (int i = 0; i < 5; i++) {
            int sum = 0;
            for (int j = 2; j < 7; j++) {
                if (matrix[i][j] instanceof Integer) {  // Asegurar que el valor sea un Integer
                    sum += (Integer) matrix[i][j];
                }
            }
            matrix[i][7] = sum;  // Asignar la suma calculada
        }

        // Calcular sumas verticales para las columnas con datos numéricos
        for (int j = 2; j < 7; j++) {
            int sum = 0;
            for (int i = 0; i < 5; i++) {
                if (matrix[i][j] instanceof Integer) {  // Asegurar que el valor sea un Integer
                    sum += (Integer) matrix[i][j];
                }
            }
            matrix[7][j] = sum;  // Asignar la suma calculada
        }

        // Calcular el total de impactos sumando las sumas horizontales
        int totalImpact = 0;
        for (int j = 2; j < 8; j++) {
            if (matrix[7][j] instanceof Integer) {  // Asegurar que el valor sea un Integer
                totalImpact += (Integer) matrix[7][j];
            }
        }
        matrix[7][7] = totalImpact;  // Asignar el total de impactos
    }

}
