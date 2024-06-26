package atc.riesgos.model.dto.EventoRiesgo;

import lombok.Getter;
import lombok.Setter;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Setter
@Getter

public class EventoRiesgoPostDTO {

    private String tipoEvento;
    private String estadoRegistro;
    private String estadoEvento;
    private Date fechaIni;
    private Time horaIni;
    private Date fechaDesc;
    private int fechaDescAux;
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
    private String riesgoRelacionado;
    private String detalleEstado;
    private Float montoPerdida;
    private Float gastoAsociado;
    private Float montoRecuperado;
    private Boolean coberturaSeguro;
    private Float montoRecuperadoSeguro;
    private Float perdidaMercado;
    private String otros;
    private Date fechaContable;
    private Integer procesoCriticoAsfi; // CAMPO NUEVO: valores 1 o 2

    private Long agenciaId;
    private Long ciudadId;
    private Long areaID;
    private Long unidadId;
    private Long entidadId;
    private List<Long> cargoId;
    private Long fuenteInfId;
    private Long canalAsfiId;
    private Long subcategorizacionId;
    private Long tipoEventoPerdidaId;
    private Long subEventoId;
    private Long claseEventoId;
    private Long factorRiesgoId;
    private Long procesoId;
    private Long procedimientoId;
    private Long lineaAsfiId;
    private Long operacionId;
    private Long efectoPerdidaId;
    private Long opeProSerId;
    private Long tipoServicioId;
    private Long descServicioId;
    private String tasaCambioId;
    private Long monedaId;
    private Long impactoId;
    private Long polizaSeguroId;
    private Long recuperacionActivoId;

    private Long operativoId;
    private Long seguridadId;
    private Long liquidezId;
    private Long fraudeId;
    private Long legalId;
    private Long reputacionalId;
    private Long cumplimientoId;
    private Long estrategicoId;
    private Long gobiernoId;
    private Long lgiId;

    private Long cuentaContableId;
    private String responsableElaborador;
    private int usuario_id;

    // Relacion con matriz de riesgos.
    private List<Long> listMatrizRiesgo;

}
