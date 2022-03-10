package atc.riesgos.controller.report;

import atc.riesgos.dao.service.report.ReporteCiroService;
import atc.riesgos.model.dto.EventoRiesgo.EventoRiesgoPostDTO;
import atc.riesgos.model.dto.report.ciro.*;
import atc.riesgos.model.entity.EventoRiesgo;
//import org.json.JSONException;
//import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/reporte/ciro")
@CrossOrigin(origins = "*", maxAge = 20000)
public class ReporteCiroController {

    @Autowired
    ReporteCiroService reporteCiroService;


    @PostMapping("/allFiles")
    public DownloadAllReportDTO allFilesCiro(@Valid @RequestBody DatesForReport data) {
        return reporteCiroService.generateAllFiles(data);
    }

   /* @PostMapping("/byid")
   public List<Long> getIdEventoTest (@Valid @RequestBody DatesForReport data){
        //System.out.println("Demooooooooooooooooo: " + Log.toJSON(data));
        return reporteCiroService.getIdEventoToReport(data);
    }*/

    @PostMapping("/riesgooperativoA")
    public List<ReportADTO> riesgoOperativo(@Valid @RequestBody DatesForReport data) {
        return reporteCiroService.reportARiesgoOperativo(data);
    }



    @PostMapping("/cuentascontablesB")
    public List<ReportBDTO> cuentasContables(@Valid @RequestBody DatesForReport data) {
        return reporteCiroService.reportBCuentasContables(data);
    }



    @PostMapping("/tipoevebtoC")
    public List<ReportCDTO> reporteTipoEvento(@Valid @RequestBody DatesForReport data) {
        return reporteCiroService.reportCTipoEvento(data);
    }

    @PostMapping("/atencionfinancieraD")
    public List<ReportDDTO> atencionFinanciera(@Valid @RequestBody DatesForReport data) {
        return reporteCiroService.reportDAtencionFinanciera(data);
    }

    @PostMapping("/canalE")
    public List<ReportEDTO> canal(@Valid @RequestBody DatesForReport data) {
        return reporteCiroService.reportECanal(data);
    }

    @PostMapping("/procesoF")
    public List<ReportFDTO> proceso(@Valid @RequestBody DatesForReport data) {
        return reporteCiroService.reportFProceso(data);
    }

    @PostMapping("/operacionG")
    public List<ReportGDTO> opreacion(@Valid @RequestBody DatesForReport data) {
        return reporteCiroService.reportGOperacion(data);
    }

    @PostMapping("/lugarH")
    public List<ReportHDTO> lugar(@Valid @RequestBody DatesForReport data) {
        return reporteCiroService.reportHLugar(data);
    }

    @PostMapping("/lineanegocioI")
    public List<ReportIDTO> lineaNegocio(@Valid @RequestBody DatesForReport data) {
        return reporteCiroService.reportILineaNegocio(data);
    }


}
