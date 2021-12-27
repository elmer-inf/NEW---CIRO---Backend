package atc.riesgos.model.dto.TablaDescripcion;

import atc.riesgos.model.entity.TablaDescripcion;
import atc.riesgos.model.entity.TablaLista;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class TablaDescripcionGetDTO2 {

    private Long id;
    private String clave;
    private String nombre;
    private String descripcion;
    private String campoA;
    private String campoB;
    private String campoC;
    private String campoD;
    private String codigoAsfi;

    private int usuario_id;
    private TablaLista tablaLista;
    private TablaDescripcion nivel2_id;
    private TablaDescripcion nivel3_id;
}
