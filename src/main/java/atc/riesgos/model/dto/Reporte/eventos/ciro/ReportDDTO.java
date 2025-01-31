package atc.riesgos.model.dto.Reporte.eventos.ciro;
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


    public ReportDDTO(Integer idRegister, Object[] columns, String fechaCorteSend) {
        id = idRegister;
        codigoEnvio = (String) columns[0];
        fechaCorte = fechaCorteSend;
        codigoEvento = (String) columns[1];
        this.codigoPaf = (String) columns[2];
        tipoEnvio = (String) columns[3];

    }


}
