package atc.riesgos.model.dto.EventoRiesgo;

import atc.riesgos.model.dto.TablaDescripcion.TablaDescripcionGetDTO3;
import atc.riesgos.model.entity.Archivo;
import atc.riesgos.model.entity.TablaDescripcion;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;


@Setter
@Getter
public class EventoRiesgoGetDTODesc {

    private Long id;
    private String codigo;
    private int idAreaCorrelativo;
    private int fechaDescAux;
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
    private Float gastoAsociado;
    private Float montoRecuperado;
    private Boolean coberturaSeguro;
    private Float montoRecuperadoSeguro;
    private Float perdidaMercado;
    private String otros;

    // Planes de accion
    private TablaDescripcionGetDTO3 areaResponsableId;
    private TablaDescripcionGetDTO3 cargoResponsableId;
    private String detallePlan;
    private Date fechaFinPlan;
    private String descripcionEstado;
    private String estadoPlan;

    private Date fechaContable;
    private Integer procesoCriticoAsfi; // CAMPO NUEVO: valores 1 o 2

    private TablaDescripcionGetDTO3 agenciaId;
    private TablaDescripcionGetDTO3 ciudadId;
    private TablaDescripcionGetDTO3 areaID;
    private TablaDescripcionGetDTO3 unidadId;
    private TablaDescripcionGetDTO3 entidadId;
    private TablaDescripcionGetDTO3 cargoId;
    private TablaDescripcionGetDTO3 fuenteInfId;
    private TablaDescripcionGetDTO3 canalAsfiId;
    private TablaDescripcionGetDTO3 subcategorizacionId;
    private TablaDescripcionGetDTO3 tipoEventoPerdidaId;
    private TablaDescripcionGetDTO3 subEventoId;
    private TablaDescripcionGetDTO3 claseEventoId;
    private TablaDescripcionGetDTO3 factorRiesgoId;
    private TablaDescripcionGetDTO3 procesoId;
    private TablaDescripcionGetDTO3 procedimientoId;
    private TablaDescripcionGetDTO3 lineaAsfiId;
    private TablaDescripcionGetDTO3 operacionId;
    private TablaDescripcionGetDTO3 efectoPerdidaId;
    private TablaDescripcionGetDTO3 opeProSerId;
    private TablaDescripcionGetDTO3 tipoServicioId;
    private TablaDescripcionGetDTO3 descServicioId;
    private String tasaCambioId;
    private TablaDescripcionGetDTO3 monedaId;
    private TablaDescripcionGetDTO3 impactoId;
    private TablaDescripcionGetDTO3 polizaSeguroId;
    private TablaDescripcionGetDTO3 recuperacionActivoId;

    private TablaDescripcionGetDTO3 operativoId;
    private TablaDescripcionGetDTO3 seguridadId;
    private TablaDescripcionGetDTO3 liquidezId;
    private TablaDescripcionGetDTO3 fraudeId;
    private TablaDescripcionGetDTO3 legalId;
    private TablaDescripcionGetDTO3 reputacionalId;
    private TablaDescripcionGetDTO3 cumplimientoId;
    private TablaDescripcionGetDTO3 estrategicoId;
    private TablaDescripcionGetDTO3 gobiernoId;
    private TablaDescripcionGetDTO3 lgiId;

    private TablaDescripcionGetDTO3 cuentaContableId;

    private Archivo archivoId;
    
}
