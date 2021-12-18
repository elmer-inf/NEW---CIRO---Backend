package ciro.atc.model.dto.MatrizOportunidad;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter

public class MatrizOportunidadPostDTO {
    
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

    private Long areaId;
    private Long unidadId;
    private Long procesoId;
    private Long procedimientoId;
    private Long duenoCargoId;
    private Long responsableCargoId;
    private Long fodaId;
    private Long fodaDescripcionId;
    private Long grupoInteresId;
    private Long probabilidadId;
    private Long impactoOporId;
    private Long fortalezaId;

    private int usuarioId;
}
