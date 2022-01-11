package atc.riesgos.model.dto.MatrizRiesgo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class MatrizRiesgoPutDTOevaluacion {

    private String estadoRegistro;

    private String listaObservacion;
    private String nota;
    private String estado;
    private String modulo;
    private Long matrizRiesgoId;
}
