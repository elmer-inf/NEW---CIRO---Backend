package ciro.atc.model.dto.EventoRiesgo;

import java.sql.Date;
import java.sql.Time;

import ciro.atc.model.entity.Archivo;
import ciro.atc.model.entity.TablaDescripcion;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class EventoRiesgoGetDTO {

    private Long id;
    private String codigo;
    private String tipoEvento;
    private String estadoRegistro;
    private String estadoEvento;
    private Date fechaIni;
    private Time horaIni;
    private Date fechaDesc;
    private Time horaDesc;
    private Date fechaFin;
    private Time horaFin;
    private Boolean entidadAfectada;
    private Boolean comercioAfectado;
    private String estadoReportado;
    private String descripcion;
    private String descripcionCompleta;
    private String codigoInicial;
    private String trimestre;
    private String detalleEventoCritico;
    private String eventoCritico;
    private String lineaNegocio;
    private String riesgoRelacionado; // Relacion con Matriz de Riesgos por definir
    private String detalleEstado;
    private Float montoPerdida;
    private Float montoPerdidaRiesgo;
    private Float gastoAsociado;
    private Float montoRecuperado;
    private Boolean coberturaSeguro;
    private Float montoRecuperadoSeguro;
    private String recuperacionActivo; // REVISAR CAMPO (QUE CONTIENE - boolean, select, input)
    private Float perdidaMercado;
    private Float totalPerdida;
    private String otros; // POR CONFIRMAR A QUE SELECT PERTENECE


    private TablaDescripcion agenciaId;
    private TablaDescripcion ciudadId;
    private TablaDescripcion areaID;
    private TablaDescripcion unidadId;
    private TablaDescripcion entidadId;
    private TablaDescripcion cargoId;
    private TablaDescripcion fuenteInfId;
    private TablaDescripcion canalAsfiId;
    private TablaDescripcion subcategorizacionId;
    private TablaDescripcion tipoEventoPerdidaId;
    private TablaDescripcion subEventoId;
    private TablaDescripcion claseEventoId;
    private TablaDescripcion factorRiesgoId;
    private TablaDescripcion procesoId;
    private TablaDescripcion procedimientoId;
    private TablaDescripcion lineaAsfiId;
    private TablaDescripcion operacionId;
    private TablaDescripcion efectoPerdidaId;
    private TablaDescripcion opeProSerId;
    private TablaDescripcion tipoServicioId;
    private TablaDescripcion descServicioId;
    private TablaDescripcion tasaCambioId;
    private TablaDescripcion monedaId;
    private TablaDescripcion impactoId;
    private TablaDescripcion polizaSeguroId;

    private TablaDescripcion operativoId;
    private TablaDescripcion seguridadId;
    private TablaDescripcion liquidezId;
    private TablaDescripcion lgiId;
    private TablaDescripcion fraudeId;
    private TablaDescripcion legalId;
    private TablaDescripcion reputacionalId;
    private TablaDescripcion cumplimientoId;
    private TablaDescripcion estrategicoId;
    private TablaDescripcion gobiernoId;

    private Archivo archivoId;

}
