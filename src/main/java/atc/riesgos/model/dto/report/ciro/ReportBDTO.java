package atc.riesgos.model.dto.report.ciro;
//2.2.  Cuentas Contables (Revisar con el solicitante)

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ReportBDTO extends MainReport{
    private String cuentaContable;
    private Date fechaRegistroCuenta;

    public ReportBDTO() {
    }

    public ReportBDTO(String cuentaContable, Date fechaRegistroCuenta) {
        this.cuentaContable = cuentaContable;
        this.fechaRegistroCuenta = fechaRegistroCuenta;
    }


    public ReportBDTO(Object[] columns, String fechaCorteSend) {
        codigoEnvio = (String) columns[0];
        fechaCorte = fechaCorteSend;
        codigoEvento = (String) columns[1];
        this.cuentaContable = (String) columns[2];
        this.fechaRegistroCuenta = (Date) columns[3];
        tipoEnvio = (String) columns[4];

    }
}
