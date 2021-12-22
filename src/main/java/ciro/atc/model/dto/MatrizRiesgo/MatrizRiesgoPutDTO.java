package ciro.atc.model.dto.MatrizRiesgo;

import ciro.atc.model.entity.TablaDescripcion;
import ciro.atc.model.entity.TablaDescripcionMatrizRiesgo;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Setter
@Getter

public class MatrizRiesgoPutDTO {

    private Date fechaEvaluacion;
    private String identificadoOtro;
    private String definicion;
    private String causa;
    private String consecuencia;
    private String defConcatenado;
    private Boolean monetario;

    // Controles
    private Boolean controlesTiene;
    private String controles;
    private String controlObjetivo;
    private String controlComentario;
    // Planes
    private String planesAccion;

    // Seguimiento
    private Date seguimientoFecha;
    private String seguimientoObs;
    private String seguimientoComen;

    // Valoracion
    private String criterioImpacto;
    private String criterioprobabilidad;
    private Float impactoUSD;

    private Long areaId;
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
    private Long controlId;
}