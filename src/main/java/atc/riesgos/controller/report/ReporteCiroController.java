package atc.riesgos.controller.report;

import atc.riesgos.dao.service.report.ReporteCiroService;
import atc.riesgos.model.dto.report.ciro.*;
import atc.riesgos.model.entity.EventoRiesgo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/reporte/ciro")
@CrossOrigin(origins = "*", maxAge = 20000)
public class ReporteCiroController {

    @Autowired
    ReporteCiroService reporteCiroService;


    @GetMapping("/riesgooperativoA")
    public List<ReportADTO> riesgoOperativo() {
        return reporteCiroService.reportARiesgoOperativo();
    }



    @GetMapping("/cuentascontablesB")
    public List<ReportBDTO> cuentasContables() {
        return reporteCiroService.reportBCuentasContables();
    }



    @GetMapping("/tipoevebtoC")
    public List<ReportCDTO> reporteTipoEvento() {
        return reporteCiroService.reportCTipoEvento();
    }

    @GetMapping("/atencionfinancieraD")
    public List<ReportDDTO> atencionFinanciera() {
        return reporteCiroService.reportDAtencionFinanciera();
    }

    @GetMapping("/canalE")
    public List<ReportEDTO> canal() {
        return reporteCiroService.reportECanal();
    }

    @GetMapping("/procesoF")
    public List<ReportFDTO> proceso() {
        return reporteCiroService.reportFProceso();
    }

    @GetMapping("/operacionG")
    public List<ReportGDTO> opreacion() {
        return reporteCiroService.reportGOperacion();
    }

    @GetMapping("/lugarH")
    public List<ReportHDTO> lugar() {
        return reporteCiroService.reportHLugar();
    }

    @GetMapping("/lineanegocioI")
    public List<ReportIDTO> lineaNegocio() {
        return reporteCiroService.reportILineaNegocio();
    }


}
