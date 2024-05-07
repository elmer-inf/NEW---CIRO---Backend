package atc.riesgos.dao.service.report;


import atc.riesgos.model.dto.report.ciro.DatesForReport;
import atc.riesgos.model.dto.report.ciro.eventos.FiltroReporteEvento;
import atc.riesgos.model.dto.report.ciro.eventos.ReporteEventoGralDTO;
import atc.riesgos.model.entity.EventoRiesgo;
import atc.riesgos.model.repository.EventoRiesgoRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static atc.riesgos.util.JPQL.byIdEventoReportB;


@Service
public class ReporteEventoServiceImpl implements ReporteEventoService {

    @Autowired
    EventoRiesgoRepository eventoRiesgoRepository;

    @Override
    public byte[] reporteEventoExcel(FiltroReporteEvento filter) {

        List<ReporteEventoGralDTO> eventos = eventoRiesgoRepository.getReporteGralEvento(filter.getFechaIni(), filter.getFechaDesc(), filter.getEstadoEvento());

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Eventos de Riesgo");

            // Estilo para cabecera: texto en negrita, fondo verde y texto blanco
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            headerCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
            headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerCellStyle.setBorderTop(BorderStyle.THIN);
            headerCellStyle.setBorderBottom(BorderStyle.THIN);
            headerCellStyle.setBorderLeft(BorderStyle.THIN);
            headerCellStyle.setBorderRight(BorderStyle.THIN);

            // Creando el encabezado
            String[] headers = {"Código", "Descripción", "Estado del evento", "Fecha descubrimiento", "Fecha fin"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerCellStyle);
            }

            // Llenado de datos y aplicando bordes
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            for (ReporteEventoGralDTO evento : eventos) {
                Row row = sheet.createRow(sheet.getLastRowNum() + 1);
                for (int i = 0; i < headers.length; i++) {
                    Cell cell = row.createCell(i);
                    cell.setCellValue(getCellValue(evento, i));
                    cell.setCellStyle(cellStyle);
                }
            }

            // Establecer ancho fijo 40 para la columna "Descripción"
            sheet.setColumnWidth(1, 40 * 256);

            // Ancho automatico de las columnas restantes
            for (int i = 0; i < headers.length; i++) {
                if (i != 1) { // Excluir la columna 2
                    sheet.autoSizeColumn(i);
                }
            }

            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el reporte", e);
        }
    }

    private String getCellValue(ReporteEventoGralDTO evento, int index) {
        switch (index) {
            case 0:
                return evento.getCodigo();
            case 1:
                return evento.getDescripcion();
            case 2:
                return evento.getEstadoEvento();
            case 3:
                return evento.getFechaDesc() != null ? evento.getFechaDesc().toString() : "";
            case 4:
                return evento.getFechaFin() != null ? evento.getFechaFin().toString() : "";
            default:
                return "";
        }
    }


    public List<ReporteEventoGralDTO> getReportEvento(FiltroReporteEvento filter) {
        return eventoRiesgoRepository.getReporteGralEvento(filter.getFechaIni(), filter.getFechaDesc(), filter.getEstadoEvento());
    }

}
