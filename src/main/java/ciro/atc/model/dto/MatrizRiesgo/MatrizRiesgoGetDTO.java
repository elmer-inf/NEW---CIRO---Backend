package ciro.atc.model.dto.MatrizRiesgo;

import ciro.atc.model.entity.Archivo;
import ciro.atc.model.entity.TablaDescripcion;
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
    private String definicion;
    private String causa;
    private String consecuencia;
    private Boolean monetario;

    private TablaDescripcion areaID;
    private TablaDescripcion unidadId;
    private TablaDescripcion procesoId;
    private TablaDescripcion procedimientoId;
    private TablaDescripcion duenoCargoId;
    private TablaDescripcion responsableCargoId;
    private TablaDescripcion efectoPerdidaId;
    private TablaDescripcion factorRiesgoId;

    private int usuario_id;

}
