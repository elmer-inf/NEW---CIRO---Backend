package atc.riesgos.model.dto.report.ciro;
// 2.9 Linea de negocio I
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReportIDTO extends MainReport{
    private String lineaNegocio;
    private String lineaNegocioNivel3;

    public ReportIDTO() {
    }


    public ReportIDTO(String lineaNegocio, String lineaNegocioNivel3) {
        this.lineaNegocio = lineaNegocio;
        this.lineaNegocioNivel3 = lineaNegocioNivel3;
    }

    public ReportIDTO(Integer idRegister, Object[] columns, String fechaCorteSend) {
        id = idRegister;
        codigoEnvio = (String) columns[0];
        fechaCorte = fechaCorteSend;
        codigoEvento = (String) columns[1];
        this.lineaNegocio = (String) columns[2];
        this.lineaNegocioNivel3 = (String) columns[3];
        tipoEnvio = (String) columns[4];
    }

}
