package ciro.atc.model.dto.TablaDescripcionSeguridad;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class TablaDescripcionSeguridadPostDTO {

    private String nombre;
    private int usuarioId;
    private Long tablaId;
}
