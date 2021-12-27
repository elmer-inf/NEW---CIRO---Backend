package atc.riesgos.model.dto.TablaDescripcionMatrizOportunidad;

import atc.riesgos.model.entity.TablaDescripcionMatrizOportunidad;
import atc.riesgos.model.entity.TablaListaMatrizOportunidad;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class TablaDescripcionMatrizOportunidadGetDTO2 {
    private Long id;
    private String nombre;
    private String campoA;
    private String campoB;
    private String campoC;
    private String campoD;
    private int usuarioId;
    private TablaListaMatrizOportunidad tablaId;
    private TablaDescripcionMatrizOportunidad nivel2Id;
}
