package atc.riesgos.dao.service.report;

import atc.riesgos.model.dto.Reporte.oportunidades.FiltroReporteConfigOportunidad;
import atc.riesgos.model.dto.Reporte.oportunidades.mapas.MapaInherenteDTO;
import atc.riesgos.model.dto.Reporte.oportunidades.mapas.MapaInherenteOportunidadDTO;
import atc.riesgos.model.dto.Reporte.oportunidades.mapas.MapaResumenDTO;
import atc.riesgos.model.dto.Reporte.oportunidades.mapas.conOportunidades.MapaInherenteConOportunidadesDTO;
import atc.riesgos.model.dto.Reporte.oportunidades.mapas.conOportunidades.MapaInherenteConOportunidadesListDTO;
import atc.riesgos.model.repository.EventoRiesgoRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReporteOportunidadServiceImpl implements ReporteOportunidadService {

    @Autowired
    EventoRiesgoRepository eventoRiesgoRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PersistenceContext
    private EntityManager entityManager;

    // REPORTE DINAMICO EVENTO DE OPORTUNIDAD
    @Override
    public byte[] reporteConfigOportunidad(FiltroReporteConfigOportunidad filter)  {
        List<Map<String, Object>> results = getDataOportunidadColumns(filter);

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Matriz de oportunidades");

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
                FiltroReporteConfigOportunidad.DataColumn column = filter.getDataColumns().get(colIdx);
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
                for (FiltroReporteConfigOportunidad.DataColumn column : filter.getDataColumns()) {
                    Cell cell = row.createCell(colIdx++);
                    Object value = result.get(column.getLabel());
                    cell.setCellValue(value != null ? value.toString() : "");
                    cell.setCellStyle(cellStyle);
                }
            }

            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el reporte oportunidad", e);
        }
    }

    private List<Map<String, Object>> getDataOportunidadColumns(FiltroReporteConfigOportunidad filter) {
        StringBuilder query = new StringBuilder("SELECT ");
        // Construye las columnas dinámicamente
        List<String> columns = filter.getDataColumns().stream()
                .map(column -> {
                    switch (column.getId()) {
                        case 1:
                            return "COALESCE((SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion d WHERE o.opo_area_id = d.des_id), '') AS \"" + column.getLabel() + "\"";
                        case 2:
                            return "COALESCE((SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion d WHERE o.opo_unidad_id = d.des_id), '') AS \"" + column.getLabel() + "\"";
                        case 3:
                            return "COALESCE((SELECT d.des_clave FROM riesgos.tbl_tabla_descripcion d WHERE o.opo_proceso_id = d.des_id), '') AS \"" + column.getLabel() + "\"";
                        case 4:
                            return "COALESCE((SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion d WHERE o.opo_proceso_id = d.des_id), '') AS \"" + column.getLabel() + "\"";
                        case 5:
                            return "COALESCE((SELECT d.des_campo_a FROM riesgos.tbl_tabla_descripcion d WHERE o.opo_procedimiento_id = d.des_id), '') AS \"" + column.getLabel() + "\"";
                        case 6:
                            return "COALESCE((SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion d WHERE o.opo_dueno_cargo_id = d.des_id), '') AS \"" + column.getLabel() + "\"";
                        case 7:
                            return "COALESCE((SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion d WHERE o.opo_responsable_cargo_id = d.des_id), '') AS \"" + column.getLabel() + "\"";
                        case 8:
                            return "COALESCE(TO_CHAR(o.opo_fecha_evaluacion, 'DD/MM/YYYY'), '') AS \"" + column.getLabel() + "\"";
                        case 9:
                            return "COALESCE(opo_codigo, '') AS \"" + column.getLabel() + "\"";
                        case 10:
                            return "COALESCE(opo_definicion, '') AS \"" + column.getLabel() + "\"";
                        case 11:
                            return "COALESCE(opo_causa, '') AS \"" + column.getLabel() + "\"";
                        case 12:
                            return "COALESCE(opo_consecuencia, '') AS \"" + column.getLabel() + "\"";
                        case 13:
                            return "COALESCE('Oportunidad de ' || opo_definicion || ' debido a ' || opo_causa || ' puede ocasionar ' || opo_consecuencia, '') AS \"" + column.getLabel() + "\"";
                        case 14:
                            return "COALESCE(opo_factor, '') AS \"" + column.getLabel() + "\"";
                        case 15:
                            return "COALESCE((SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion_matriz_oportunidad d WHERE o.opo_grupo_interes_id = d.des_id), '') AS \"" + column.getLabel() + "\"";
                        case 16:
                            return "COALESCE((SELECT d.des_campo_d FROM riesgos.tbl_tabla_descripcion_matriz_riesgo d WHERE o.opo_probabilidad_id = d.des_id), '') AS \"" + column.getLabel() + "\"";
                        case 17:
                            return "COALESCE((SELECT d.des_campo_a FROM riesgos.tbl_tabla_descripcion_matriz_riesgo d WHERE o.opo_probabilidad_id = d.des_id), '') AS \"" + column.getLabel() + "\"";
                        case 18:
                            return "COALESCE((SELECT d.des_campo_g || '%' FROM riesgos.tbl_tabla_descripcion_matriz_riesgo d WHERE o.opo_probabilidad_id = d.des_id), '') AS \"" + column.getLabel() + "\"";
                        case 19:
                            return "COALESCE((SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion_matriz_riesgo d WHERE o.opo_probabilidad_id = d.des_id), '') AS \"" + column.getLabel() + "\"";
                        case 20:
                            return "COALESCE((SELECT d.des_campo_c FROM riesgos.tbl_tabla_descripcion_matriz_oportunidad d WHERE o.opo_impacto_opor_id = d.des_id), '') AS \"" + column.getLabel() + "\"";
                        case 21:
                            return "COALESCE((SELECT d.des_campo_a FROM riesgos.tbl_tabla_descripcion_matriz_oportunidad d WHERE o.opo_impacto_opor_id = d.des_id), '') AS \"" + column.getLabel() + "\"";
                        case 22:
                            return "COALESCE((SELECT d.des_campo_d || '%' FROM riesgos.tbl_tabla_descripcion_matriz_oportunidad d WHERE o.opo_impacto_opor_id = d.des_id), '') AS \"" + column.getLabel() + "\"";
                        case 23:
                            return "COALESCE((SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion_matriz_oportunidad d WHERE o.opo_impacto_opor_id = d.des_id), '') AS \"" + column.getLabel() + "\"";
                        case 24:
                            return "COALESCE(CAST(riesgos.fc_calcula_valoracion_riesgo(cast((SELECT d.des_campo_a FROM riesgos.tbl_tabla_descripcion_matriz_riesgo d WHERE o.opo_probabilidad_id = d.des_id) AS integer), cast((SELECT d.des_campo_a FROM riesgos.tbl_tabla_descripcion_matriz_oportunidad d WHERE o.opo_impacto_opor_id = d.des_id) AS integer)) AS TEXT), '') AS \"" + column.getLabel() + "\"";
                        case 25:
                            return "COALESCE((SELECT CAST(des_nombre AS TEXT) FROM riesgos.tbl_tabla_descripcion_matriz_oportunidad WHERE des_tabla_id = 4 AND CAST(des_campo_a AS INTEGER) = riesgos.fc_calcula_valoracion_riesgo(cast((SELECT d.des_campo_a FROM riesgos.tbl_tabla_descripcion_matriz_riesgo d WHERE o.opo_probabilidad_id = d.des_id) AS integer), cast((SELECT d.des_campo_a FROM riesgos.tbl_tabla_descripcion_matriz_oportunidad d WHERE o.opo_impacto_opor_id = d.des_id) AS integer))), '') AS \"" + column.getLabel() + "\"";
                        case 26:
                            return "COALESCE(CASE WHEN opo_controles_tiene THEN 'SI' ELSE 'NO' END, '') AS \"" + column.getLabel() + "\"";
                        case 27:
                            return "COALESCE((SELECT string_agg(format('%s. %s', rn, campo), E'\n' ORDER BY rn) AS lista FROM (SELECT elem->>'descripcion' AS campo, row_number() OVER (ORDER BY elem->>'nroControl') AS rn FROM jsonb_array_elements(o.opo_controles::jsonb) AS elem) sub), '') AS \"" + column.getLabel() + "\"";
                        case 28:
                            return "COALESCE((SELECT d.des_campo_a FROM riesgos.tbl_tabla_descripcion_matriz_oportunidad d WHERE o.opo_fortaleza_id = d.des_id), '') AS \"" + column.getLabel() + "\"";
                        case 29:
                            return "COALESCE((SELECT d.des_campo_a FROM riesgos.tbl_tabla_descripcion_matriz_oportunidad d WHERE o.opo_fortaleza_id = d.des_id) || '. ' || (SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion_matriz_oportunidad d WHERE o.opo_fortaleza_id = d.des_id), '') AS \"" + column.getLabel() + "\"";
                        case 30:
                            return "COALESCE(jsonb_array_length(o.opo_planes_accion::jsonb), 0) AS \"" + column.getLabel() + "\"";
                        case 31:
                            return "COALESCE((SELECT string_agg(format('%s. %s', rn, campo), E'\n' ORDER BY rn) AS lista FROM (SELECT elem->>'estrategia' AS campo, row_number() OVER (ORDER BY elem->>'nroPlan') AS rn FROM jsonb_array_elements(o.opo_planes_accion::jsonb) AS elem) sub), '') AS \"" + column.getLabel() + "\"";
                        case 32:
                            return "COALESCE((SELECT string_agg(format('%s. %s', rn, campo), E'\n' ORDER BY rn) AS lista FROM (SELECT elem->>'descripcion' AS campo, row_number() OVER (ORDER BY elem->>'nroPlan') AS rn FROM jsonb_array_elements(o.opo_planes_accion::jsonb) AS elem) sub), '') AS \"" + column.getLabel() + "\"";
                        case 33:
                            return "COALESCE((SELECT string_agg(format('%s. %s', rn, campo), E'\n' ORDER BY rn) AS lista FROM (SELECT elem->>'cargo' AS campo, row_number() OVER (ORDER BY elem->>'nroPlan') AS rn FROM jsonb_array_elements(o.opo_planes_accion::jsonb) AS elem) sub), '') AS \"" + column.getLabel() + "\"";
                        case 34:
                            return "COALESCE((SELECT string_agg(format('%s. %s', rn, campo), E'\n' ORDER BY rn) AS lista FROM (SELECT elem->>'fechaImpl' AS campo, row_number() OVER (ORDER BY elem->>'nroPlan') AS rn FROM jsonb_array_elements(o.opo_planes_accion::jsonb) AS elem) sub), '') AS \"" + column.getLabel() + "\"";
                        case 35:
                            return "COALESCE(jsonb_array_length(o.opo_planes_accion::jsonb), 0) AS \"" + column.getLabel() + "\"";
                        case 36:
                            return "COALESCE((SELECT COUNT(*) FROM jsonb_array_elements(o.opo_planes_accion::jsonb) AS elem WHERE elem->>'estado' = 'No iniciado'), 0) AS \"" + column.getLabel() + "\"";
                        case 37:
                            return "COALESCE((SELECT COUNT(*) FROM jsonb_array_elements(o.opo_planes_accion::jsonb) AS elem WHERE elem->>'estado' = 'En proceso'), 0) AS \"" + column.getLabel() + "\"";
                        case 38:
                            return "COALESCE((SELECT COUNT(*) FROM jsonb_array_elements(o.opo_planes_accion::jsonb) AS elem WHERE elem->>'estado' = 'Concluido'), 0) AS \"" + column.getLabel() + "\"";
                        case 39:
                            return "COALESCE((CASE WHEN jsonb_array_length(o.opo_planes_accion::jsonb) > 0 THEN CONCAT(ROUND((SELECT COUNT(*) FROM jsonb_array_elements(o.opo_planes_accion::jsonb) AS elem WHERE elem->>'estado' = 'Concluido') * 100.0 / jsonb_array_length(o.opo_planes_accion::jsonb)), '%') ELSE '0%' END), '') AS \"" + column.getLabel() + "\"";
                        case 40:
                            return "COALESCE(opo_planes_accion_estado, '') AS \"" + column.getLabel() + "\"";
                        case 41:
                            return "COALESCE((SELECT string_agg(format('%s. %s', rn, campo), E'\n' ORDER BY rn) AS lista FROM (SELECT elem->>'fechaSeg' AS campo, row_number() OVER (ORDER BY elem->>'nroPlan') AS rn FROM jsonb_array_elements(o.opo_planes_accion::jsonb) AS elem) sub), '') AS \"" + column.getLabel() + "\"";
                        case 42:
                            return "COALESCE((SELECT string_agg(format('%s. %s', rn, campo), E'\n' ORDER BY rn) AS lista FROM (SELECT elem->>'comenConcluido' AS campo, row_number() OVER (ORDER BY elem->>'nroPlan') AS rn FROM jsonb_array_elements(o.opo_planes_accion::jsonb) AS elem) sub), '') AS \"" + column.getLabel() + "\"";
                        case 43:
                            return "COALESCE((SELECT string_agg(format('%s. %s', rn, campo), E'\n' ORDER BY rn) AS lista FROM (SELECT elem->>'comenEnProceso' AS campo, row_number() OVER (ORDER BY elem->>'nroPlan') AS rn FROM jsonb_array_elements(o.opo_planes_accion::jsonb) AS elem) sub), '') AS \"" + column.getLabel() + "\"";
                        case 44:
                            return "COALESCE((SELECT string_agg(format('%s. %s', rn, campo), E'\n' ORDER BY rn) AS lista FROM (SELECT elem->>'fechaAccion' AS campo, row_number() OVER (ORDER BY elem->>'nroPlan') AS rn FROM jsonb_array_elements(o.opo_planes_accion::jsonb) AS elem) sub), '') AS \"" + column.getLabel() + "\"";
                        default:
                            return null;
                    }
                })
                .filter(column -> column != null)
                .collect(Collectors.toList());

        query.append(String.join(", ", columns));
        query.append(" FROM riesgos.tbl_matriz_oportunidad o ");
        query.append("WHERE o.opo_fecha_evaluacion >= ? AND o.opo_fecha_evaluacion <= ? ORDER BY o.opo_id ASC");

        return jdbcTemplate.queryForList(query.toString(), filter.getDataFilter().getFechaDesde(), filter.getDataFilter().getFechaHasta());
    }

    // Mapa de oportunidad
    public Object[][] mapaInherente(Long procesoId) {
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
        String baseSql = "SELECT \n" +
                "(SELECT CAST(des_campo_a AS int) as prob FROM riesgos.tbl_tabla_descripcion_matriz_riesgo WHERE des_id = opo_probabilidad_id), \n" +
                "(SELECT CAST(des_campo_a AS int) as imp FROM riesgos.tbl_tabla_descripcion_matriz_oportunidad WHERE des_id = opo_impacto_opor_id)\n" +
                "FROM riesgos.tbl_matriz_oportunidad\n" +
                "WHERE opo_delete = FALSE";

        if (procesoId != null) {
            baseSql += " AND opo_proceso_id = :procesoId ";
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

    public MapaInherenteOportunidadDTO mapaInherenteOportunidad (Long procesoId){
        Object[][] matrizInherente = mapaInherente(procesoId);

        // Vaciado de la matriz inherente a DTO
        List<MapaInherenteDTO> listMapaInherenteDTO = new ArrayList<>();
        for (Object[] row : matrizInherente) {
            if (row != null) {
                MapaInherenteDTO dto = new MapaInherenteDTO(
                        row[0].toString(), row[1].toString(), row[2].toString(), row[3].toString(), row[4].toString(), row[5].toString(), row[6].toString(), row[7].toString()
                );
                listMapaInherenteDTO.add(dto);
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

        // Totales
        matrizResumen[6][0] = "Total";
        matrizResumen[6][1] = "";
        matrizResumen[6][2] = "";
        matrizResumen[6][3] = String.valueOf(
                Integer.parseInt((String) matrizResumen[1][3]) + Integer.parseInt((String) matrizResumen[2][3]) + Integer.parseInt((String) matrizResumen[3][3]) + Integer.parseInt((String) matrizResumen[4][3]) + Integer.parseInt((String) matrizResumen[5][3])
        );

        // Vaciado de la matriz resumen a DTO
        List<MapaResumenDTO> listMapaResumenDTO = new ArrayList<>();
        for (Object[] row : matrizResumen) {
            if (row != null) {
                MapaResumenDTO dto = new MapaResumenDTO(
                        row[0].toString(), row[1].toString(), row[2].toString(), row[3].toString()
                );
                listMapaResumenDTO.add(dto);
            }
        }

        // Crear y devolver el DTO combinado
        return new MapaInherenteOportunidadDTO(listMapaInherenteDTO, listMapaResumenDTO);
    }


    // Mapa inherente con Riesgos
    public MapaInherenteConOportunidadesListDTO getMapaInherenteConOportunidades(Long procesoId) {
        MapaInherenteConOportunidadesListDTO finalListDTO = new MapaInherenteConOportunidadesListDTO();

        // Definir los IDs de probabilidad e impacto
        int[] probabilidadIds = {5, 6, 7, 8, 9};  // Probabilidad 5 a 1 (ids en tabla parametrizada de riesgos)
        int[] impactoIds = {39, 38, 37, 36, 35};  // Impacto de 1 a 5 (ids en tabla parametrizada de oportunidades)

        // Iterar sobre cada probabilidad
        for (int probId : probabilidadIds) {
            MapaInherenteConOportunidadesDTO dto = new MapaInherenteConOportunidadesDTO();

            // Iterar sobre cada impacto para la probabilidad actual
            for (int i = 0; i < impactoIds.length; i++) {
                int impId = impactoIds[i];
                String sql = "SELECT opo_id, opo_codigo FROM riesgos.tbl_matriz_oportunidad WHERE opo_probabilidad_id = ? AND opo_impacto_opor_id = ?";
                List<Object> params = new ArrayList<>(Arrays.asList(probId, impId));

                if (procesoId != null) {
                    sql += " AND opo_proceso_id = ?";
                    params.add(procesoId);
                }

                List<String> results = jdbcTemplate.query(sql, params.toArray(), (rs, rowNum) ->
                        rs.getLong("opo_id") + ", " + rs.getString("opo_codigo")
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
            finalListDTO.getListMapaInherenteConOportunidadesDTO().add(dto);
        }

        return finalListDTO;
    }
}
