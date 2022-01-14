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

    public ReportHDTO (Object[] columns){
        codigoEnvio = (String) columns[0];
        fechaCorte = (String) columns[1];
        codigoEvento = (String) columns[2];
        this.lugar = (String) columns[3];
        tipoEnvio = (String) columns[4];
    }
}
