package atc.riesgos.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
@SuppressWarnings("serial")
@Setter
@Getter
@Entity

@Table(name = "tbl_evento_riesgo")

public class EventoRiesgo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eve_id")
    private Long id;

    @Column(name = "eve_codigo", length = 20)
    private String codigo;

    @Column(name = "eve_id_area_correlativo")
    private int idAreaCorrelativo;

    @Column(name = "eve_tipo_evento", length = 10)
    private String tipoEvento;

    @Column(name = "eve_estado_registro", length = 30)
    private String estadoRegistro; // Pendiente, observado, autorizado, descartado

    @Column(name = "eve_estado_evento", length = 30)
    private String estadoEvento; // Investigacion, seguimiento y Resuelto

    @Column(name = "eve_fecha_ini")
    private Date fechaIni;

    @Column(name = "eve_hora_ini")
    private Time horaIni;

    @Column(name = "eve_fecha_desc")
    private Date fechaDesc;

    @Column(name = "eve_hora_desc")
    private Time horaDesc;

    @Column(name = "eve_fecha_fin")
    private Date fechaFin;

    @Column(name = "eve_hora_fin")
    private Time horaFin;

    @Column(name = "eve_entidad_afectada")
    private Boolean entidadAfectada;

    @Column(name = "eve_comercio_afectado")
    private Boolean comercioAfectado;

    @Column(name = "eve_estado_reportado", length = 50)
    private String estadoReportado; // reportado, no reportado

    @Column(name = "eve_descripcion", columnDefinition = "text")
    private String descripcion;

    @Column(name = "eve_descripcion_completa", columnDefinition = "text")
    private String descripcionCompleta;

    @Column(name = "eve_codigo_inicial", columnDefinition = "text")
    private String codigoInicial;

    @Column(name = "eve_trimestre", length = 50)
    private String trimestre;

    @Column(name = "eve_detalle_evento_critico", columnDefinition = "text")
    private String detalleEventoCritico;

    @Column(name = "eve_evento_critico", length = 500)
    private String eventoCritico;

    @Column(name = "eve_linea_negocio", length = 500)
    private String lineaNegocio;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "eventoriesgo_matriz",
            joinColumns = @JoinColumn(name = "id_evento_riesgo", nullable = true),
            inverseJoinColumns = @JoinColumn(name = "id_matriz_riesgo" , nullable = true)
    )
    //  @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private List<MatrizRiesgo> riesgoRelacionado;


    @Column(name = "eve_detalle_estado", columnDefinition = "text")
    private String detalleEstado;

    @Column(name = "eve_monto_perdida") //, length = 20
    private Float montoPerdida;

    @Column(name = "eve_gasto_asociado")//, length = 20
    private Float gastoAsociado;

    @Column(name = "eve_monto_recuperado")//, length = 20
    private Float montoRecuperado;

    @Column(name = "eve_cobertura_seguro")
    private Boolean coberturaSeguro;

    @Column(name = "eve_monto_recuperado_seguro") //, length = 20
    private Float montoRecuperadoSeguro;

    @Column(name = "eve_perdida_mercado") //, length = 20
    private Float perdidaMercado;

    @Column(name = "eve_otros", columnDefinition = "text")
    private String otros;

    // Planes de accion
    @ManyToOne
    @JoinColumn(name = "eve_area_responsable_id")
    private TablaDescripcion areaResponsableId;

    @ManyToOne
    @JoinColumn(name = "eve_cargo_reponsable_id")
    private TablaDescripcion cargoResponsableId;

    @Column(name = "eve_detalle_plan", columnDefinition = "text")
    private String detallePlan;

    @Column(name = "eve_fecha_fin_plan")
    private Date fechaFinPlan;

    @Column(name = "eve_descripcion_estado", columnDefinition = "text")
    private String descripcionEstado;

    @Column(name = "eve_estado_plan", length = 200)
    private String estadoPlan;

    /* -------- RELACION DE PARAMETROS --------- */
    @ManyToOne
    @JoinColumn(name = "eve_agencia_id")
    private TablaDescripcion agenciaId;

    @ManyToOne
    @JoinColumn(name = "eve_ciudad_id")
    private TablaDescripcion ciudadId;

    @ManyToOne
    @JoinColumn(name = "eve_area_id")
    private TablaDescripcion areaID;

    @ManyToOne
    @JoinColumn(name = "eve_unidad_id")
    private TablaDescripcion unidadId;

    @ManyToOne
    @JoinColumn(name = "eve_entidad_id")
    private TablaDescripcion entidadId;

    @ManyToOne
    @JoinColumn(name = "eve_cargo_id")
    private TablaDescripcion cargoId;

    @ManyToOne
    @JoinColumn(name = "eve_fuente_inf_id")
    private TablaDescripcion fuenteInfId;

    @ManyToOne
    @JoinColumn(name = "eve_canal_asfi_id")
    private TablaDescripcion canalAsfiId;

    @ManyToOne
    @JoinColumn(name = "eve_subcategorizacion_id")
    private TablaDescripcion subcategorizacionId;

    @ManyToOne
    @JoinColumn(name = "eve_tipo_evento_perdida_id")
    private TablaDescripcion tipoEventoPerdidaId;

    @ManyToOne
    @JoinColumn(name = "eve_sub_evento_id")
    private TablaDescripcion subEventoId;

    @ManyToOne
    @JoinColumn(name = "eve_clase_evento_id")
    private TablaDescripcion claseEventoId;

    @ManyToOne
    @JoinColumn(name = "eve_factor_riesgo_id")
    private TablaDescripcion factorRiesgoId;

    @ManyToOne
    @JoinColumn(name = "eve_proceso_id")
    private TablaDescripcion procesoId;

    @ManyToOne
    @JoinColumn(name = "eve_procedimiento_id")
    private TablaDescripcion procedimientoId;

    @ManyToOne
    @JoinColumn(name = "eve_linea_asfi_id")
    private TablaDescripcion lineaAsfiId;

    @ManyToOne
    @JoinColumn(name = "eve_operacion_id")
    private TablaDescripcion operacionId;

    @ManyToOne
    @JoinColumn(name = "eve_efecto_perdida_id")
    private TablaDescripcion efectoPerdidaId;

    @ManyToOne
    @JoinColumn(name = "eve_ope_pro_ser_id")
    private TablaDescripcion opeProSerId;

    @ManyToOne
    @JoinColumn(name = "eve_tipo_servicio_id")
    private TablaDescripcion tipoServicioId;

    @ManyToOne
    @JoinColumn(name = "eve_desc_servicio_id")
    private TablaDescripcion descServicioId;

    // @ManyToOne
    //@JoinColumn(name = "eve_tasa_cambio_id")
    @Column(name = "eve_tasa_cambio_id", length = 5)
    private String tasaCambioId;

    @ManyToOne
    @JoinColumn(name = "eve_moneda_id")
    private TablaDescripcion monedaId;

    @ManyToOne
    @JoinColumn(name = "eve_impacto_id")
    private TablaDescripcion impactoId;

    @ManyToOne
    @JoinColumn(name = "eve_poliza_seguro_id")
    private TablaDescripcion polizaSeguroId;


    @ManyToOne
    @JoinColumn(name = "eve_operativo_id")
    private TablaDescripcion operativoId;

    @ManyToOne
    @JoinColumn(name = "eve_liquidez_id")
    private TablaDescripcion liquidezId;

    @ManyToOne
    @JoinColumn(name = "eve_fraude_id")
    private TablaDescripcion fraudeId;

    @ManyToOne
    @JoinColumn(name = "eve_legal_id")
    private TablaDescripcion legalId;

    @ManyToOne
    @JoinColumn(name = "eve_reputacional_id")
    private TablaDescripcion reputacionalId;

    @ManyToOne
    @JoinColumn(name = "eve_cumplimiento_id")
    private TablaDescripcion cumplimientoId;

    @ManyToOne
    @JoinColumn(name = "eve_estrategico_id")
    private TablaDescripcion estrategicoId;

    @ManyToOne
    @JoinColumn(name = "eve_gobierno_id")
    private TablaDescripcion gobiernoId;

    @ManyToOne
    @JoinColumn(name = "eve_seguridad_id")
    private TablaDescripcion seguridadId;

    @ManyToOne
    @JoinColumn(name = "eve_recuperacion_activo_id")
    private TablaDescripcion recuperacionActivoId;
    /* -------- FIN RELACION DE PARAMETROS  --------- */

    @JsonIgnore
    @OneToMany
    @JoinColumn(name="evento_id", referencedColumnName="eve_id", nullable=true)
    private List<Archivo> archivoId;

    @JsonIgnore
    @OneToMany(mappedBy = "eventoId")
    private List<Observacion> observacionId;

    @Column(name = "des_usuario_id")
    private int usuario_id;


    /* -------- USUARIOS RESPONSABLES  --------- */
    @Column(name = "eve_responsable_elaborador", columnDefinition = "text")
    private String responsableElaborador;
    /* -------- FIN USUARIOS RESPONSABLES  --------- */

    @CreationTimestamp
    @Column(name = "eve_dateTimeCreate")
    private Timestamp created;

    @UpdateTimestamp
    @Column(name = "eve_dateTimeUpdate")
    private Timestamp updated;

    @Column(name = "eve_delete")
    private boolean deleted;

    final public EventoRiesgo deleteOnly() {
        this.deleted = true;
        return this;
    }
}