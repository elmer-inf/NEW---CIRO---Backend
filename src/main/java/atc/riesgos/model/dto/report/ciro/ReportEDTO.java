package atc.riesgos.model.dto.report.ciro;

// 2.5. Canal

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReportEDTO extends MainReport {
    private String canal;

    public ReportEDTO() {
    }

    public ReportEDTO(String canal) {
        this.canal = canal;
    }


    public ReportEDTO(Object[] columns) {
        codigoEnvio = (String) columns[0];
        fechaCorte = (String) columns[1];
        codigoEvento = (String) columns[2];
        this.canal = (String) columns[3];
        tipoEnvio = (String) columns[4];


    }

}
