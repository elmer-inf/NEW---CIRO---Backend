package atc.riesgos.model.dto.report.ciro;

// 2.3. Tipo de evento

import atc.riesgos.config.log.Log;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ReportCDTO extends MainReport {

    private String tipoEvento;
    private String descripcionTipoEvento;


    public ReportCDTO() {
    }


    public ReportCDTO(String tipoEvento, String descripcionTipoEvento) {
        this.tipoEvento = tipoEvento;
        this.descripcionTipoEvento = descripcionTipoEvento;
    }


    public ReportCDTO(Integer idRegister, Object[] columns, String fechaCorteSend) {
        id = idRegister;
        codigoEnvio = (String) columns[0];
        fechaCorte = fechaCorteSend;
        codigoEvento = (String) columns[1];
        this.tipoEvento = (String) columns[2];
        this.descripcionTipoEvento = (String) columns[3];
        tipoEnvio = (String) columns[4];

    }

}
