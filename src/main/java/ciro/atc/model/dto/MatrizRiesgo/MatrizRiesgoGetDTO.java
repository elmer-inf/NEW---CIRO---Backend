package ciro.atc.model.dto.MatrizRiesgo;

import ciro.atc.model.entity.Archivo;
import ciro.atc.model.entity.TablaDescripcion;
import ciro.atc.model.entity.TablaDescripcionMatrizRiesgo;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Setter
@Getter

public class MatrizRiesgoGetDTO {

    private Long id;
    private String codigo;
    private Integer idUnidadCorrelativo;
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
    private Boolean tieneControles;
    // Controles
    private String controlesTiene;
    private String controles;
    // Planes
    private String planesAccion;
    // Seguimiento
    private Date seguimientoFecha;
    private String seguimientoObs;
    private String seguimientoComen;

    private TablaDescripcion areaID;
    private TablaDescripcion unidadId;
    private TablaDescripcion procesoId;
    private TablaDescripcion procedimientoId;
    private TablaDescripcion duenoCargoId;
    private TablaDescripcion responsableCargoId;
    private TablaDescripcionMatrizRiesgo identificadoId;
    private TablaDescripcion efectoPerdidaId;
    private TablaDescripcionMatrizRiesgo perdidaAsfiId;
    private TablaDescripcion factorRiesgoId;
    private TablaDescripcionMatrizRiesgo probabilidadId;
    private TablaDescripcionMatrizRiesgo impactoId;

    private int usuario_id;

}
