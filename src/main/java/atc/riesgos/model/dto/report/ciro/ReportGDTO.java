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

    public ReportGDTO(Integer idRegister, Object[] columns,String fechaCorteSend) {
        id = idRegister;
        codigoEnvio = (String) columns[0];
        fechaCorte = fechaCorteSend;
        codigoEvento = (String) columns[1];
        this.operacion = (String) columns[2];
        tipoEnvio = (String) columns[3];
    }


}
