package atc.riesgos.model.dto.report.ciro.eventos;

import atc.riesgos.model.dto.report.ciro.MainReport;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ReporteEventoGralDTO {

    private String codigo;
    private String descripcion;
    private String estadoEvento;
    private Date fechaDesc;
    private Date fechaFin;

    public ReporteEventoGralDTO(String codigo, String descripcion, String estadoEvento, Date fechaDesc, Date fechaFin) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.estadoEvento = estadoEvento;
        this.fechaDesc = fechaDesc;
        this.fechaFin = fechaFin;
    }

}
