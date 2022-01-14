package atc.riesgos.model.dto.report.ciro;

// 2.3. Tipo de evento

import lombok.Getter;
import lombok.Setter;

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


    public ReportCDTO(Object[] columns) {
        codigoEnvio = (String) columns[0];
        fechaCorte = (String) columns[1];
        codigoEvento = (String) columns[2];
        this.tipoEvento = (String) columns[3];
        this.descripcionTipoEvento = (String) columns[4];
        tipoEnvio = (String) columns[5];

    }

}
