package atc.riesgos.model.dto.Reporte.riesgos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class FiltroReporteGerencialDTO {

    private Date fechaDesde;
    private Date fechaHasta;
}
