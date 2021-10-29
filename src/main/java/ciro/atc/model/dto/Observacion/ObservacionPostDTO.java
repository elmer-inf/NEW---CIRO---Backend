package ciro.atc.model.dto.Observacion;

import ciro.atc.model.entity.EventoRiesgo;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class ObservacionPostDTO {

    private String listaObservacion;
    private String nota;
    private String estado;
    private Long eventoId;
    private int usuarioId;
}
