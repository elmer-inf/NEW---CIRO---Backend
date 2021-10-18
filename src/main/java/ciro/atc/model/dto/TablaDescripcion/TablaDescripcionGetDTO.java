package ciro.atc.model.dto.TablaDescripcion;

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
    private Long tablaLista;
    private int nivel2_id;
    private int nivel3_id;

}
