package atc.riesgos.dao.service.report;

import atc.riesgos.model.dto.report.ciro.*;

import java.util.List;

public interface ReporteCiroService {
    List<ReportADTO> reportARiesgoOperativo();
    List<ReportBDTO> reportBCuentasContables();
    List<ReportCDTO> reportCTipoEvento();
    List<ReportDDTO> reportDAtencionFinanciera();
    List<ReportEDTO> reportECanal();
    List<ReportFDTO> reportFProceso();
    List<ReportGDTO> reportGOperacion();
    List<ReportHDTO> reportHLugar();
    List<ReportIDTO> reportILineaNegocio();
}
