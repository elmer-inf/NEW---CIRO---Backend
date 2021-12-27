package atc.riesgos.model.dto.MatrizOportunidad;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class MatrizOportunidadPutDTOevaluacion {

    private String estadoRegistro;

    private String listaObservacion;
    private String nota;
    private String estado;
    private String modulo;
    private Long matrizOportunidadId;

}
