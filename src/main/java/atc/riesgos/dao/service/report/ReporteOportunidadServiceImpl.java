package atc.riesgos.dao.service.report;

import atc.riesgos.model.dto.report.ciro.oportunidades.FiltroReporteConfigOportunidad;
import atc.riesgos.model.repository.EventoRiesgoRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReporteOportunidadServiceImpl implements ReporteOportunidadService {

    @Autowired
    EventoRiesgoRepository eventoRiesgoRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;


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



}
