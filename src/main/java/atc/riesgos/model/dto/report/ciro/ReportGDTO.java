package atc.riesgos.model.dto.report.ciro;
// 2.7. Operación G
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReportGDTO extends MainReport{
    private String operacion;

    public ReportGDTO() {
    }

    public ReportGDTO(String operacion) {
        this.operacion = operacion;
    }

    public ReportGDTO(Object[] columns) {
        codigoEnvio = (String) columns[0];
        fechaCorte = (String) columns[1];
        codigoEvento = (String) columns[2];
        this.operacion = (String) columns[3];
        tipoEnvio = (String) columns[4];
    }


}
