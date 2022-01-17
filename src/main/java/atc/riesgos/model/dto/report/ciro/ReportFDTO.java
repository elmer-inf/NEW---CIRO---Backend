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

    public ReportFDTO(Object[] columns, String fechaCorteSend) {
        codigoEnvio = (String) columns[0];
        fechaCorte = fechaCorteSend;
        codigoEvento = (String) columns[1];
        this.proceso = (String) columns[2];
        this.procesoCritico = (Integer) columns[3];
        this.detalleProcesoCritico = (String) columns[4];
        tipoEnvio = (String) columns[5];
    }

}
