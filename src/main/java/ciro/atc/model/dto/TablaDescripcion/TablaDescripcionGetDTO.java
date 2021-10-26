package ciro.atc.model.dto.TablaDescripcion;

import ciro.atc.model.entity.TablaLista;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class TablaDescripcionGetDTO {

    private Long id;
    private String clave;
    private String nombre;
    private String descripcion;
    private int usuario_id;
    private TablaLista tablaLista;
    private int nivel2_id;
    private int nivel3_id;

}
