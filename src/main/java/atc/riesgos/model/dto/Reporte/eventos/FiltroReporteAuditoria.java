package atc.riesgos.model.dto.Reporte.eventos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class FiltroReporteAuditoria {

    private Date fechaDesde;
    private Date fechaHasta;
}
