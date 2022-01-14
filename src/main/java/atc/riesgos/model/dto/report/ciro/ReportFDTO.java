package atc.riesgos.model.dto.report.ciro;
//2.6. Proceso F
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReportFDTO extends MainReport{
    private String proceso;
    private Integer procesoCritico;
    private String detalleProcesoCritico;


    public ReportFDTO() {
    }


    public ReportFDTO(String proceso, Integer procesoCritico, String detalleProcesoCritico) {
        this.proceso = proceso;
        this.procesoCritico = procesoCritico;
        this.detalleProcesoCritico = detalleProcesoCritico;
    }

    public ReportFDTO(Object[] columns) {
        codigoEnvio = (String) columns[0];
        fechaCorte = (String) columns[1];
        codigoEvento = (String) columns[2];
        this.proceso = (String) columns[3];
        this.procesoCritico = (Integer) columns[4];
        this.detalleProcesoCritico = (String) columns[5];
        tipoEnvio = (String) columns[6];
    }

}
