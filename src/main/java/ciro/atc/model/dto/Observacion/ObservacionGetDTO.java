package ciro.atc.model.dto.Observacion;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class ObservacionGetDTO {
    private Long id;
    private String listaObservacion;
    private String nota;
    private String estado;
    private Long eventoId;
}
