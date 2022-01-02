package atc.riesgos.model.dto.EventoRiesgo;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import atc.riesgos.model.entity.Archivo;
import atc.riesgos.model.entity.MatrizRiesgo;
import atc.riesgos.model.entity.TablaDescripcion;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class EventoRiesgoGetDTO {

    private Long id;
    private String codigo;
    private int idAreaCorrelativo;
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
  //  private String riesgoRelacionado; // Relacion con Matriz de Riesgos por definir
    private String detalleEstado;
    private Float montoPerdida;
    private Float gastoAsociado;
    private Float montoRecuperado;
    private Boolean coberturaSeguro;
    private Float montoRecuperadoSeguro;
    private Float perdidaMercado;
    private String otros;

    // Planes de accion
    private TablaDescripcion areaResponsableId;
    private TablaDescripcion cargoResponsableId;
    private String detallePlan;
    private Date fechaFinPlan;
    private String descripcionEstado;
    private String estadoPlan;


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
    private String tasaCambioId;
    private TablaDescripcion monedaId;
    private TablaDescripcion impactoId;
    private TablaDescripcion polizaSeguroId;
    private TablaDescripcion recuperacionActivoId;

    private TablaDescripcion operativoId;
    private TablaDescripcion seguridadId;
    private TablaDescripcion liquidezId;
    private TablaDescripcion fraudeId;
    private TablaDescripcion legalId;
    private TablaDescripcion reputacionalId;
    private TablaDescripcion cumplimientoId;
    private TablaDescripcion estrategicoId;
    private TablaDescripcion gobiernoId;

    private Archivo archivoId;
    private List<MatrizRiesgo> riesgoRelacionado; // Relacion con Matriz de Riesgos por definir
}