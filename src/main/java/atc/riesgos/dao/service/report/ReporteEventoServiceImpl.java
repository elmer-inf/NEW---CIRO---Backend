package atc.riesgos.dao.service.report;

import atc.riesgos.config.log.Log;
import atc.riesgos.model.dto.report.ciro.eventos.FiltroReporteAuditoria;
import atc.riesgos.model.dto.report.ciro.eventos.FiltroReporteEvento;
import atc.riesgos.model.dto.report.ciro.eventos.ReporteEventoGralDTO;
import atc.riesgos.model.entity.EventoRiesgo;
import atc.riesgos.model.entity.MatrizRiesgo;
import atc.riesgos.model.entity.TablaDescripcion;
import atc.riesgos.model.repository.EventoRiesgoRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class ReporteEventoServiceImpl implements ReporteEventoService {

    @Autowired
    EventoRiesgoRepository eventoRiesgoRepository;

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

}
