package atc.riesgos.model.dto.TablaDescripcionMatrizRiesgo;

import atc.riesgos.model.entity.TablaListaMatrizRiesgo;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class TablaDescripcionMatrizRiesgoGetDTO {
    private Long id;
    private String nombre;
    private String campoA;
    private String campoB;
    private String campoC;
    private String campoD;
    private Float campoE;
    private Float campoF;
    private String campoG;
    private int usuarioId;
    private TablaListaMatrizRiesgo tablaId;
}
