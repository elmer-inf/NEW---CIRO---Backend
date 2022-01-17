package atc.riesgos.dao.service.report;

import atc.riesgos.model.dto.report.ciro.*;

import java.util.List;

public interface ReporteCiroService {

    //List<Long> getIdEventoToReport (DatesForReport data);
    List<ReportADTO> reportARiesgoOperativo(DatesForReport data);
    List<ReportBDTO> reportBCuentasContables(DatesForReport data);
    List<ReportCDTO> reportCTipoEvento(DatesForReport data);
    List<ReportDDTO> reportDAtencionFinanciera(DatesForReport data);
    List<ReportEDTO> reportECanal(DatesForReport data);
    List<ReportFDTO> reportFProceso(DatesForReport data);
    List<ReportGDTO> reportGOperacion(DatesForReport data);
    List<ReportHDTO> reportHLugar(DatesForReport data);
    List<ReportIDTO> reportILineaNegocio(DatesForReport data);
}
