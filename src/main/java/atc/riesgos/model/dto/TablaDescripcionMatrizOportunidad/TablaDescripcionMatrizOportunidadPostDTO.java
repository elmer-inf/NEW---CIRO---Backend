package atc.riesgos.model.dto.TablaDescripcionMatrizOportunidad;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class TablaDescripcionMatrizOportunidadPostDTO {

    private String nombre;
    private String campoA;
    private String campoB;
    private String campoC;
    private String campoD;
    private int usuarioId;
    private Long tablaId;
    private int nivel2Id;
}
