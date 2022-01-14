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

    public ReportIDTO(Object[] columns) {
        codigoEnvio = (String) columns[0];
        fechaCorte = (String) columns[1];
        codigoEvento = (String) columns[2];
        this.lineaNegocio = (String) columns[3];
        this.lineaNegocioNivel3 = (String) columns[4];
        tipoEnvio = (String) columns[5];
    }

}
