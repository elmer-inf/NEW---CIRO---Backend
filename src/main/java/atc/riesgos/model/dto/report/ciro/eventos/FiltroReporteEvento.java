package atc.riesgos.model.dto.report.ciro.eventos;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class FiltroReporteEvento {

    private Date fechaIni;
    private Date fechaDesc;
    private List<String> estadoEvento;
}
