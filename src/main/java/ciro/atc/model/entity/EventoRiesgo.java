package ciro.atc.model.entity;


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

@Setter
@Getter
@Entity

@Table(name = "tbl_evento_riesgo")

public class EventoRiesgo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eve_id")
    private Long id;

    @Column(name = "eve_codigo")
    private String codigo;

    @Column(name = "eve_tipo_evento")
    private String tipoEvento;

    @Column(name = "eve_estado_registro")
    private String estadoRegistro; // Pendiente, observado, autorizado, descartado

    @Column(name = "eve_estado_evento")
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

    @Column(name = "eve_estado_reportado")
    private String estadoReportado; // reportado, no reportado

    @Column(name = "eve_descripcion")
    private String descripcion;

    @Column(name = "eve_descripcion_completa")
    private String descripcionCompleta;

    @Column(name = "eve_codigo_inicial")
    private String codigoInicial;

    @Column(name = "eve_trimestre")
    private String trimestre;

    @Column(name = "eve_detalle_evento_critico")
    private String detalleEventoCritico;

    @Column(name = "eve_evento_critico")
    private String eventoCritico;

    @Column(name = "eve_linea_negocio")
    private String lineaNegocio;

            // Relacion con Matriz de Riesgos por definir
    @Column(name = "eve_riesgo_relaciionado")
    private String riesgoRelacionado;
            // FIN Relacion con Matriz de Riesgos por definir

    @Column(name = "eve_detalle_estado")
    private String detalleEstado;

    @Column(name = "eve_monto_perdida")
    private Float montoPerdida;

    @Column(name = "eve_monto_perdida_riesgo")
    private Float montoPerdidaRiesgo;

    @Column(name = "eve_gasto_asociado")
    private Float gastoAsociado;

    @Column(name = "eve_monto_recuperado")
    private Float montoRecuperado;

    @Column(name = "eve_cobertura_seguro")
    private Boolean coberturaSeguro;

    @Column(name = "eve_monto_recuperado_seguro")
    private Float montoRecuperadoSeguro;

    @Column(name = "eve_recuperacion_activo")
    private String recuperacionActivo; // REVISAR CAMPO (QUE CONTIENE - boolean, select, input)

    @Column(name = "eve_perdida_mercado")
    private Float perdidaMercado;

    @Column(name = "eve_total_perdida")
    private Float totalPerdida;

    @Column(name = "eve_otros")
    private String otros; // POR CONFIRMAR A QUE SELECT PERTENECE


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

    @Column(name = "eve_unidad_id")
    private TablaDescripcion unidadId;

    @Column(name = "eve_entidad_id")
    private TablaDescripcion entidadId;

    @Column(name = "eve_cargo_id")
    private TablaDescripcion cargoId;

    @Column(name = "eve_fuente_inf_id")
    private TablaDescripcion fuenteInfId;

    @Column(name = "eve_canal_asfi_id")
    private TablaDescripcion canalAsfiId;

    @Column(name = "eve_subcategorizacion_id")
    private TablaDescripcion subcategorizacionId;

    @Column(name = "eve_tipo_evento_perdida_id")
    private TablaDescripcion tipoEventoPerdidaId;

    @Column(name = "eve_sub_evento_id")
    private TablaDescripcion subEventoId;

    @Column(name = "eve_clase_evento_id")
    private TablaDescripcion claseEventoId;

    @Column(name = "eve_factor_riesgo_id")
    private TablaDescripcion factorRiesgoId;

    @Column(name = "eve_proceso_id")
    private TablaDescripcion procesoId;

    @Column(name = "eve_procedimiento_id")
    private TablaDescripcion procedimientoId;

    @Column(name = "eve_linea_asfi_id")
    private TablaDescripcion lineaAsfiId;

    @Column(name = "eve_operacion_id")
    private TablaDescripcion operacionId;

    @Column(name = "eve_efecto_perdida_id")
    private TablaDescripcion efectoPerdidaId;

    @Column(name = "eve_ope_pro_ser_id")
    private TablaDescripcion opeProSerId;

    @Column(name = "eve_tipo_servicio_id")
    private TablaDescripcion tipoServicioId;

    @Column(name = "eve_desc_servicio_id")
    private TablaDescripcion descServicioId;

    @Column(name = "eve_tasa_cambio_id")
    private TablaDescripcion tasaCambioId;

    @Column(name = "eve_moneda_id")
    private TablaDescripcion monedaId;

    @Column(name = "eve_impacto_id")
    private TablaDescripcion impactoId;

    @Column(name = "eve_poliza_seguro_id")
    private TablaDescripcion polizaSeguroId;


    @Column(name = "eve_operativo_id")
    private TablaDescripcion operativoId;

    @Column(name = "eve_seguridad_id")
    private TablaDescripcion seguridadId;

    @Column(name = "eve_liquidez_id")
    private TablaDescripcion liquidezId;

    @Column(name = "eve_lgi_id")
    private TablaDescripcion lgiId;

    @Column(name = "eve_fraude_id")
    private TablaDescripcion fraudeId;

    @Column(name = "eve_legal_id")
    private TablaDescripcion legalId;

    @Column(name = "eve_reputacional_id")
    private TablaDescripcion reputacionalId;

    @Column(name = "eve_cumplimiento_id")
    private TablaDescripcion cumplimientoId;

    @Column(name = "eve_estrategico_id")
    private TablaDescripcion estrategicoId;

    @Column(name = "eve_gobierno_id")
    private TablaDescripcion gobiernoId;
    /* -------- FIN RELACION DE PARAMETROS  --------- */


    /* -------- ARCHIVO --------- */
    @JsonIgnore
    @OneToMany(mappedBy = "eventoId")
    private List<Archivo> archivoId;
    /* -------- FIN ARCHIVO --------- */


    @Column(name = "des_usuario_id", length = 10)
    private int usuario_id;

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