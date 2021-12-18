package ciro.atc.model.dto.TablaDescripcionMatrizOportunidad;

import ciro.atc.model.entity.TablaListaMatrizOportunidad;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class TablaDescripcionMatrizOportunidadGetDTO {
    private Long id;
    private String nombre;
    private String campoA;
    private String campoB;
    private String campoC;
    private String campoD;
    private int usuarioId;
    private TablaListaMatrizOportunidad tablaId;
    private int nivel2Id;
}
