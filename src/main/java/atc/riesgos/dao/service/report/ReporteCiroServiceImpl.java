package atc.riesgos.dao.service.report;

import atc.riesgos.config.log.Log;
import atc.riesgos.model.dto.report.ciro.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static atc.riesgos.util.JPQL.*;

@Service
public class ReporteCiroServiceImpl implements ReporteCiroService {

    @PersistenceContext
    protected EntityManager manager;


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


    //2.1. Evento de Riesgo Operativo
    public List<ReportADTO> reportARiesgoOperativo(DatesForReport data) {

        List<ReportADTO> reportList = new ArrayList<>();
        List<Long> in = getIdEventoToReport(data);

        try {
            Query query = manager.createNativeQuery(riesgoOperativoA);
            query.setParameter("idEventos", in);
            List<Object[]> result = query.getResultList();
            String fechaCorte = Log.convertDate(data.getFechaFinTrim());

            result.forEach(row -> {
                reportList.add(new ReportADTO(row,fechaCorte));
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
            result.forEach(row -> {
                reportList.add(new ReportBDTO(row, fechaCorte));
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

            result.forEach(row -> {
                reportList.add(new ReportCDTO(row, fechaCorte));
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

            result.forEach(row -> {
                reportList.add(new ReportDDTO(row,fechaCorte));
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

            result.forEach(row -> {
                reportList.add(new ReportEDTO(row,fechaCorte));
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

            result.forEach(row -> {
                reportList.add(new ReportFDTO(row, fechaCorte));
            });

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

            result.forEach(row -> {
                reportList.add(new ReportGDTO(row,fechaCorte));
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

            result.forEach(row -> {
                reportList.add(new ReportHDTO(row,fechaCorte));
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

            result.forEach(row -> {
                reportList.add(new ReportIDTO(row,fechaCorte));
            });

            return reportList;
        } catch (Exception e) {
            Log.error("Error generando reporte Linea de negocio - I ==> ", e);
        }
        return reportList;

    }


}
