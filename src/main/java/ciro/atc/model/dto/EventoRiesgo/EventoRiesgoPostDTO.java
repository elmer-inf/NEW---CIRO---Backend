package ciro.atc.model.dto.EventoRiesgo;

import ciro.atc.model.entity.TablaDescripcion;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Setter
@Getter

public class EventoRiesgoPostDTO {

    private String codigo;
    private Date fechaIni;
    private Time horaIni;
    private Date fechaDesc;
    private Time horaDesc;
    private Long agenciaId;
    private Long ciudadId;
    private Long areaID;
    private Long archivoId;

}
