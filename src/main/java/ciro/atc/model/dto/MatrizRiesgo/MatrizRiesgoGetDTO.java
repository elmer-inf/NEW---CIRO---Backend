package ciro.atc.model.dto.MatrizRiesgo;

import ciro.atc.model.entity.Archivo;
import ciro.atc.model.entity.EventoRiesgo;
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

    private TablaDescripcion areaId;
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
    private TablaDescripcionMatrizRiesgo controlId;

    // RELACION CON EVENTO DE RIESGO
    //private Boolean eventoMaterializado;
   // private EventoRiesgo eventoRiesgoId;
}
