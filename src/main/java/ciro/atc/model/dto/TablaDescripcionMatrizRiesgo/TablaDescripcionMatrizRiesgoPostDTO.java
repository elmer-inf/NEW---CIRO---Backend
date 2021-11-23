package ciro.atc.model.dto.TablaDescripcionMatrizRiesgo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Setter
@Getter

public class TablaDescripcionMatrizRiesgoPostDTO {

    private String nombre;
    private String campoA;
    private String campoB;
    private String campoC;
    private String campoD;
    private Float campoE;
    private Float campoF;
    private int usuarioId;
    private Long tablaId;
}
