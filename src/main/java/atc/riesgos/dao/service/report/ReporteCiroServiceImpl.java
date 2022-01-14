package atc.riesgos.dao.service.report;

import atc.riesgos.config.log.Log;
import atc.riesgos.model.dto.report.ciro.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static atc.riesgos.util.JPQL.*;

@Service
public class ReporteCiroServiceImpl implements ReporteCiroService {

    @PersistenceContext
    protected EntityManager manager;

    //2.1. Evento de Riesgo Operativo
    public List<ReportADTO> reportARiesgoOperativo(){

        List<ReportADTO> reportList = new ArrayList<>();

        try {
            Query query = manager.createNativeQuery(riesgoOperativoA);
            List<Object[]> result = query.getResultList();

            result.forEach(row -> {
                reportList.add(new ReportADTO(row));
            });

            return reportList;
        } catch (Exception e) {
            Log.error("Error generando reporte Riesgo Operativo - A ==> ", e);
        }
        return reportList;


    }

    //2.2.  Cuentas Contables (Revisar con el solicitante)
    public List<ReportBDTO> reportBCuentasContables() {

        List<ReportBDTO> reportList = new ArrayList<>();

        try {
            Query query = manager.createNativeQuery(cuentasContablesB);
            List<Object[]> result = query.getResultList();

            result.forEach(row -> {
                reportList.add(new ReportBDTO(row));
            });

            return reportList;
        } catch (Exception e) {
            Log.error("Error generando reporte Tipo de evento - B ==> ", e);
        }
        return reportList;

    }


    // 2.3. Tipo de evento
    public List<ReportCDTO> reportCTipoEvento() {
        List<ReportCDTO> reportList = new ArrayList<>();

        try {
            Query query = manager.createNativeQuery(tipoEventoC);
            //query.setParameter("tipoServicio", "S002");
            List<Object[]> result = query.getResultList();

            result.forEach(row -> {
                reportList.add(new ReportCDTO(row));
            });

            return reportList;
        } catch (Exception e) {
            Log.error("Error generando reporte Tipo de evento - C ==> ", e);
        }


        return reportList;
    }



    //2.4. Punto de Atención Financiera (PAF)
    public List<ReportDDTO> reportDAtencionFinanciera(){
        List<ReportDDTO> reportList = new ArrayList<>();

        try {
            Query query = manager.createNativeQuery(puntosAtencionD);
            List<Object[]> result = query.getResultList();

            result.forEach(row -> {
                reportList.add(new ReportDDTO(row));
            });

            return reportList;
        } catch (Exception e) {
            Log.error("Error generando reporte Atencion Financiera - D ==> ", e);
        }
        return reportList;
    }


    //2.5. Canal
    public List<ReportEDTO> reportECanal(){
        List<ReportEDTO> reportList = new ArrayList<>();

        try {
            Query query = manager.createNativeQuery(canalE);
            List<Object[]> result = query.getResultList();

            result.forEach(row -> {
                reportList.add(new ReportEDTO(row));
            });

            return reportList;
        } catch (Exception e) {
            Log.error("Error generando reporte Canal - E ==> ", e);
        }
        return reportList;
    }

    //2.6. Proceso
    public List<ReportFDTO> reportFProceso(){
        List<ReportFDTO> reportList = new ArrayList<>();

        try {
            Query query = manager.createNativeQuery(procesoF);
            List<Object[]> result = query.getResultList();

            result.forEach(row -> {
                reportList.add(new ReportFDTO(row));
            });

            return reportList;
        } catch (Exception e) {
            Log.error("Error generando reporte Proceso - F ==> ", e);
        }
        return reportList;

    }


    //2.7. Operacion
    public List<ReportGDTO> reportGOperacion(){
        List<ReportGDTO> reportList = new ArrayList<>();

        try {
            Query query = manager.createNativeQuery(operacionG);
            List<Object[]> result = query.getResultList();

            result.forEach(row -> {
                reportList.add(new ReportGDTO(row));
            });

            return reportList;
        } catch (Exception e) {
            Log.error("Error generando reporte Operacion - G ==> ", e);
        }
        return reportList;
    }


    //2.8. Lugar
    public List<ReportHDTO> reportHLugar(){
        List<ReportHDTO> reportList = new ArrayList<>();

        try {
            Query query = manager.createNativeQuery(lugarH);
            List<Object[]> result = query.getResultList();

            result.forEach(row -> {
                reportList.add(new ReportHDTO(row));
            });

            return reportList;
        } catch (Exception e) {
            Log.error("Error generando reporte Lugar - H ==> ", e);
        }
        return reportList;

    }

    //2.9. Linea de negocio
    public List<ReportIDTO> reportILineaNegocio(){
        List<ReportIDTO> reportList = new ArrayList<>();

        try {
            Query query = manager.createNativeQuery(lineaNegocioI);
            List<Object[]> result = query.getResultList();

            result.forEach(row -> {
                reportList.add(new ReportIDTO(row));
            });

            return reportList;
        } catch (Exception e) {
            Log.error("Error generando reporte Linea de negocio - I ==> ", e);
        }
        return reportList;

    }


}
