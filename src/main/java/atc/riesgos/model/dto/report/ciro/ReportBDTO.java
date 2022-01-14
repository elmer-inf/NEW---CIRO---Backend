package atc.riesgos.model.dto.report.ciro;
//2.2.  Cuentas Contables (Revisar con el solicitante)

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReportBDTO extends MainReport{
    private String cuentaContable;
    private String fechaRegistroCuenta;

    public ReportBDTO() {
    }

    public ReportBDTO(String cuentaContable, String fechaRegistroCuenta) {
        this.cuentaContable = cuentaContable;
        this.fechaRegistroCuenta = fechaRegistroCuenta;
    }


    public ReportBDTO(Object[] columns) {
        codigoEnvio = (String) columns[0];
        fechaCorte = (String) columns[1];
        codigoEvento = (String) columns[2];
        this.cuentaContable = (String) columns[3];
        this.fechaRegistroCuenta = (String) columns[4];
        tipoEnvio = (String) columns[5];

    }
}
