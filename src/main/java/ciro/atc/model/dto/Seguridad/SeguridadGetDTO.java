package ciro.atc.model.dto.Seguridad;

import ciro.atc.model.entity.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter

public class SeguridadGetDTO {

    private Long id;
    private Date fechaRegistro;
    private String ipUrl;
    private String activoInformacion;
    private String red;
    private String descripcionRiesgo;
    private String recomendacion;
    private Date fechaSolucion;
    private Date fechaLimite;
    private String planTrabajo;
    private String informeEmitido;
    private String ciSeguimiento;
    private String comentario;

    private TablaDescripcionSeguridad tipoActivoId;
    private TablaDescripcionSeguridad estadoId;
    private TablaDescripcionMatrizRiesgo nivelRiesgoId;
    private TablaDescripcion areaId;
}
