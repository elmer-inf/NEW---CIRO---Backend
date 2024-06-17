package atc.riesgos.model.dto.EventoRiesgo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class EventoRiesgoPutDTOevaluacion {

    private String estadoRegistro;
    private String listaObservacion;
    private String nota;
    private String estado;
    private String modulo;
    private Long eventoId;
}
