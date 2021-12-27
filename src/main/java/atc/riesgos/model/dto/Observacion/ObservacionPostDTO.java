package atc.riesgos.model.dto.Observacion;

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
    private Long matrizOportunidadId;
    private int usuarioId;
}
