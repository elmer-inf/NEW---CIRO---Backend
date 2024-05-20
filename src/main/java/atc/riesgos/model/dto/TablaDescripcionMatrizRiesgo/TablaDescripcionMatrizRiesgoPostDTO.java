package atc.riesgos.model.dto.TablaDescripcionMatrizRiesgo;

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
    private String campoG;
    private int usuarioId;
    private Long tablaId;
    private int nivel2_id;
}
