package ciro.atc.model.dto.TablaDescripcionMatrizRiesgo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class TablaDescripcionMatrizRiesgoPutDTO {
    private String nombre;
    private String campoA;
    private String campoB;
    private String campoC;
    private String campoD;
    private Float campoE;
    private Float campoF;
    private String campoG;
    private int usuarioId;
    private Long tablaId;
}
