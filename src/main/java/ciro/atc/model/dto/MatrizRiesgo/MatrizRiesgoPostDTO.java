package ciro.atc.model.dto.MatrizRiesgo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter

public class MatrizRiesgoPostDTO {
    
    private String estadoRegistro;
    private Date fechaEvaluacion;
    private String definicion;
    private String causa;
    private String consecuencia;
    private Boolean monetario;

    private Long areaID;
    private Long unidadId;
    private Long procesoId;
    private Long procedimientoId;
    private Long duenoCargoId;
    private Long responsableCargoId;
    private Long efectoPerdidaId;
    private Long factorRiesgoId;

    private int usuario_id;
}
