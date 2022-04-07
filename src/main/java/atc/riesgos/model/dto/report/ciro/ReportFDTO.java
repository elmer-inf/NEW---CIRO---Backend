package atc.riesgos.model.dto.report.ciro;
//2.6. Proceso F
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReportFDTO extends MainReport{
    private String proceso;
    private Integer procesoCritico;
    private String detalleEventoCritico;


    public ReportFDTO() {
    }


    public ReportFDTO(String proceso, Integer procesoCritico, String detalleEventoCritico) {
        this.proceso = proceso;
        this.procesoCritico = procesoCritico;
        this.detalleEventoCritico = detalleEventoCritico;
    }

    public ReportFDTO(Integer idRegister,  Object[] columns, String fechaCorteSend) {
        id = idRegister;
        codigoEnvio = (String) columns[0];
        fechaCorte = fechaCorteSend;
        codigoEvento = (String) columns[1];
        this.proceso = (String) columns[2];
        this.procesoCritico = (Integer) columns[3];
        this.detalleEventoCritico = (String) columns[4];
        tipoEnvio = (String) columns[5];
    }

}
