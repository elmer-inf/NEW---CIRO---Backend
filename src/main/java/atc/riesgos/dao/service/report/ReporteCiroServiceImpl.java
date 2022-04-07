package atc.riesgos.dao.service.report;

import atc.riesgos.config.log.Log;
import atc.riesgos.model.dto.report.ciro.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static atc.riesgos.util.JPQL.*;

@Service
public class ReporteCiroServiceImpl implements ReporteCiroService {

    @PersistenceContext
    protected EntityManager manager;
    private String textFormatA = "%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s\n";
    private String textFormatB = "%s|%s|%s|%s|%s|%s\n";
    private String textFormatC = "%s|%s|%s|%s|%s|%s\n";
    private String textFormatD = "%s|%s|%s|%s|%s\n";
    private String textFormatE = "%s|%s|%s|%s|%s\n";
    private String textFormatF = "%s|%s|%s|%s|%s|%s|%s\n";
    private String textFormatG = "%s|%s|%s|%s|%s\n";
    private String textFormatH = "%s|%s|%s|%s|%s\n";
    private String textFormatI = "%s|%s|%s|%s|%s|%s\n";


    private List<Long> getIdEventoToReport(DatesForReport data) {
        Query query = manager.createNativeQuery(byIdEvento);
        query.setParameter("fechaIniTrimestre", data.getFechaIniTrim());
        query.setParameter("fechaFinTrimestre", data.getFechaFinTrim());
        List<String> result = query.getResultList();

        List<Long> newListIds = new ArrayList<>();

        String formatterIn = Log.toJSON(result);
        // System.out.println("dddd: " + formatterIn);

        result.forEach(row -> {
            // System.out.println("rrroww: " + row);
            newListIds.add(new Long(row));
        });
        // String formatterIn = Log.toJSON(result);
        //String s1 = formatterIn.replace("[","(");
        // String s2 = s1.replace("]",")");
        // return formatterIn;
        return newListIds;

    }

    public DownloadAllReportDTO generateAllFiles(DatesForReport data) {
        DownloadAllReportDTO allReportDTO = new DownloadAllReportDTO();

        List<ReportADTO> reportListA = reportARiesgoOperativo(data);
        List<ReportBDTO> reportListB = reportBCuentasContables(data);
        List<ReportCDTO> reportListC = reportCTipoEvento(data);
        List<ReportDDTO> reportListD = reportDAtencionFinanciera(data);
        List<ReportEDTO> reportListE = reportECanal(data);
        List<ReportFDTO> reportListF = reportFProceso(data);
        List<ReportGDTO> reportListG = reportGOperacion(data);
        List<ReportHDTO> reportListH = reportHLugar(data);
        List<ReportIDTO> reportListI = reportILineaNegocio(data);
        System.out.println("LOggg F" + Log.toJSON(reportListF));;

        String reportA = "";
        String reportB = "";
        String reportC = "";
        String reportD = "";
        String reportE = "";
        String reportF = "";
        String reportG = "";
        String reportH = "";
        String reportI = "";

        for (ReportADTO a : reportListA) {
            reportA = reportA + String.format(textFormatA, a.getCodigoEnvio(),a.getFechaCorte(), a.getCodigoEvento(),a.getTipoEntidad(),
                                                            a.getDescripcionResumida(), a.getFactorRiesgo(),a.getCargoInvolucrado(),
                    a.getAreaInvolucrada(),  a.getCategoria(),a.getPerdidaRiesgoOperativoContable(), a.getPerdidaRiesgoOperativoMercado(),
                    a.getGastoAsociadoPerdida(), a.getMontoTotalRecuperado(), a.getMontoRecuperadoCoberturaSeguro(), a.getRecuperacionActivo(),
                    a.getRelacionRiesgoCredito(),a.getEventoCritico(), a.getDetalleEventoCritico(), a.getMonedaMontoEvento(), a.getFechaDescubrimiento(),
                    a.getHoraDescubrimiento(), a.getFechaInicio(), a.getHoraInicio(),a.getFechaFinalizacion(), a.getHoraFinalizacion(),
                    a.getElaborador(), a.getRevisor(), a.getRevisor(), a.getAprobador(),a.getEstadoEvento(), a.getDetalleEstadoEvento(),
                    a.getCodigoEventoRelacionado(), a.getTipoEnvio());

        }

        for (ReportBDTO b : reportListB) {
            reportB = reportB + String.format(textFormatB,b.getCodigoEnvio(),b.getFechaCorte(), b.getCodigoEvento(), b.getCuentaContable(), b.getFechaRegistroCuenta(),b.getTipoEnvio());
        }

        for (ReportCDTO c : reportListC) {
            reportC = reportC + String.format(textFormatC,c.getCodigoEnvio(),c.getFechaCorte(), c.getCodigoEvento(), c.getTipoEvento(), c.getDescripcionTipoEvento() , c.getTipoEnvio());
        }
        for (ReportDDTO d : reportListD) {
            reportD = reportD + String.format(textFormatD,d.getCodigoEnvio(),d.getFechaCorte(), d.getCodigoEvento(), d.getCodigoPaf(), d.getTipoEnvio());
        }


        for (ReportEDTO e : reportListE) {
            reportE = reportE + String.format(textFormatE,e.getCodigoEnvio(),e.getFechaCorte(), e.getCodigoEvento(), e.getCanal() ,e.getTipoEnvio() );
        }

        for (ReportFDTO f : reportListF) {
            reportF = reportF + String.format(textFormatF, f.getCodigoEnvio(),f.getFechaCorte(), f.getCodigoEvento(),
                    f.getProceso(), f.getProcesoCritico(), f.getDetalleEventoCritico(), f.getTipoEnvio());

        }
        for (ReportGDTO g : reportListG) {
            reportG = reportG + String.format(textFormatG,g.getCodigoEnvio(),g.getFechaCorte(), g.getCodigoEvento(), g.getOperacion() ,g.getTipoEnvio());

        }
        for (ReportHDTO h : reportListH) {
            reportH = reportH + String.format(textFormatH, h.getCodigoEnvio(),h.getFechaCorte(), h.getCodigoEvento(),h.getLugar() ,h.getTipoEnvio());

        }
        for (ReportIDTO i : reportListI) {
            reportI = reportI + String.format(textFormatI, i.getCodigoEnvio(),i.getFechaCorte(), i.getCodigoEvento(), i.getLineaNegocio(), i.getLineaNegocioNivel3() , i.getTipoEnvio());
        }

        //Field[] fieldA = ReportADTO.class.getFields();

        allReportDTO.setReportA(reportA.replaceAll("null",""));
        allReportDTO.setReportB(reportB.replaceAll("null",""));
        allReportDTO.setReportC(reportC.replaceAll("null",""));
        allReportDTO.setReportD(reportD.replaceAll("null",""));
        allReportDTO.setReportE(reportE.replaceAll("null",""));
        allReportDTO.setReportF(reportF.replaceAll("null",""));
        allReportDTO.setReportG(reportG.replaceAll("null",""));
        allReportDTO.setReportH(reportH.replaceAll("null",""));
        allReportDTO.setReportI(reportI.replaceAll("null",""));

        return allReportDTO;
    }


    //2.1. Evento de Riesgo Operativo
    public List<ReportADTO> reportARiesgoOperativo(DatesForReport data) {

        List<ReportADTO> reportList = new ArrayList<>();
        List<Long> in = getIdEventoToReport(data);
        try {
            Query query = manager.createNativeQuery(riesgoOperativoA);
            query.setParameter("idEventos", in);

            List<Object[]> result = query.getResultList();
            String fechaCorte = Log.convertDate(data.getFechaFinTrim());

            AtomicReference<Integer> i = new AtomicReference<>(1);

            result.forEach(row -> {
                reportList.add(new ReportADTO(i.getAndSet(i.get() + 1), row, fechaCorte));
            });

            return reportList;
        } catch (Exception e) {
            Log.error("Error generando reporte Riesgo Operativo - A ==> ", e);
        }
        return reportList;


    }

    //2.2.  Cuentas Contables (Revisar con el solicitante)
    public List<ReportBDTO> reportBCuentasContables(DatesForReport data) {

        List<ReportBDTO> reportList = new ArrayList<>();
        List<Long> in = getIdEventoToReport(data);

        try {
            Query query = manager.createNativeQuery(cuentasContablesB);
            query.setParameter("idEventos", in);
            List<Object[]> result = query.getResultList();
            String fechaCorte = Log.convertDate(data.getFechaFinTrim());
            AtomicReference<Integer> i = new AtomicReference<>(1);

            result.forEach(row -> {
                reportList.add(new ReportBDTO(i.getAndSet(i.get() + 1), row, fechaCorte));
            });

            return reportList;
        } catch (Exception e) {
            Log.error("Error generando reporte Tipo de evento - B ==> ", e);
        }
        return reportList;

    }


    // 2.3. Tipo de evento
    public List<ReportCDTO> reportCTipoEvento(DatesForReport data) {
        List<ReportCDTO> reportList = new ArrayList<>();

        List<Long> in = getIdEventoToReport(data);

        try {
            Query query = manager.createNativeQuery(tipoEventoC);
            query.setParameter("idEventos", in);
            List<Object[]> result = query.getResultList();
            String fechaCorte = Log.convertDate(data.getFechaFinTrim());
            AtomicReference<Integer> i = new AtomicReference<>(1);

            result.forEach(row -> {
                reportList.add(new ReportCDTO(i.getAndSet(i.get() + 1), row, fechaCorte));
            });

            return reportList;
        } catch (Exception e) {
            Log.error("Error generando reporte Tipo de evento - C ==> ", e);
        }


        return reportList;
    }


    //2.4. Punto de Atención Financiera (PAF)
    public List<ReportDDTO> reportDAtencionFinanciera(DatesForReport data) {
        List<ReportDDTO> reportList = new ArrayList<>();
        List<Long> in = getIdEventoToReport(data);

        try {
            Query query = manager.createNativeQuery(puntosAtencionD);
            query.setParameter("idEventos", in);

            List<Object[]> result = query.getResultList();
            String fechaCorte = Log.convertDate(data.getFechaFinTrim());
            AtomicReference<Integer> i = new AtomicReference<>(1);

            result.forEach(row -> {
                reportList.add(new ReportDDTO(i.getAndSet(i.get() + 1), row, fechaCorte));
            });

            return reportList;
        } catch (Exception e) {
            Log.error("Error generando reporte Atencion Financiera - D ==> ", e);
        }
        return reportList;
    }


    //2.5. Canal
    public List<ReportEDTO> reportECanal(DatesForReport data) {
        List<ReportEDTO> reportList = new ArrayList<>();
        List<Long> in = getIdEventoToReport(data);

        try {
            Query query = manager.createNativeQuery(canalE);
            query.setParameter("idEventos", in);

            List<Object[]> result = query.getResultList();
            String fechaCorte = Log.convertDate(data.getFechaFinTrim());
            AtomicReference<Integer> i = new AtomicReference<>(1);

            result.forEach(row -> {
                reportList.add(new ReportEDTO(i.getAndSet(i.get() + 1), row, fechaCorte));
            });

            return reportList;
        } catch (Exception e) {
            Log.error("Error generando reporte Canal - E ==> ", e);
        }
        return reportList;
    }

    //2.6. Proceso
    public List<ReportFDTO> reportFProceso(DatesForReport data) {
        List<ReportFDTO> reportList = new ArrayList<>();
        List<Long> in = getIdEventoToReport(data);

        try {
            Query query = manager.createNativeQuery(procesoF);
            query.setParameter("idEventos", in);

            List<Object[]> result = query.getResultList();
            String fechaCorte = Log.convertDate(data.getFechaFinTrim());
            AtomicReference<Integer> i = new AtomicReference<>(1);

            result.forEach(row -> {
                reportList.add(new ReportFDTO(i.getAndSet(i.get() + 1), row, fechaCorte));
                System.out.println("result: " + Log.toJSON(row));
            });
            System.out.println("result: " + Log.toJSON(reportList));
            return reportList;
        } catch (Exception e) {
            Log.error("Error generando reporte Proceso - F ==> ", e);
        }
        return reportList;

    }


    //2.7. Operacion
    public List<ReportGDTO> reportGOperacion(DatesForReport data) {
        List<ReportGDTO> reportList = new ArrayList<>();
        List<Long> in = getIdEventoToReport(data);

        try {
            Query query = manager.createNativeQuery(operacionG);
            query.setParameter("idEventos", in);

            List<Object[]> result = query.getResultList();
            String fechaCorte = Log.convertDate(data.getFechaFinTrim());
            AtomicReference<Integer> i = new AtomicReference<>(1);

            result.forEach(row -> {
                reportList.add(new ReportGDTO(i.getAndSet(i.get() + 1), row, fechaCorte));
            });

            return reportList;
        } catch (Exception e) {
            Log.error("Error generando reporte Operacion - G ==> ", e);
        }
        return reportList;
    }


    //2.8. Lugar
    public List<ReportHDTO> reportHLugar(DatesForReport data) {
        List<ReportHDTO> reportList = new ArrayList<>();
        List<Long> in = getIdEventoToReport(data);

        try {
            Query query = manager.createNativeQuery(lugarH);
            query.setParameter("idEventos", in);

            List<Object[]> result = query.getResultList();
            String fechaCorte = Log.convertDate(data.getFechaFinTrim());
            AtomicReference<Integer> i = new AtomicReference<>(1);

            result.forEach(row -> {
                reportList.add(new ReportHDTO(i.getAndSet(i.get() + 1), row, fechaCorte));
            });

            return reportList;
        } catch (Exception e) {
            Log.error("Error generando reporte Lugar - H ==> ", e);
        }
        return reportList;

    }

    //2.9. Linea de negocio
    public List<ReportIDTO> reportILineaNegocio(DatesForReport data) {
        List<ReportIDTO> reportList = new ArrayList<>();
        List<Long> in = getIdEventoToReport(data);

        try {
            Query query = manager.createNativeQuery(lineaNegocioI);
            query.setParameter("idEventos", in);

            List<Object[]> result = query.getResultList();
            String fechaCorte = Log.convertDate(data.getFechaFinTrim());
            AtomicReference<Integer> i = new AtomicReference<>(1);

            result.forEach(row -> {
                reportList.add(new ReportIDTO(i.getAndSet(i.get() + 1), row, fechaCorte));
            });

            return reportList;
        } catch (Exception e) {
            Log.error("Error generando reporte Linea de negocio - I ==> ", e);
        }
        return reportList;

    }


}
