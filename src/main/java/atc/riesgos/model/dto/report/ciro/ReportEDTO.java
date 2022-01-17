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


    public ReportEDTO(Integer idRegister, Object[] columns, String fechaCorteSend) {
        id = idRegister;
        codigoEnvio = (String) columns[0];
        fechaCorte = fechaCorteSend;
        codigoEvento = (String) columns[1];
        this.canal = (String) columns[2];
        tipoEnvio = (String) columns[3];


    }

}
