package atc.riesgos.dao.service.report;

import atc.riesgos.model.dto.report.ciro.eventos.FiltroReporteAuditoria;
import atc.riesgos.model.dto.report.ciro.eventos.FiltroReporteConfigEvento;
import atc.riesgos.model.dto.report.ciro.eventos.FiltroReporteEvento;
import atc.riesgos.model.dto.report.ciro.eventos.ReporteEventoGralDTO;
import atc.riesgos.model.entity.EventoRiesgo;
import atc.riesgos.model.entity.MatrizRiesgo;
import atc.riesgos.model.entity.TablaDescripcion;
import atc.riesgos.model.repository.EventoRiesgoRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReporteEventoServiceImpl implements ReporteEventoService {

    @Autowired
    EventoRiesgoRepository eventoRiesgoRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    // REPORTE EVENTO DE RIESGO
    public byte[] reporteEventoExcel(FiltroReporteEvento filter) {

        List<ReporteEventoGralDTO> eventos = eventoRiesgoRepository.getReporteGralEvento(filter.getFechaDesde(), filter.getFechaHasta(), filter.getEstadoEvento());

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Eventos de Riesgo");

            // Estilo para cabecera: texto en negrita, fondo azul claro
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.BLACK.getIndex());
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            headerCellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE .getIndex());
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
        return eventoRiesgoRepository.getReporteGralEvento(filter.getFechaDesde(), filter.getFechaHasta(), filter.getEstadoEvento());
    }


    @Override
    // REPORTE AUDITORIA EXTERNA
    public byte[] reporteAuditoriaExtExcel(FiltroReporteAuditoria filter) {
        List<EventoRiesgo> eventos = eventoRiesgoRepository.getReporteAuditoriaExterna(filter.getFechaDesde(), filter.getFechaHasta());

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Auditoría Externa");

            //Define un Formateador de Fechas:
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

            Locale locale = new Locale("es", "ES");
            SimpleDateFormat formatMesAnio = new SimpleDateFormat("MMM-yy", locale);
            SimpleDateFormat formatAnio = new SimpleDateFormat("yyyy", locale);

            // Crear un estilo de cabecera con texto en negrita, fondo azul claro y con salto de línea
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
            headerCellStyle.setWrapText(true); // Permite saltos de línea

            // Define las cabeceras manualmente con los campos deseados: 65 COLUMNAS
            String[] headers = {
                    "Código del Evento", "Fecha del Inicio del Evento", "Hora de Inicio", "CAMPO AUXILIAR MES-AÑO INICIO", "Hora de Finalización", "CAMPO AUXILIAR MES-AÑO FIN", "CAMPO AUXILIAR AÑO FIN", "Agencia", "(Lugar) Oficina", "Unidad",
                    "Descripción Resumida del Evento", "Categoría (del Evento)", "Descripción Clase de Evento", "Tipo de Evento", "Clase de Evento", "Evento Crítico", "Detalle Evento Crítico", "Codigo Riesgo Relacionado", "Descripcion Riesgo", "Procedimiento",
                    "Proceso Critico", "Líneas de Negocio (Nivel 3)", "Líneas de Negocio ASFI", "Operación (Producto o Servicio afectado)", "Tipo de Servicio", "Descripción Operación, Producto o Servicio Afectado", "Operaciones ASFI", "Entidad o Comercio Afectado", "Acciones efectuadas por el área responsable de la incidencia", "Estado del Evento",
                    "Moneda", "Pérdida por Riesgo Operativo (Valor Contable) Monto de Pérdida", "Monto de Pérdida por Riesgo Operativo USD", "Gastos Asociados a la Pérdida (BS)", "Monto Total Recuperados", "Cobertura de Seguro SI / NO", "Póliza de Seguro asociada", "Monto Recuperado por Coberturas de Seguros  BS", "Pérdida por Riesgo Operativo (Valor de Mercado)", "Recuperación de Activo",
                    "Monto Total Final de la Perdida Expresado en USD", "Fecha de Registro Contable del evento de Pérdida", "Cuentas Contables Afectadas", "Nombre cuenta", "Fuente de información", "Impacto o Severidad", "Riesgo Relacionado", "Cargo (s) Involucrados", "Canal", "Efectos de Pérdida",
                    "Proceso Crítico", "Detalle Proceso Crítico", "Operativo", "Seguridad de la Información", "Liquidez y Mercado", "LGI FT y/o DP", "Fraude con medios de Pago Electrónico", "Legal y Regulatorio", "Reputacional", "Cumplimiento",
                    "Estratégico", "Gobierno Corporativo", "Código Inicial", "Sub Categorización", "Trimestre"
            };

            // Creando el encabezado con ancho fijo
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerCellStyle);

                // Establece el mismo ancho fijo para cada columna
                sheet.setColumnWidth(i, 4000);
            }

            // Estilo para las celdas de datos
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);

            // Llena los datos filtrando los campos seleccionados
            for (EventoRiesgo evento : eventos) {
                Row row = sheet.createRow(sheet.getLastRowNum() + 1);
                int colIndex = 0;

                // Columna A-J   1:10
                row.createCell(colIndex++).setCellValue(evento.getCodigo() != null ? evento.getCodigo() : "");
                row.createCell(colIndex++).setCellValue(evento.getFechaIni() != null ? dateFormat.format(evento.getFechaIni()) : "");
                row.createCell(colIndex++).setCellValue(evento.getHoraIni() != null ? timeFormat.format(evento.getHoraIni()) : "");
                row.createCell(colIndex++).setCellValue(evento.getFechaIni() != null ? formatMesAnio.format(evento.getFechaIni()) : "");
                row.createCell(colIndex++).setCellValue(evento.getHoraFin() != null ? timeFormat.format(evento.getHoraFin()) : "");
                row.createCell(colIndex++).setCellValue(evento.getFechaFin() != null ? formatMesAnio.format(evento.getFechaFin()) : "");
                row.createCell(colIndex++).setCellValue(evento.getFechaFin() != null ? formatAnio.format(evento.getFechaFin()) : "");
                row.createCell(colIndex++).setCellValue(evento.getAgenciaId() != null ? evento.getAgenciaId().getNombre(): "");
                row.createCell(colIndex++).setCellValue(evento.getCiudadId() != null ? evento.getCiudadId().getNombre() : "");
                row.createCell(colIndex++).setCellValue(evento.getUnidadId() != null ? evento.getUnidadId().getNombre() : "");

                // Columna K-T   11:20
                row.createCell(colIndex++).setCellValue(evento.getDescripcion() != null ? evento.getDescripcion() : "");
                row.createCell(colIndex++).setCellValue(evento.getTipoEvento()!= null ? evento.getTipoEvento() : "");
                row.createCell(colIndex++).setCellValue(eventoRiesgoRepository.getDescripcionTipoEvento(evento.getTipoEvento()!= null ? evento.getTipoEvento() : ""));
                row.createCell(colIndex++).setCellValue(evento.getTipoEventoPerdidaId() != null ? evento.getTipoEventoPerdidaId().getNombre() : "");
                row.createCell(colIndex++).setCellValue(evento.getClaseEventoId() != null ? evento.getClaseEventoId().getNombre() : "");
                row.createCell(colIndex++).setCellValue(evento.getEventoCritico() != null ? evento.getEventoCritico() : "");
                row.createCell(colIndex++).setCellValue(evento.getDetalleEventoCritico() != null ? evento.getDetalleEventoCritico() : "");
                // Es para obtener los valores de las dos columnas que contienen una lista:
                StringBuilder riesgosCodigo = new StringBuilder();
                StringBuilder riesgosDetalles = new StringBuilder();

                if (evento.getRiesgoRelacionado() != null && !evento.getRiesgoRelacionado().isEmpty()) {
                    for (MatrizRiesgo riesgo : evento.getRiesgoRelacionado()) {
                        riesgosCodigo.append(riesgo.getCodigo()).append("_");
                        riesgosDetalles.append(riesgo.getDefinicion()).append(" debido a ").append(riesgo.getCausa()).append(" puede ocasionar ").append(riesgo.getConsecuencia()).append("_");
                    }
                }
                // Asignar todos los códigos
                Cell cellCodigo = row.createCell(colIndex++);
                cellCodigo.setCellValue(riesgosCodigo.length() > 0 ? riesgosCodigo.substring(0, riesgosCodigo.length() - 1): ""); // Quita el último "_"

                // Asignar todos los detalles
                Cell cellDetalles = row.createCell(colIndex++);
                cellDetalles.setCellValue(riesgosDetalles.length() > 0 ? riesgosDetalles.substring(0, riesgosDetalles.length() - 1): ""); // Quita el último "_"

                row.createCell(colIndex++).setCellValue(evento.getProcedimientoId() != null ? evento.getProcedimientoId().getCampoA() : "");

                // Columna U:AD     21:30
                row.createCell(colIndex++).setCellValue(evento.getProcesoId() != null ? evento.getProcesoId().getCampoA() : "");
                row.createCell(colIndex++).setCellValue(evento.getLineaNegocio() != null ? evento.getLineaNegocio() : "");
                row.createCell(colIndex++).setCellValue(evento.getLineaAsfiId() != null ? evento.getLineaAsfiId().getNombre() : "");
                row.createCell(colIndex++).setCellValue(evento.getOpeProSerId() != null ? evento.getOpeProSerId().getNombre() : "");
                row.createCell(colIndex++).setCellValue(evento.getTipoServicioId() != null ? evento.getTipoServicioId().getNombre() : "");
                row.createCell(colIndex++).setCellValue(evento.getDescServicioId() != null ? evento.getDescServicioId().getNombre() : "");
                row.createCell(colIndex++).setCellValue(evento.getOperacionId() != null ? evento.getOperacionId().getNombre() : "");
                row.createCell(colIndex++).setCellValue(
                        !evento.getEntidadAfectada() && !evento.getComercioAfectado()  ? "ATC" :
                                evento.getEntidadAfectada()  && !evento.getComercioAfectado() ? "EIF" :
                                        !evento.getEntidadAfectada()  && evento.getComercioAfectado() ? "Aceptantes" :
                                                "EIF_Aceptantes"
                );
                row.createCell(colIndex++).setCellValue(evento.getDetalleEstado() != null ? evento.getDetalleEstado() : "");
                row.createCell(colIndex++).setCellValue(evento.getEstadoEvento() != null ? evento.getEstadoEvento() : "");

                // Columna AE:AN      31:40
                row.createCell(colIndex++).setCellValue(evento.getMonedaId() != null ? evento.getMonedaId().getClave(): "NA");
                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ?  evento.getMontoPerdida().toString() : "NA");
                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ? String.valueOf(convierteBsADolares(evento)) : "NA");
                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ? evento.getGastoAsociado().toString() :  "NA");
                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ? String.valueOf(calcularMontoTotal(evento)) : "NA");
                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ? (evento.getCoberturaSeguro() ? "SI" : "NO") : "NA");
                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ? evento.getPolizaSeguroId() != null ? evento.getPolizaSeguroId().getNombre() : "NA" : "NA");

                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ? evento.getMontoRecuperadoSeguro() != null? evento.getMontoRecuperadoSeguro().toString() : "0.0" : "NA");
                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ? evento.getPerdidaMercado() != null ? evento.getPerdidaMercado().toString() : "NA" : "NA");
                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ? evento.getRecuperacionActivoId() != null ? evento.getRecuperacionActivoId().getNombre() : "NA" : "NA");

                // Columna AO:AX     41:50
                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ? (String.valueOf(convierteBsADolares(evento) - calcularMontoTotal(evento))) : "NA" );
                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ? evento.getFechaContable() != null ? dateFormat.format(evento.getFechaContable()) : "NA" : "NA");
                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ? evento.getCuentaContableId() != null ? evento.getCuentaContableId().getCodigoAsfi() : "NA" : "NA");
                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ? evento.getCuentaContableId() != null ? evento.getCuentaContableId().getNombre() : "NA" : "NA");
                row.createCell(colIndex++).setCellValue(evento.getFuenteInfId() != null ? evento.getFuenteInfId().getNombre() : "");
                row.createCell(colIndex++).setCellValue(evento.getImpactoId() != null ? evento.getImpactoId().getNombre() : "");

                // Inicializa un StringBuilder para construir el string
                StringBuilder riesgosConcatenados = new StringBuilder();

               // Verifica cada campo y concatena el texto correspondiente si no es null
                if (evento.getOperativoId() != null) {
                    riesgosConcatenados.append("Riesgo Operativo");
                }

                if (evento.getSeguridadId() != null) {
                    if (riesgosConcatenados.length() > 0) {
                        riesgosConcatenados.append("_");
                    }
                    riesgosConcatenados.append("Riesgo Seguridad de la Información");
                }

                if (evento.getLiquidezId() != null) {
                    if (riesgosConcatenados.length() > 0) {
                        riesgosConcatenados.append("_");
                    }
                    riesgosConcatenados.append("Riesgo Liquidez y Mercado");
                }

                if (evento.getLgiId() != null) {
                    if (riesgosConcatenados.length() > 0) {
                        riesgosConcatenados.append("_");
                    }
                    riesgosConcatenados.append("Riesgo LGI FT y/o DP");
                }

                if (evento.getFraudeId() != null) {
                    if (riesgosConcatenados.length() > 0) {
                        riesgosConcatenados.append("_");
                    }
                    riesgosConcatenados.append("Riesgo Fraude con medios de Pago Electrónico");
                }

                if (evento.getLegalId() != null) {
                    if (riesgosConcatenados.length() > 0) {
                        riesgosConcatenados.append("_");
                    }
                    riesgosConcatenados.append("Riesgo Legal y Regulatorio");
                }

                if (evento.getReputacionalId() != null) {
                    if (riesgosConcatenados.length() > 0) {
                        riesgosConcatenados.append("_");
                    }
                    riesgosConcatenados.append("Riesgo Reputacional");
                }

                if (evento.getCumplimientoId() != null) {
                    if (riesgosConcatenados.length() > 0) {
                        riesgosConcatenados.append("_");
                    }
                    riesgosConcatenados.append("Riesgo Cumplimiento");
                }

                if (evento.getEstrategicoId() != null) {
                    if (riesgosConcatenados.length() > 0) {
                        riesgosConcatenados.append("_");
                    }
                    riesgosConcatenados.append("Riesgo Estratégico");
                }

                if (evento.getGobiernoId() != null) {
                    if (riesgosConcatenados.length() > 0) {
                        riesgosConcatenados.append("_");
                    }
                    riesgosConcatenados.append("Riesgo Gobierno Corporativo");
                }
                // Crea la celda en la hoja Excel con el texto concatenado
                row.createCell(colIndex++).setCellValue(riesgosConcatenados.toString());

                //row.createCell(colIndex++).setCellValue(evento.getCargoId() != null ? evento.getCargoId().getNombre() : "");


                // Asigna los nombres de los cargos a la celda, separados por "_"
                String cargos = evento.getCargoId() != null ? evento.getCargoId().stream()
                        .map(TablaDescripcion::getNombre) // Asume que getNombre() devuelve el nombre del cargo
                        .filter(nombre -> nombre != null && !nombre.isEmpty()) // Filtra nombres nulos o vacíos
                        .collect(Collectors.joining("_")) : ""; // Une los nombres con "_"

                row.createCell(colIndex++).setCellValue(cargos);

                row.createCell(colIndex++).setCellValue(evento.getCanalAsfiId() != null ? evento.getCanalAsfiId().getNombre() : "");
                row.createCell(colIndex++).setCellValue(evento.getEfectoPerdidaId() != null ? evento.getEfectoPerdidaId().getNombre() : "");

                // Columna AY:BH     51:60
                row.createCell(colIndex++).setCellValue(evento.getProcesoCriticoAsfi() != null ? evento.getProcesoCriticoAsfi() : 0);
                row.createCell(colIndex++).setCellValue(evento.getProcesoCriticoAsfi() != null ? (evento.getProcesoCriticoAsfi() == 1 ? "Se encuentra definido dentro del Mapa de Procesos, priorizando los Procesos Operativos de acuerdo a la cadena de valor que impacta directamente a los objetivos estratégico de la empresa establecidos en el Plan-GTIC-002-SIST." : "") : "");
                row.createCell(colIndex++).setCellValue(evento.getOperativoId() != null ? evento.getOperativoId().getClave() : "-");
                row.createCell(colIndex++).setCellValue(evento.getSeguridadId() != null ? evento.getSeguridadId().getClave() : "-");
                row.createCell(colIndex++).setCellValue(evento.getLiquidezId() != null ? evento.getLiquidezId().getClave() : "-");
                row.createCell(colIndex++).setCellValue(evento.getLgiId() != null ? evento.getLgiId().getClave() : "-");
                row.createCell(colIndex++).setCellValue(evento.getFraudeId() != null ? evento.getFraudeId().getClave() : "-");
                row.createCell(colIndex++).setCellValue(evento.getLegalId() != null ? evento.getLegalId().getClave() : "-");
                row.createCell(colIndex++).setCellValue(evento.getReputacionalId() != null ? evento.getReputacionalId().getClave() : "-");
                row.createCell(colIndex++).setCellValue(evento.getCumplimientoId() != null ? evento.getCumplimientoId().getClave() : "-");

                // Columna BI:BM     61:65
                row.createCell(colIndex++).setCellValue(evento.getEstrategicoId() != null ? evento.getEstrategicoId().getClave() : "-");
                row.createCell(colIndex++).setCellValue(evento.getGobiernoId() != null ? evento.getGobiernoId().getClave() : "-");
                row.createCell(colIndex++).setCellValue(evento.getCodigoInicial() != null ? evento.getCodigoInicial() : "-");
                row.createCell(colIndex++).setCellValue(evento.getSubcategorizacionId() != null ? evento.getSubcategorizacionId().getNombre() : "-");
                row.createCell(colIndex++).setCellValue(evento.getTrimestre() != null ? evento.getTrimestre() : "-");

                // Aplica el estilo de datos a cada celda de esta fila:
                for (int j = 0; j < colIndex; j++) {
                    row.getCell(j).setCellStyle(cellStyle);
                }

            }
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el reporte de auditoría externa", e);
        }
    }


    @Override
    // REPORTE AUDITORIA INTERNA
    public byte[] reporteAuditoriaIntExcel(FiltroReporteAuditoria filter) {
        List<EventoRiesgo> eventos = eventoRiesgoRepository.getReporteAuditoriaExterna(filter.getFechaDesde(), filter.getFechaHasta());

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Auditoría Interna");

            //Define un Formateador de Fechas:
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

            Locale locale = new Locale("es", "ES");
            SimpleDateFormat formatMesAnio = new SimpleDateFormat("MMM-yy", locale);
            SimpleDateFormat formatAnio = new SimpleDateFormat("yyyy", locale);

            // Crear un estilo de cabecera con texto en negrita, fondo azul claro y con salto de línea
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
            headerCellStyle.setWrapText(true); // Permite saltos de línea

            // Define las cabeceras manualmente con los campos deseados: 65 COLUMNAS
            String[] headers = {
                    "Código del Evento", "Fecha del Inicio del Evento", "Hora de Inicio", "Fecha de Descubrimiento del Evento", "Hora de Descubrimiento", "CAMPO AUXILIAR MES-AÑO INICIO", "CAMPO AUXILIAR AÑO INICIO", "Fecha de Finalización del Evento", "Hora de Finalización", "CAMPO AUXILIAR MES-AÑO FIN",
                    "CAMPO AUXILIAR AÑO FIN", "Agencia", "(Lugar) Oficina", "Unidad", "Descripción Resumida del Evento", "Categoría (del Evento)", "Descripción Clase de Evento", "Tipo de Evento", "Definición", "Subtipo de Evento",
                    "Clase de Evento", "Evento Crítico", "Detalle Evento Crítico", "Factores de Riesgo Operativo", "MacroProceso", "Codigo Riesgo Relacionado", "Descripcion Riesgo", "Procedimiento", "Proceso Critico", "Líneas de Negocio (Nivel 3)",
                    "Líneas de Negocio ASFI", "Operación (Producto o Servicio afectado)", "Tipo de Servicio", "Descripción Operación, Producto o Servicio Afectado", "Operaciones ASFI", "Entidad o Comercio Afectado", "Acciones efectuadas por el área responsable de la incidencia", "Estado del Evento", "Moneda", "Pérdida por Riesgo Operativo (Valor Contable) Monto de Pérdida",
                    "Monto de Pérdida por Riesgo Operativo USD", "Gastos Asociados a la Pérdida (BS)", "Monto Total Recuperados", "Cobertura de Seguro SI / NO", "Póliza de Seguro asociada", "Monto Recuperado por Coberturas de Seguros  BS", "Pérdida por Riesgo Operativo (Valor de Mercado)", "Recuperación de Activo", "Monto Total Final de la Perdida Expresado en USD", "Fecha de Registro Contable del evento de Pérdida",
                    "Cuentas Contables Afectadas", "Nombre cuenta", "Fuente de información", "Impacto o Severidad", "Riesgo Relacionado", "Cargo (s) Involucrados", "Canal", "Efectos de Pérdida", "Proceso Crítico", "Detalle Proceso Crítico",
                    "Operativo", "Seguridad de la Información", "Liquidez y Mercado", "LGI FT y/o DP", "Fraude con medios de Pago Electrónico", "Legal y Regulatorio", "Reputacional", "Cumplimiento", "Estratégico", "Gobierno Corporativo",
                    "Código Inicial", "Sub Categorización", "Trimestre"
            };

            // Creando el encabezado con ancho fijo
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerCellStyle);

                // Establece el mismo ancho fijo para cada columna
                sheet.setColumnWidth(i, 4000);
            }

            // Estilo para las celdas de datos
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);

            // Llena los datos filtrando los campos seleccionados
            for (EventoRiesgo evento : eventos) {
                Row row = sheet.createRow(sheet.getLastRowNum() + 1);
                int colIndex = 0;

                // Columna A-J   1:10
                row.createCell(colIndex++).setCellValue(evento.getCodigo() != null ? evento.getCodigo() : "");
                row.createCell(colIndex++).setCellValue(evento.getFechaIni() != null ? dateFormat.format(evento.getFechaIni()) : "");
                row.createCell(colIndex++).setCellValue(evento.getHoraIni() != null ? timeFormat.format(evento.getHoraIni()) : "");
                row.createCell(colIndex++).setCellValue(evento.getFechaDesc() != null ? dateFormat.format(evento.getFechaDesc()) : "");
                row.createCell(colIndex++).setCellValue(evento.getHoraDesc() != null ? timeFormat.format(evento.getHoraDesc()) : "");
                row.createCell(colIndex++).setCellValue(evento.getFechaIni() != null ? formatMesAnio.format(evento.getFechaIni()) : "");
                row.createCell(colIndex++).setCellValue(evento.getFechaIni() != null ? formatAnio.format(evento.getFechaIni()) : "");
                row.createCell(colIndex++).setCellValue(evento.getFechaFin() != null ? dateFormat.format(evento.getFechaFin()) : "");
                row.createCell(colIndex++).setCellValue(evento.getHoraFin() != null ? timeFormat.format(evento.getHoraFin()) : "");
                row.createCell(colIndex++).setCellValue(evento.getFechaFin() != null ? formatMesAnio.format(evento.getFechaFin()) : "");

                // Columna K-T   11:20
                row.createCell(colIndex++).setCellValue(evento.getFechaFin() != null ? formatAnio.format(evento.getFechaFin()) : "");
                row.createCell(colIndex++).setCellValue(evento.getAgenciaId() != null ? evento.getAgenciaId().getNombre(): "");
                row.createCell(colIndex++).setCellValue(evento.getCiudadId() != null ? evento.getCiudadId().getNombre() : "");
                row.createCell(colIndex++).setCellValue(evento.getUnidadId() != null ? evento.getUnidadId().getNombre() : "");
                row.createCell(colIndex++).setCellValue(evento.getDescripcion() != null ? evento.getDescripcion() : "");
                row.createCell(colIndex++).setCellValue(evento.getTipoEvento() != null ? evento.getTipoEvento() : "");
                row.createCell(colIndex++).setCellValue(eventoRiesgoRepository.getDescripcionTipoEvento(evento.getTipoEvento()!= null ? evento.getTipoEvento() : ""));
                row.createCell(colIndex++).setCellValue(evento.getTipoEventoPerdidaId() != null ? evento.getTipoEventoPerdidaId().getNombre() : "");
                row.createCell(colIndex++).setCellValue(evento.getTipoEventoPerdidaId() != null ? evento.getTipoEventoPerdidaId().getDescripcion() : "");
                row.createCell(colIndex++).setCellValue(evento.getSubEventoId() != null ? evento.getSubEventoId().getNombre() : "");

                // Columna U:AD     21:30
                row.createCell(colIndex++).setCellValue(evento.getClaseEventoId() != null ? evento.getClaseEventoId().getNombre() : "");
                row.createCell(colIndex++).setCellValue(evento.getEventoCritico() != null ? evento.getEventoCritico() : "");
                row.createCell(colIndex++).setCellValue(evento.getDetalleEventoCritico() != null ? evento.getDetalleEventoCritico() : "");
                row.createCell(colIndex++).setCellValue(evento.getFactorRiesgoId() != null ? evento.getFactorRiesgoId().getNombre() : "");
                row.createCell(colIndex++).setCellValue(evento.getProcesoId()!= null ? evento.getProcesoId().getNombre() : "");
                // Es para obtener los valores de las dos columnas que contienen una lista:
                StringBuilder riesgosCodigo = new StringBuilder();
                StringBuilder riesgosDetalles = new StringBuilder();

                if (evento.getRiesgoRelacionado() != null && !evento.getRiesgoRelacionado().isEmpty()) {
                    for (MatrizRiesgo riesgo : evento.getRiesgoRelacionado()) {
                        riesgosCodigo.append(riesgo.getCodigo()).append("_");
                        riesgosDetalles.append(riesgo.getDefinicion()).append(" debido a ").append(riesgo.getCausa()).append(" puede ocasionar ").append(riesgo.getConsecuencia()).append("_");
                    }
                }
                // Asignar todos los códigos
                Cell cellCodigo = row.createCell(colIndex++);
                cellCodigo.setCellValue(riesgosCodigo.length() > 0 ? riesgosCodigo.substring(0, riesgosCodigo.length() - 1): ""); // Quita el último "_"

                // Asignar todos los detalles
                Cell cellDetalles = row.createCell(colIndex++);
                cellDetalles.setCellValue(riesgosDetalles.length() > 0 ? riesgosDetalles.substring(0, riesgosDetalles.length() - 1): ""); // Quita el último "_"

                row.createCell(colIndex++).setCellValue(evento.getProcedimientoId() != null ? evento.getProcedimientoId().getCampoA() : "");
                row.createCell(colIndex++).setCellValue(evento.getProcesoId() != null ? evento.getProcesoId().getCampoA() : "");
                row.createCell(colIndex++).setCellValue(evento.getLineaNegocio() != null ? evento.getLineaNegocio() : "");

                // Columna AE:AN      31:40
                row.createCell(colIndex++).setCellValue(evento.getLineaAsfiId() != null ? evento.getLineaAsfiId().getNombre() : "");
                row.createCell(colIndex++).setCellValue(evento.getOpeProSerId() != null ? evento.getOpeProSerId().getNombre() : "");
                row.createCell(colIndex++).setCellValue(evento.getTipoServicioId() != null ? evento.getTipoServicioId().getNombre() : "");
                row.createCell(colIndex++).setCellValue(evento.getDescServicioId() != null ? evento.getDescServicioId().getNombre() : "");
                row.createCell(colIndex++).setCellValue(evento.getOperacionId() != null ? evento.getOperacionId().getNombre() : "");
                row.createCell(colIndex++).setCellValue(
                        !evento.getEntidadAfectada() && !evento.getComercioAfectado()  ? "ATC" :
                                evento.getEntidadAfectada()  && !evento.getComercioAfectado() ? "EIF" :
                                        !evento.getEntidadAfectada()  && evento.getComercioAfectado() ? "Aceptantes" :
                                                "EIF_Aceptantes"
                );
                row.createCell(colIndex++).setCellValue(evento.getDetalleEstado() != null ? evento.getDetalleEstado() : "");
                row.createCell(colIndex++).setCellValue(evento.getEstadoEvento() != null ? evento.getEstadoEvento() : "");
                row.createCell(colIndex++).setCellValue(evento.getMonedaId() != null ? evento.getMonedaId().getClave(): "NA");
                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ?  evento.getMontoPerdida().toString() : "NA");

                // Columna AO:AX     41:50
                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ? String.valueOf(convierteBsADolares(evento)) : "NA");
                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ? evento.getGastoAsociado().toString() :  "NA");
                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ? String.valueOf(calcularMontoTotal(evento)) : "NA");
                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ? (evento.getCoberturaSeguro() ? "SI" : "NO") : "NA");
                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ? evento.getPolizaSeguroId() != null ? evento.getPolizaSeguroId().getNombre() : "NA" : "NA");
                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ? evento.getMontoRecuperadoSeguro() != null? evento.getMontoRecuperadoSeguro().toString() : "0.0" : "NA");
                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ? evento.getPerdidaMercado() != null ? evento.getPerdidaMercado().toString() : "NA" : "NA");
                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ? evento.getRecuperacionActivoId() != null ? evento.getRecuperacionActivoId().getNombre() : "NA" : "NA");
                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ? (String.valueOf(convierteBsADolares(evento) - calcularMontoTotal(evento))) : "NA" );
                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ? evento.getFechaContable() != null ? dateFormat.format(evento.getFechaContable()) : "NA" : "NA");

                // Columna AY:BH     51:60
                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ? evento.getCuentaContableId() != null ? evento.getCuentaContableId().getCodigoAsfi() : "NA" : "NA");
                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ? evento.getCuentaContableId() != null ? evento.getCuentaContableId().getNombre() : "NA" : "NA");
                row.createCell(colIndex++).setCellValue(evento.getFuenteInfId() != null ? evento.getFuenteInfId().getNombre() : "");
                row.createCell(colIndex++).setCellValue(evento.getImpactoId() != null ? evento.getImpactoId().getNombre() : "");

                // Inicializa un StringBuilder para construir el string
                StringBuilder riesgosConcatenados = new StringBuilder();

                // Verifica cada campo y concatena el texto correspondiente si no es null
                if (evento.getOperativoId() != null) {
                    riesgosConcatenados.append("Riesgo Operativo");
                }

                if (evento.getSeguridadId() != null) {
                    if (riesgosConcatenados.length() > 0) {
                        riesgosConcatenados.append("_");
                    }
                    riesgosConcatenados.append("Riesgo Seguridad de la Información");
                }

                if (evento.getLiquidezId() != null) {
                    if (riesgosConcatenados.length() > 0) {
                        riesgosConcatenados.append("_");
                    }
                    riesgosConcatenados.append("Riesgo Liquidez y Mercado");
                }

                if (evento.getLgiId() != null) {
                    if (riesgosConcatenados.length() > 0) {
                        riesgosConcatenados.append("_");
                    }
                    riesgosConcatenados.append("Riesgo LGI FT y/o DP");
                }

                if (evento.getFraudeId() != null) {
                    if (riesgosConcatenados.length() > 0) {
                        riesgosConcatenados.append("_");
                    }
                    riesgosConcatenados.append("Riesgo Fraude con medios de Pago Electrónico");
                }

                if (evento.getLegalId() != null) {
                    if (riesgosConcatenados.length() > 0) {
                        riesgosConcatenados.append("_");
                    }
                    riesgosConcatenados.append("Riesgo Legal y Regulatorio");
                }

                if (evento.getReputacionalId() != null) {
                    if (riesgosConcatenados.length() > 0) {
                        riesgosConcatenados.append("_");
                    }
                    riesgosConcatenados.append("Riesgo Reputacional");
                }

                if (evento.getCumplimientoId() != null) {
                    if (riesgosConcatenados.length() > 0) {
                        riesgosConcatenados.append("_");
                    }
                    riesgosConcatenados.append("Riesgo Cumplimiento");
                }

                if (evento.getEstrategicoId() != null) {
                    if (riesgosConcatenados.length() > 0) {
                        riesgosConcatenados.append("_");
                    }
                    riesgosConcatenados.append("Riesgo Estratégico");
                }

                if (evento.getGobiernoId() != null) {
                    if (riesgosConcatenados.length() > 0) {
                        riesgosConcatenados.append("_");
                    }
                    riesgosConcatenados.append("Riesgo Gobierno Corporativo");
                }
                // Crea la celda en la hoja Excel con el texto concatenado
                row.createCell(colIndex++).setCellValue(riesgosConcatenados.toString());

                //row.createCell(colIndex++).setCellValue(evento.getCargoId() != null ? evento.getCargoId().getNombre() : "");

                // Asigna los nombres de los cargos a la celda, separados por "_"
                String cargos = evento.getCargoId() != null ? evento.getCargoId().stream()
                        .map(TablaDescripcion::getNombre) // Asume que getNombre() devuelve el nombre del cargo
                        .filter(nombre -> nombre != null && !nombre.isEmpty()) // Filtra nombres nulos o vacíos
                        .collect(Collectors.joining("_")) : ""; // Une los nombres con "_"
                row.createCell(colIndex++).setCellValue(cargos);

                row.createCell(colIndex++).setCellValue(evento.getCanalAsfiId() != null ? evento.getCanalAsfiId().getNombre() : "");
                row.createCell(colIndex++).setCellValue(evento.getEfectoPerdidaId() != null ? evento.getEfectoPerdidaId().getNombre() : "");
                row.createCell(colIndex++).setCellValue(evento.getProcesoCriticoAsfi() != null ? evento.getProcesoCriticoAsfi() : 0);
                row.createCell(colIndex++).setCellValue(evento.getProcesoCriticoAsfi() != null ? (evento.getProcesoCriticoAsfi() == 1 ? "Se encuentra definido dentro del Mapa de Procesos, priorizando los Procesos Operativos de acuerdo a la cadena de valor que impacta directamente a los objetivos estratégico de la empresa establecidos en el Plan-GTIC-002-SIST." : "") : "");

                // Columna BI:BR     61:65
                row.createCell(colIndex++).setCellValue(evento.getOperativoId() != null ? evento.getOperativoId().getClave() : "-");
                row.createCell(colIndex++).setCellValue(evento.getSeguridadId() != null ? evento.getSeguridadId().getClave() : "-");
                row.createCell(colIndex++).setCellValue(evento.getLiquidezId() != null ? evento.getLiquidezId().getClave() : "-");
                row.createCell(colIndex++).setCellValue(evento.getLgiId() != null ? evento.getLgiId().getClave() : "-");
                row.createCell(colIndex++).setCellValue(evento.getFraudeId() != null ? evento.getFraudeId().getClave() : "-");
                row.createCell(colIndex++).setCellValue(evento.getLegalId() != null ? evento.getLegalId().getClave() : "-");
                row.createCell(colIndex++).setCellValue(evento.getReputacionalId() != null ? evento.getReputacionalId().getClave() : "-");
                row.createCell(colIndex++).setCellValue(evento.getCumplimientoId() != null ? evento.getCumplimientoId().getClave() : "-");
                row.createCell(colIndex++).setCellValue(evento.getEstrategicoId() != null ? evento.getEstrategicoId().getClave() : "-");
                row.createCell(colIndex++).setCellValue(evento.getGobiernoId() != null ? evento.getGobiernoId().getClave() : "-");

                // Columna BS:BU     61:65
                row.createCell(colIndex++).setCellValue(evento.getCodigoInicial() != null ? evento.getCodigoInicial() : "-");
                row.createCell(colIndex++).setCellValue(evento.getSubcategorizacionId() != null ? evento.getSubcategorizacionId().getNombre() : "-");
                row.createCell(colIndex++).setCellValue(evento.getTrimestre() != null ? evento.getTrimestre() : "-");

                // Aplica el estilo de datos a cada celda de esta fila:
                for (int j = 0; j < colIndex; j++) {
                    row.getCell(j).setCellStyle(cellStyle);
                }

            }
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el reporte de auditoría externa", e);
        }
    }


    @Override
    // REPORTE ASFI
    public byte[] reporteAsfiExcel(FiltroReporteAuditoria filter) {
        List<EventoRiesgo> eventos = eventoRiesgoRepository.getReporteAuditoriaExterna(filter.getFechaDesde(), filter.getFechaHasta());

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("ASFI");

            //Define un Formateador de Fechas:
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

            // Crear un estilo de cabecera con texto en negrita, fondo azul claro y con salto de línea
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
            headerCellStyle.setWrapText(true); // Permite saltos de línea

            // Define las cabeceras manualmente con los campos deseados: 65 COLUMNAS
            String[] headers = {
                    "Código del Evento", "Fecha del Inicio del Evento", "Hora de Inicio", "Fecha de Descubrimiento del Evento", "Hora de Descubrimiento", "Fecha de Finalización del Evento",  "Hora de Finalización", "Agencia", "(Lugar) Oficina", "Área Involucrada ( en la que se suscitó la incidencia)",
                    "Unidad", "Descripción Resumida del Evento", "Categoría (del Evento)", "Descripción Clase de Evento", "Tipo de Evento", "Definición","Evento Crítico", "Detalle Evento Crítico", "Factores de Riesgo Operativo", "MacroProceso",
                    "Código ASFI de Proceso", "Codigo Riesgo Relacionado", "Descripción Riesgo", "Código de Macroproceso", "Proceso Critico", "Líneas de Negocio (Nivel 3)", "Líneas de Negocio ASFI", "Operaciones ASFI", "Acciones efectuadas por el área responsable de la incidencia", "Estado del Evento",
                    "Pérdida por Riesgo Operativo (Valor Contable) Monto de Pérdida", "Monto de Pérdida por Riesgo Operativo USD", "Gastos Asociados a la Pérdida (BS)", "Monto Total Recuperados", "Cobertura de Seguro SI / NO", "Póliza de Seguro asociada", "Monto Recuperado por Coberturas de Seguros  BS", "Pérdida por Riesgo Operativo (Valor de Mercado)", "Recuperación de Activo", "Monto Total Final de la Perdida Expresado en USD",
                    "Fecha de Registro Contable del evento de Pérdida", "Cuentas Contables Afectadas", "Nombre cuenta", "Fuente de información", "Cargo (s) Involucrados", "Canal", "Proceso Crítico", "Detalle Proceso Crítico",  "Operativo", "Seguridad de la Información",
                    "Liquidez y Mercado", "LGI FT y/o DP", "Fraude con medios de Pago Electrónico", "Legal y Regulatorio", "Reputacional", "Cumplimiento", "Estratégico", "Gobierno Corporativo"
            };

            // Creando el encabezado con ancho fijo
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerCellStyle);

                // Establece el mismo ancho fijo para cada columna
                sheet.setColumnWidth(i, 4000);
            }

            // Estilo para las celdas de datos
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);

            // Llena los datos filtrando los campos seleccionados
            for (EventoRiesgo evento : eventos) {
                Row row = sheet.createRow(sheet.getLastRowNum() + 1);
                int colIndex = 0;

                // Columna A-J   1:10
                row.createCell(colIndex++).setCellValue(evento.getCodigo() != null ? evento.getCodigo() : "");
                row.createCell(colIndex++).setCellValue(evento.getFechaIni() != null ? dateFormat.format(evento.getFechaIni()) : "");
                row.createCell(colIndex++).setCellValue(evento.getHoraIni() != null ? timeFormat.format(evento.getHoraIni()) : "");
                row.createCell(colIndex++).setCellValue(evento.getFechaDesc() != null ? dateFormat.format(evento.getFechaDesc()) : "");
                row.createCell(colIndex++).setCellValue(evento.getHoraDesc() != null ? timeFormat.format(evento.getHoraDesc()) : "");
                row.createCell(colIndex++).setCellValue(evento.getFechaFin() != null ? dateFormat.format(evento.getFechaFin()) : "");
                row.createCell(colIndex++).setCellValue(evento.getHoraFin() != null ? timeFormat.format(evento.getHoraFin()) : "");
                row.createCell(colIndex++).setCellValue(evento.getAgenciaId() != null ? evento.getAgenciaId().getNombre(): "");
                row.createCell(colIndex++).setCellValue(evento.getCiudadId() != null ? evento.getCiudadId().getNombre() : "");
                row.createCell(colIndex++).setCellValue(evento.getAreaID() != null ? evento.getAreaID().getNombre() : "");

                // Columna K-T   11:20
                row.createCell(colIndex++).setCellValue(evento.getUnidadId() != null ? evento.getUnidadId().getNombre() : "");
                row.createCell(colIndex++).setCellValue(evento.getDescripcion() != null ? evento.getDescripcion() : "");
                row.createCell(colIndex++).setCellValue(evento.getTipoEvento() != null ? evento.getTipoEvento() : "");
                row.createCell(colIndex++).setCellValue(eventoRiesgoRepository.getDescripcionTipoEvento(evento.getTipoEvento()!= null ? evento.getTipoEvento() : ""));
                row.createCell(colIndex++).setCellValue(evento.getTipoEventoPerdidaId() != null ? evento.getTipoEventoPerdidaId().getNombre() : "");
                row.createCell(colIndex++).setCellValue(evento.getTipoEventoPerdidaId() != null ? evento.getTipoEventoPerdidaId().getDescripcion() : "");
                row.createCell(colIndex++).setCellValue(evento.getEventoCritico() != null ? evento.getEventoCritico() : "");
                row.createCell(colIndex++).setCellValue(evento.getDetalleEventoCritico() != null ? evento.getDetalleEventoCritico() : "");
                row.createCell(colIndex++).setCellValue(evento.getFactorRiesgoId() != null ? evento.getFactorRiesgoId().getNombre() : "");
                row.createCell(colIndex++).setCellValue(evento.getProcesoId()!= null ? evento.getProcesoId().getNombre() : "");

                // Columna U:AD     21:30
                row.createCell(colIndex++).setCellValue(evento.getProcesoId()!= null ? evento.getProcesoId().getCodigoAsfi() : "");

                // Es para obtener los valores de las dos columnas que contienen una lista:
                StringBuilder riesgosCodigo = new StringBuilder();
                StringBuilder riesgosDetalles = new StringBuilder();

                if (evento.getRiesgoRelacionado() != null && !evento.getRiesgoRelacionado().isEmpty()) {
                    for (MatrizRiesgo riesgo : evento.getRiesgoRelacionado()) {
                        riesgosCodigo.append(riesgo.getCodigo()).append("_");
                        riesgosDetalles.append(riesgo.getDefinicion()).append(" debido a ").append(riesgo.getCausa()).append(" puede ocasionar ").append(riesgo.getConsecuencia()).append("_");
                    }
                }
                // Asignar todos los códigos
                Cell cellCodigo = row.createCell(colIndex++);
                cellCodigo.setCellValue(riesgosCodigo.length() > 0 ? riesgosCodigo.substring(0, riesgosCodigo.length() - 1): ""); // Quita el último "_"

                // Asignar todos los detalles
                Cell cellDetalles = row.createCell(colIndex++);
                cellDetalles.setCellValue(riesgosDetalles.length() > 0 ? riesgosDetalles.substring(0, riesgosDetalles.length() - 1): ""); // Quita el último "_"

                row.createCell(colIndex++).setCellValue(evento.getProcesoId() != null ? evento.getProcesoId().getClave() : "");
                row.createCell(colIndex++).setCellValue(evento.getProcesoId() != null ? evento.getProcesoId().getCampoA() : "");
                row.createCell(colIndex++).setCellValue(evento.getLineaNegocio() != null ? evento.getLineaNegocio() : "");
                row.createCell(colIndex++).setCellValue(evento.getLineaAsfiId() != null ? evento.getLineaAsfiId().getNombre() : "");
                row.createCell(colIndex++).setCellValue(evento.getOperacionId() != null ? evento.getOperacionId().getNombre() : "");
                row.createCell(colIndex++).setCellValue(evento.getDetalleEstado() != null ? evento.getDetalleEstado() : "");
                row.createCell(colIndex++).setCellValue(evento.getEstadoEvento() != null ? evento.getEstadoEvento() : "");

                // Columna AE:AN      31:40
                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ?  evento.getMontoPerdida().toString() : "NA");
                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ? String.valueOf(convierteBsADolares(evento)) : "NA");
                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ? evento.getGastoAsociado().toString() :  "NA");
                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ? String.valueOf(calcularMontoTotal(evento)) : "NA");
                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ? (evento.getCoberturaSeguro() ? "SI" : "NO") : "NA");
                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ? evento.getPolizaSeguroId() != null ? evento.getPolizaSeguroId().getNombre() : "NA" : "NA");
                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ? evento.getMontoRecuperadoSeguro() != null? evento.getMontoRecuperadoSeguro().toString() : "0.0" : "NA");
                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ? evento.getPerdidaMercado() != null ? evento.getPerdidaMercado().toString() : "NA" : "NA");
                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ? evento.getRecuperacionActivoId() != null ? evento.getRecuperacionActivoId().getNombre() : "NA" : "NA");
                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ? (String.valueOf(convierteBsADolares(evento) - calcularMontoTotal(evento))) : "NA" );

                // Columna AO:AX     41:50
                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ? evento.getFechaContable() != null ? dateFormat.format(evento.getFechaContable()) : "NA" : "NA");
                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ? evento.getCuentaContableId() != null ? evento.getCuentaContableId().getCodigoAsfi() : "NA" : "NA");
                row.createCell(colIndex++).setCellValue("A".equals(evento.getTipoEvento()) ? evento.getCuentaContableId() != null ? evento.getCuentaContableId().getNombre() : "NA" : "NA");
                row.createCell(colIndex++).setCellValue(evento.getFuenteInfId() != null ? evento.getFuenteInfId().getNombre() : "");

                //row.createCell(colIndex++).setCellValue(evento.getCargoId() != null ? evento.getCargoId().getNombre() : "");

                // Asigna los nombres de los cargos a la celda, separados por "_"
                String cargos = evento.getCargoId() != null ? evento.getCargoId().stream()
                        .map(TablaDescripcion::getNombre) // Asume que getNombre() devuelve el nombre del cargo
                        .filter(nombre -> nombre != null && !nombre.isEmpty()) // Filtra nombres nulos o vacíos
                        .collect(Collectors.joining("_")) : ""; // Une los nombres con "_"
                row.createCell(colIndex++).setCellValue(cargos);

                row.createCell(colIndex++).setCellValue(evento.getCanalAsfiId() != null ? evento.getCanalAsfiId().getNombre() : "");
                row.createCell(colIndex++).setCellValue(evento.getProcesoCriticoAsfi() != null ? evento.getProcesoCriticoAsfi() : 0);
                row.createCell(colIndex++).setCellValue(evento.getProcesoCriticoAsfi() != null ? (evento.getProcesoCriticoAsfi() == 1 ? "Se encuentra definido dentro del Mapa de Procesos, priorizando los Procesos Operativos de acuerdo a la cadena de valor que impacta directamente a los objetivos estratégico de la empresa establecidos en el Plan-GTIC-002-SIST." : "") : "");
                row.createCell(colIndex++).setCellValue(evento.getOperativoId() != null ? evento.getOperativoId().getClave() : "-");
                row.createCell(colIndex++).setCellValue(evento.getSeguridadId() != null ? evento.getSeguridadId().getClave() : "-");

                // Columna AY:BF     51:58
                row.createCell(colIndex++).setCellValue(evento.getLiquidezId() != null ? evento.getLiquidezId().getClave() : "-");
                row.createCell(colIndex++).setCellValue(evento.getLgiId() != null ? evento.getLgiId().getClave() : "-");
                row.createCell(colIndex++).setCellValue(evento.getFraudeId() != null ? evento.getFraudeId().getClave() : "-");
                row.createCell(colIndex++).setCellValue(evento.getLegalId() != null ? evento.getLegalId().getClave() : "-");
                row.createCell(colIndex++).setCellValue(evento.getReputacionalId() != null ? evento.getReputacionalId().getClave() : "-");
                row.createCell(colIndex++).setCellValue(evento.getCumplimientoId() != null ? evento.getCumplimientoId().getClave() : "-");
                row.createCell(colIndex++).setCellValue(evento.getEstrategicoId() != null ? evento.getEstrategicoId().getClave() : "-");
                row.createCell(colIndex++).setCellValue(evento.getGobiernoId() != null ? evento.getGobiernoId().getClave() : "-");

                // Aplica el estilo de datos a cada celda de esta fila:
                for (int j = 0; j < colIndex; j++) {
                    row.getCell(j).setCellStyle(cellStyle);
                }

            }
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el reporte de auditoría externa", e);
        }
    }


    private double calcularMontoTotal(EventoRiesgo evento) {
        double result = 0;
        if (evento.getTipoEvento() != null && evento.getTipoEvento().equals("A")) {
            double montoRecuperado = evento.getMontoRecuperado() != null ? evento.getMontoRecuperado() : 0;
            double gastoAsociado = evento.getGastoAsociado() != null ? evento.getGastoAsociado() : 0;
            double montoRecuperadoSeguro = evento.getMontoRecuperadoSeguro() != null ? evento.getMontoRecuperadoSeguro() : 0;
            result = BigDecimal.valueOf(montoRecuperado + gastoAsociado + montoRecuperadoSeguro).setScale(2, RoundingMode.HALF_UP).doubleValue();
        }
        return result;
    }

    private double convierteDolaresABs(EventoRiesgo evento) {
        double result = 0;
        if (evento.getTipoEvento().equals("A")) {
            String clave = evento.getMonedaId().getClave();
            if ("BOB".equals(clave) || "Bs".equals(clave)) {
                result = BigDecimal.valueOf(evento.getMontoPerdida()).setScale(2, RoundingMode.HALF_UP).doubleValue();
            } else if ("USD".equals(clave) || "$".equals(clave)) {
                result = BigDecimal.valueOf(evento.getMontoPerdida() * Double.parseDouble(evento.getTasaCambioId())).setScale(2, RoundingMode.HALF_UP).doubleValue();
            } else {
                result = 0;
            }
        } else {
            result = 0;
        }
        return result;
    }


    private double convierteBsADolares(EventoRiesgo evento) {
        double result = 0;
        if (evento.getTipoEvento().equals("A")) {
            String clave = evento.getMonedaId().getClave();
            if ("BOB".equals(clave) || "Bs".equals(clave)) {
                result = BigDecimal.valueOf(evento.getMontoPerdida() / Double.parseDouble(evento.getTasaCambioId())).setScale(2, RoundingMode.HALF_UP).doubleValue();
            } else if ("USD".equals(clave) || "$".equals(clave)) {
                result = BigDecimal.valueOf(evento.getMontoPerdida()).setScale(2, RoundingMode.HALF_UP).doubleValue();
            } else{
                result = 0;
            }
        } else {
            result = 0;
        }
        return result;
    }


    // REPORTE DINAMICO EVENTO DE RIESGO
    @Override
    public byte[] reporteConfigEvento(FiltroReporteConfigEvento filter)  {
        List<Map<String, Object>> results = getDataEventoColumns(filter);

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Evento de Riesgos");

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
                FiltroReporteConfigEvento.DataColumn column = filter.getDataColumns().get(colIdx);
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
                for (FiltroReporteConfigEvento.DataColumn column : filter.getDataColumns()) {
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


    private List<Map<String, Object>> getDataEventoColumns(FiltroReporteConfigEvento filter) {
        StringBuilder query = new StringBuilder("SELECT ");
        // Construye las columnas dinámicamente
        List<String> columns = filter.getDataColumns().stream()
                .map(column -> {
                    switch (column.getId()) {
                        case 1:
                            return "COALESCE(e.eve_codigo, '-') AS \"" + column.getLabel() + "\"";
                        case 2:
                            return "COALESCE(TO_CHAR(e.eve_fecha_ini, 'DD/MM/YYYY'), '-') AS \"" + column.getLabel() + "\"";
                        case 3:
                            return "COALESCE(TO_CHAR(e.eve_hora_ini, 'HH24:MI:SS'), '-') AS \"" + column.getLabel() + "\"";
                        case 4:
                            return "COALESCE(TO_CHAR(e.eve_fecha_desc, 'DD/MM/YYYY'), '-') AS \"" + column.getLabel() + "\"";
                        case 5:
                            return "COALESCE(TO_CHAR(e.eve_hora_desc, 'HH24:MI:SS'), '-') AS \"" + column.getLabel() + "\"";
                        case 6:
                            return "COALESCE(CASE TO_CHAR(e.eve_fecha_ini, 'Mon') WHEN 'Jan' THEN 'ene' WHEN 'Feb' THEN 'feb' WHEN 'Mar' THEN 'mar' WHEN 'Apr' THEN 'abr' WHEN 'May' THEN 'may' WHEN 'Jun' THEN 'jun' WHEN 'Jul' THEN 'jul' WHEN 'Aug' THEN 'ago' WHEN 'Sep' THEN 'sep' WHEN 'Oct' THEN 'oct' WHEN 'Nov' THEN 'nov' WHEN 'Dec' THEN 'dic' END || '-' || to_char(e.eve_fecha_ini, 'YY'), '-') AS \"" + column.getLabel() + "\"";
                        case 7:
                            return "COALESCE(TO_CHAR(e.eve_fecha_ini, 'YYYY'), '-') AS \"" + column.getLabel() + "\"";
                        case 8:
                            return "COALESCE(TO_CHAR(e.eve_fecha_fin, 'DD/MM/YYYY'), '-') AS \"" + column.getLabel() + "\"";
                        case 9:
                            return "COALESCE(TO_CHAR(e.eve_hora_fin, 'HH24:MI:SS'), '-') AS \"" + column.getLabel() + "\"";
                        case 10:
                            return "COALESCE(CASE TO_CHAR(e.eve_fecha_fin, 'Mon') WHEN 'Jan' THEN 'ene' WHEN 'Feb' THEN 'feb' WHEN 'Mar' THEN 'mar' WHEN 'Apr' THEN 'abr' WHEN 'May' THEN 'may' WHEN 'Jun' THEN 'jun' WHEN 'Jul' THEN 'jul' WHEN 'Aug' THEN 'ago' WHEN 'Sep' THEN 'sep' WHEN 'Oct' THEN 'oct' WHEN 'Nov' THEN 'nov' WHEN 'Dec' THEN 'dic' END || '-' || to_char(e.eve_fecha_fin, 'YY'), '-') AS \"" + column.getLabel() + "\"";
                        case 11:
                            return "COALESCE(TO_CHAR(e.eve_fecha_fin, 'YYYY'), '-') AS \"" + column.getLabel() + "\"";
                        case 12:
                            return "COALESCE((SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_agencia_id = d.des_id), '-') AS \"" + column.getLabel() + "\"";
                        case 13:
                            return "COALESCE((SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_ciudad_id = d.des_id), '-') AS \"" + column.getLabel() + "\"";
                        case 14:
                            return "COALESCE((SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_area_id = d.des_id), '-') AS \"" + column.getLabel() + "\"";
                        case 15:
                            return "COALESCE((SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_unidad_id = d.des_id), '-') AS \"" + column.getLabel() + "\"";
                        case 16:
                            return "COALESCE(e.eve_descripcion, '-') AS \"" + column.getLabel() + "\"";
                        case 17:
                            return "COALESCE(e.eve_descripcion_completa, '-') AS \"" + column.getLabel() + "\"";
                        case 18:
                            return "COALESCE(e.eve_tipo_evento, '-') AS \"" + column.getLabel() + "\"";
                        case 19:
                            return "COALESCE((SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion d WHERE des_tabla_id = 6 AND e.eve_tipo_evento = d.des_clave), '-') AS \"" + column.getLabel() + "\"";
                        case 20:
                            return "COALESCE((SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_tipo_evento_perdida_id = d.des_id), '-') AS \"" + column.getLabel() + "\"";
                        case 21:
                            return "COALESCE((SELECT d.des_descripcion FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_tipo_evento_perdida_id = d.des_id), '-') AS \"" + column.getLabel() + "\"";
                        case 22:
                            return "COALESCE((SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_sub_evento_id = d.des_id), '-') AS \"" + column.getLabel() + "\"";
                        case 23:
                            return "COALESCE((SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_clase_evento_id = d.des_id), '-') AS \"" + column.getLabel() + "\"";
                        case 24:
                            return "COALESCE(e.eve_evento_critico, '-') AS \"" + column.getLabel() + "\"";
                        case 25:
                            return "COALESCE(e.eve_detalle_evento_critico, '-') AS \"" + column.getLabel() + "\"";
                        case 26:
                            return "COALESCE((SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_factor_riesgo_id = d.des_id), '-') AS \"" + column.getLabel() + "\"";
                        case 27:
                            return "COALESCE((SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_proceso_id = d.des_id), '-') AS \"" + column.getLabel() + "\"";
                        case 28:
                            return "COALESCE((SELECT d.des_codigo_asfi FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_proceso_id = d.des_id), '-') AS \"" + column.getLabel() + "\"";
                        case 29:
                            return "COALESCE((SELECT STRING_AGG(r.rie_codigo, '_') FROM riesgos.tbl_matriz_riesgo r INNER JOIN riesgos.eventoriesgo_matriz er ON r.rie_id = er.id_matriz_riesgo WHERE er.id_evento_riesgo = e.eve_id), '-') AS \"" + column.getLabel() + "\"";
                        case 30:
                            return "COALESCE((SELECT STRING_AGG(CONCAT(r.rie_definicion, ' debido a ', r.rie_causa, ' puede ocasionar ', r.rie_consecuencia), '_') FROM riesgos.tbl_matriz_riesgo r INNER JOIN riesgos.eventoriesgo_matriz em ON r.rie_id = em.id_matriz_riesgo WHERE em.id_evento_riesgo = e.eve_id), '-') AS \"" + column.getLabel() + "\"";
                        case 31:
                            return "COALESCE((SELECT d.des_clave FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_proceso_id = d.des_id), '-') AS \"" + column.getLabel() + "\"";
                        case 32:
                            return "COALESCE((SELECT d.des_campo_a FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_procedimiento_id = d.des_id), '-') AS \"" + column.getLabel() + "\"";
                        case 33:
                            return "COALESCE((SELECT d.des_campo_a FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_proceso_id = d.des_id), '-') AS \"" + column.getLabel() + "\"";
                        case 34:
                            return "COALESCE(e.eve_linea_negocio, '-') AS \"" + column.getLabel() + "\"";
                        case 35:
                            return "COALESCE((SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_linea_asfi_id  = d.des_id), '-') AS \"" + column.getLabel() + "\"";
                        case 36:
                            return "COALESCE((SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_ope_pro_ser_id = d.des_id), '-') AS \"" + column.getLabel() + "\"";
                        case 37:
                            return "COALESCE((SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_tipo_servicio_id = d.des_id), '-') AS \"" + column.getLabel() + "\"";
                        case 38:
                            return "COALESCE((SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_desc_servicio_id = d.des_id), '-') AS \"" + column.getLabel() + "\"";
                        case 39:
                            return "COALESCE((SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_operacion_id = d.des_id), '-') AS \"" + column.getLabel() + "\"";
                        case 40:
                            return "(SELECT CASE WHEN NOT e.eve_entidad_afectada  AND NOT e.eve_comercio_afectado  THEN 'ATC' WHEN e.eve_entidad_afectada AND NOT e.eve_comercio_afectado THEN 'EIF' WHEN NOT e.eve_entidad_afectada AND e.eve_comercio_afectado THEN 'Aceptantes' ELSE 'EIF_Aceptantes' END) AS \"" + column.getLabel() + "\"";
                        case 41:
                            return "COALESCE(NULLIF(e.eve_detalle_estado, ''), '-') AS \"" + column.getLabel() + "\"";
                        case 42:
                            return "COALESCE(e.eve_estado_evento, '-') AS \"" + column.getLabel() + "\"";
                        case 43:
                            return "CASE WHEN e.eve_tipo_evento = 'A' THEN COALESCE((SELECT d.des_clave FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_moneda_id = d.des_id),'NA') ELSE 'NA' END AS \"" + column.getLabel() + "\"";
                        case 44:
                            return "CASE WHEN e.eve_tipo_evento = 'A' THEN COALESCE(CAST(ROUND(CAST(e.eve_monto_perdida AS numeric), 2) AS varchar), 'NA') ELSE 'NA' END AS \"" + column.getLabel() + "\"";
                        case 45:
                            return "CASE WHEN e.eve_tipo_evento = 'A' THEN COALESCE(CASE WHEN (SELECT d.des_clave FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_moneda_id = d.des_id) = 'BOB' THEN CAST(ROUND(CAST(e.eve_monto_perdida AS numeric) / CAST(e.eve_tasa_cambio_id AS numeric), 2) AS varchar) ELSE CAST(ROUND(CAST(e.eve_monto_perdida AS numeric), 2) AS varchar) END, 'NA') ELSE 'NA' END AS \"" + column.getLabel() + "\"";
                        case 46:
                            return "CASE WHEN e.eve_tipo_evento = 'A' THEN COALESCE(CAST(ROUND(CAST(e.eve_gasto_asociado AS numeric), 2) AS varchar), 'NA') ELSE 'NA' END AS \"" + column.getLabel() + "\"";
                        case 47:
                            return "CASE WHEN e.eve_tipo_evento = 'A' THEN COALESCE(CAST(ROUND(COALESCE(CAST(e.eve_monto_recuperado AS numeric), 0) + COALESCE(CAST(e.eve_gasto_asociado AS numeric), 0) + COALESCE(CAST(e.eve_monto_recuperado_seguro AS numeric), 0),2) AS varchar), '0.00') ELSE 'NA' END AS \"" + column.getLabel() + "\"";
                        case 48:
                            return "CASE WHEN e.eve_tipo_evento = 'A' THEN CASE WHEN e.eve_cobertura_seguro THEN 'SI' ELSE 'NO' END ELSE 'NA' END AS \"" + column.getLabel() + "\"";
                        case 49:
                            return "CASE WHEN e.eve_tipo_evento = 'A' THEN COALESCE((SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_poliza_seguro_id = d.des_id),'NA') ELSE 'NA' END AS \"" + column.getLabel() + "\"";
                        case 50:
                            return "CASE WHEN e.eve_tipo_evento = 'A' THEN COALESCE(CAST(ROUND(CAST(e.eve_monto_recuperado_seguro AS numeric), 2) AS varchar), 'NA') ELSE 'NA' END AS \"" + column.getLabel() + "\"";
                        case 51:
                            return "CASE WHEN e.eve_tipo_evento = 'A' THEN COALESCE(CAST(ROUND(CAST(e.eve_perdida_mercado AS numeric), 2) AS varchar), 'NA') ELSE 'NA' END AS \"" + column.getLabel() + "\"";
                        case 52:
                            return "CASE WHEN e.eve_tipo_evento = 'A' THEN COALESCE((SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_recuperacion_activo_id = d.des_id),'NA') ELSE 'NA' END AS \"" + column.getLabel() + "\"";
                        case 53:
                            return "CASE WHEN e.eve_tipo_evento = 'A' THEN COALESCE(CAST(ROUND(COALESCE(CASE WHEN (SELECT d.des_clave FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_moneda_id = d.des_id) = 'BOB' THEN CAST(e.eve_monto_perdida AS numeric) / CAST(e.eve_tasa_cambio_id AS numeric) ELSE CAST(e.eve_monto_perdida AS numeric) END, 0) + COALESCE(CAST(e.eve_monto_recuperado AS numeric), 0) + COALESCE(CAST(e.eve_gasto_asociado AS numeric), 0) + COALESCE(CAST(e.eve_monto_recuperado_seguro AS numeric), 0), 2) AS varchar), '0.00') ELSE 'NA' END AS \"" + column.getLabel() + "\"";
                        case 54:
                            return "CASE WHEN e.eve_tipo_evento = 'A' THEN COALESCE(TO_CHAR(e.eve_fecha_contable, 'DD/MM/YYYY'), 'NA') ELSE 'NA' END AS \"" + column.getLabel() + "\"";
                        case 55:
                            return "CASE WHEN e.eve_tipo_evento = 'A' THEN COALESCE((SELECT d.des_codigo_asfi FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_cuenta_contable_id = d.des_id),'NA') ELSE 'NA' END AS \"" + column.getLabel() + "\"";
                        case 56:
                            return "CASE WHEN e.eve_tipo_evento = 'A' THEN COALESCE((SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_cuenta_contable_id = d.des_id),'NA') ELSE 'NA' END AS \"" + column.getLabel() + "\"";
                        case 57:
                            return "COALESCE((SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_fuente_inf_id = d.des_id),'NA') AS \"" + column.getLabel() + "\"";
                        case 58:
                            return "COALESCE((SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_impacto_id = d.des_id),'NA') AS \"" + column.getLabel() + "\"";
                        case 59:
                            return "CONCAT_WS('_', CASE WHEN e.eve_operativo_id IS NOT NULL THEN 'Riesgo Operativo' END,CASE WHEN e.eve_seguridad_id IS NOT NULL THEN 'Riesgo Seguridad de la Información' END, CASE WHEN e.eve_liquidez_id IS NOT NULL THEN 'Riesgo Liquidez y Mercado' END, CASE WHEN e.eve_lgi_id IS NOT NULL THEN 'Riesgo LGI FT y/o DP' END, CASE WHEN e.eve_fraude_id IS NOT NULL THEN 'Riesgo Fraude con medios de Pago Electrónico' END, CASE WHEN e.eve_legal_id IS NOT NULL THEN 'Riesgo Legal y Regulatorio' END, CASE WHEN e.eve_reputacional_id IS NOT NULL THEN 'Riesgo Reputacional' END, CASE WHEN e.eve_cumplimiento_id IS NOT NULL THEN 'Riesgo Cumplimiento' END, CASE WHEN e.eve_estrategico_id IS NOT NULL THEN 'Riesgo Estratégico' END, CASE WHEN e.eve_gobierno_id IS NOT NULL THEN 'Riesgo Gobierno Corporativo' END) AS \"" + column.getLabel() + "\"";
                        case 60:
                            return "COALESCE((SELECT STRING_AGG(d.des_nombre, '_') FROM riesgos.tbl_tabla_descripcion d INNER JOIN riesgos.tbl_evento_cargos ec ON d.des_id = ec.cargo_id WHERE ec.eve_id = e.eve_id), '-') AS \"" + column.getLabel() + "\"";
                        case 61:
                            return "COALESCE((SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_canal_asfi_id = d.des_id),'NA') AS \"" + column.getLabel() + "\"";
                        case 62:
                            return "COALESCE((SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_efecto_perdida_id = d.des_id),'NA') AS \"" + column.getLabel() + "\"";
                        case 63:
                            return "COALESCE(CAST(e.eve_proceso_critico_asfi AS varchar), '') AS \"" + column.getLabel() + "\"";
                        case 64:
                            return "CASE WHEN e.eve_proceso_critico_asfi = 1 THEN 'Se encuentra definido dentro del Mapa de Procesos, priorizando los Procesos Operativos de acuerdo a la cadena de valor que impacta directamente a los objetivos estratégico de la empresa establecidos en el Plan-GTIC-002-SIST.' ELSE '' END AS \"" + column.getLabel() + "\"";
                        case 65:
                            return "COALESCE((SELECT d.des_clave FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_operativo_id = d.des_id), '-') AS \"" + column.getLabel() + "\"";
                        case 66:
                            return "COALESCE((SELECT d.des_clave FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_seguridad_id = d.des_id), '-') AS \"" + column.getLabel() + "\"";
                        case 67:
                            return "COALESCE((SELECT d.des_clave FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_liquidez_id = d.des_id), '-') AS \"" + column.getLabel() + "\"";
                        case 68:
                            return "COALESCE((SELECT d.des_clave FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_lgi_id = d.des_id), '-') AS \"" + column.getLabel() + "\"";
                        case 69:
                            return "COALESCE((SELECT d.des_clave FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_fraude_id = d.des_id), '-') AS \"" + column.getLabel() + "\"";
                        case 70:
                            return "COALESCE((SELECT d.des_clave FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_legal_id = d.des_id), '-') AS \"" + column.getLabel() + "\"";
                        case 71:
                            return "COALESCE((SELECT d.des_clave FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_reputacional_id = d.des_id), '-') AS \"" + column.getLabel() + "\"";
                        case 72:
                            return "COALESCE((SELECT d.des_clave FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_cumplimiento_id = d.des_id), '-') AS \"" + column.getLabel() + "\"";
                        case 73:
                            return "COALESCE((SELECT d.des_clave FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_estrategico_id = d.des_id), '-') AS \"" + column.getLabel() + "\"";
                        case 74:
                            return "COALESCE((SELECT d.des_clave FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_gobierno_id = d.des_id), '-') AS \"" + column.getLabel() + "\"";
                        case 75:
                            return "COALESCE(e.eve_codigo_inicial, '-') AS \"" + column.getLabel() + "\"";
                        case 76:
                            return "COALESCE((SELECT d.des_nombre FROM riesgos.tbl_tabla_descripcion d WHERE e.eve_subcategorizacion_id = d.des_id), '-') AS \"" + column.getLabel() + "\"";
                        case 77:
                            return "COALESCE(e.eve_trimestre, '-') AS \"" + column.getLabel() + "\"";
                        default:
                            return null;
                    }
                })
                .filter(column -> column != null)
                .collect(Collectors.toList());

        query.append(String.join(", ", columns));
        query.append("FROM riesgos.tbl_evento_riesgo e ");
        query.append("WHERE e.eve_fecha_desc >= ? AND e.eve_fecha_desc <= ? ORDER BY e.eve_id ASC");

        return jdbcTemplate.queryForList(query.toString(), filter.getDataFilter().getFechaDesde(), filter.getDataFilter().getFechaHasta());
    }

}
