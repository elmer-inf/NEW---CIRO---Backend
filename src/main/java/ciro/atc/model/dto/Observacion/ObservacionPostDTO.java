package ciro.atc.model.dto.Observacion;

import ciro.atc.model.entity.EventoRiesgo;
import ciro.atc.model.entity.MatrizRiesgo;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class ObservacionPostDTO {

    private String listaObservacion;
    private String nota;
    private String estado;
    private String modulo;
    private Long eventoId;
    private Long matrizRiesgoId;
    private int usuarioId;
}
