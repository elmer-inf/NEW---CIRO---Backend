package atc.riesgos.model.dto.ControlRecurrenciasRiesgo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter

public class ControlRecurrenciasRiesgo {

    private Long id;
    private Long idRiesgo;
    private Long idEvento1;
    private String codigoEvento1;
    private Long idEvento2;
    private String codigoEvento2;
    private Long idEvento3;
    private String codigoEvento3;
    private boolean correoEnviado;
    private Date fechaEnvio;

    public ControlRecurrenciasRiesgo(Long id, Long idRiesgo, Long idEvento1, String codigoEvento1, Long idEvento2, String codigoEvento2, Long idEvento3, String codigoEvento3, boolean correoEnviado, Date fechaEnvio) {
        this.id = id;
        this.idRiesgo = idRiesgo;
        this.idEvento1 = idEvento1;
        this.codigoEvento1 = codigoEvento1;
        this.idEvento2 = idEvento2;
        this.codigoEvento2 = codigoEvento2;
        this.idEvento3 = idEvento3;
        this.codigoEvento3 = codigoEvento3;
        this.correoEnviado = correoEnviado;
        this.fechaEnvio = fechaEnvio;
    }
}