package atc.riesgos.model.dto.MatrizRiesgo;

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
    private String efectoPerdidaOtro;
    private Boolean monetario;

    // Controles
    private Boolean controlesTiene;
    private String controles;
    private String controlObjetivo;
    private String controlComentario;

    // Planes
    private String planesAccion;

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
    private Long tipoFraudeId;
    private Long subtipoFraudeId;

    private int usuario_id;

    private Boolean eventoMaterializado;
    private Long eventoRiesgoId;

}
