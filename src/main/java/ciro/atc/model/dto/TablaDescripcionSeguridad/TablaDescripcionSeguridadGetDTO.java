package ciro.atc.model.dto.TablaDescripcionSeguridad;

import ciro.atc.model.entity.TablaListaSeguridad;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class TablaDescripcionSeguridadGetDTO {
    private Long id;
    private String nombre;
    private int usuarioId;
    private TablaListaSeguridad tablaId;
}
