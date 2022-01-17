package atc.riesgos.model.dto.report.ciro;

// 2.8. Lugar H
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReportHDTO extends MainReport{

    private String lugar;

    public ReportHDTO() {
    }

    public ReportHDTO(String lugar) {
        this.lugar = lugar;
    }

    public ReportHDTO (Integer idRegister, Object[] columns, String fechaCorteSend){
        id = idRegister;
        codigoEnvio = (String) columns[0];
        fechaCorte = fechaCorteSend;
        codigoEvento = (String) columns[1];
        this.lugar = (String) columns[2];
        tipoEnvio = (String) columns[3];
    }
}
