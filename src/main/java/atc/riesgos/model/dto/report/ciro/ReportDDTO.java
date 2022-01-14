package atc.riesgos.model.dto.report.ciro;
// 2.4. Punto de Atención Financiera (PAF) D
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReportDDTO extends MainReport{
    private String codigoPaf;

    public ReportDDTO(String codigoPaf) {
        this.codigoPaf = codigoPaf;
    }

    public ReportDDTO() {
    }


    public ReportDDTO(Object[] columns) {
        codigoEnvio = (String) columns[0];
        fechaCorte = (String) columns[1];
        codigoEvento = (String) columns[2];
        this.codigoPaf = (String) columns[3];
        tipoEnvio = (String) columns[4];

    }


}
