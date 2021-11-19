package ciro.atc.model.dto.EventoRiesgo;

import ciro.atc.model.dto.Observacion.ObservacionPostDTO;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter

public class EventoRiesgoPutDTOevaluacion {

    private String estadoRegistro;

    private String listaObservacion;
    private String nota;
    private String estado;
    private Long eventoId;

    //private ObservacionPostDTO observacion; // otra alternativa mandando como objeto



}
