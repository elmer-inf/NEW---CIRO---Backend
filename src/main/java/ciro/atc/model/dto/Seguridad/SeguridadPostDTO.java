package ciro.atc.model.dto.Seguridad;

import ciro.atc.model.entity.TablaDescripcion;
import ciro.atc.model.entity.TablaDescripcionMatrizRiesgo;
import ciro.atc.model.entity.TablaDescripcionSeguridad;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter

public class SeguridadPostDTO {

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

    private Long tipoActivoId;
    private Long estadoId;
    private Long nivelRiesgoId;
    private Long areaId;

    private int usuarioId;
}
