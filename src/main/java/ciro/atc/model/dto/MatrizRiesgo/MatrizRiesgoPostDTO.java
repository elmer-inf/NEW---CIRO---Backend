package ciro.atc.model.dto.MatrizRiesgo;

import ciro.atc.model.entity.TablaDescripcion;
import ciro.atc.model.entity.TablaDescripcionMatrizRiesgo;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter

public class MatrizRiesgoPostDTO {
    
    private String estadoRegistro;
    private Date fechaEvaluacion;
    private String identificadoOtro;
    private String definicion;
    private String causa;
    private String consecuencia;
    private String defConcatenado;
    private Boolean monetario;
    private Integer riesgoInherente;
    private String valorRiesgoInherente;

    private Long areaID;
    private Long unidadId;
    private Long procesoId;
    private Long procedimientoId;
    private Long duenoCargoId;
    private Long responsableCargoId;
    private Long identificadoId;
    private Long efectoPerdidaId;
    private Long perdidaAsfiId;
    private Long factorRiesgoId;
    private Long probabilidadId;
    private Long impactoId;

    private int usuario_id;
}
