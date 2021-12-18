package ciro.atc.model.dto.MatrizOportunidad;

import ciro.atc.model.entity.TablaDescripcion;
import ciro.atc.model.entity.TablaDescripcionMatrizOportunidad;
import ciro.atc.model.entity.TablaDescripcionMatrizRiesgo;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter

public class MatrizOportunidadGetDTO {

    private Long id;
    private String codigo;
    private Integer idMacroCorrelativo;
    private String estadoRegistro;
    private Date fechaEvaluacion;
    private String definicion;
    private String causa;
    private String consecuencia;
    private String factor;

    // Controles
    private Boolean controlesTiene;
    private String controles;
    private String controlComentario;

    // Planes
    private String planesAccion;

    private TablaDescripcion areaId;
    private TablaDescripcion unidadId;
    private TablaDescripcion procesoId;
    private TablaDescripcion procedimientoId;
    private TablaDescripcion duenoCargoId;
    private TablaDescripcion responsableCargoId;
    private TablaDescripcionMatrizOportunidad fodaId;
    private TablaDescripcionMatrizOportunidad fodaDescripcionId;
    private TablaDescripcionMatrizOportunidad grupoInteresId;
    private TablaDescripcionMatrizRiesgo probabilidadId;
    private TablaDescripcionMatrizOportunidad impactoOporId;
    private TablaDescripcionMatrizOportunidad fortalezaId;

    private int usuarioId;
}
