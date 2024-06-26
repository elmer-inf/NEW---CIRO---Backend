package atc.riesgos.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@Entity

@Table(name = "tbl_matriz_riesgo")

public class MatrizRiesgo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rie_id")
    private Long id;

    @Column(name = "rie_codigo", length = 20)
    private String codigo;

    @Column(name = "rie_id_unidad_correlativo")
    private Integer idUnidadCorrelativo;

    @Column(name = "rie_estado_registro", length = 30)
    private String estadoRegistro;

    @Column(name = "rie_fecha_evaluacion")
    private Date fechaEvaluacion;

    @Column(name = "rie_identificado_otro",  columnDefinition = "text")
    private String identificadoOtro;

    @Column(name = "rie_definicion", columnDefinition = "text")
    private String definicion;

    @Column(name = "rie_causa", columnDefinition = "text")
    private String causa;

    @Column(name = "rie_consecuencia", columnDefinition = "text")
    private String consecuencia;

    @Column(name = "rie_efecto_perdida_otro", length = 200)
    private String efectoPerdidaOtro;

    @Column(name = "rie_monetario")
    private Boolean monetario;

    // Controles
    @Column(name = "rie_controles_tiene")
    private Boolean controlesTiene;

    @Column(name = "rie_controles", columnDefinition = "text")
    private String controles;

    @Column(name = "rie_control_objetivo", length = 100)
    private String controlObjetivo;

    @Column(name = "rie_control_comentario", columnDefinition = "text")
    private String controlComentario;

    // Planes
    @Column(name = "rie_planes_accion", columnDefinition = "text")
    private String planesAccion;

    // Valoracion
    @Column(name = "rie_criterio_impacto", columnDefinition = "text")
    private String criterioImpacto;

    @Column(name = "rie_criterio_probabilidad", columnDefinition = "text")
    private String criterioprobabilidad;

    @Column(name = "rie_impacto_usd")
    private Float impactoUSD;

    /* -------- RELACION DE PARAMETROS --------- */
    @ManyToOne
    @JoinColumn(name = "rie_area_id")
    private TablaDescripcion areaId;

    @ManyToOne
    @JoinColumn(name = "rie_unidad_id")
    private TablaDescripcion unidadId;

    @ManyToOne
    @JoinColumn(name = "rie_proceso_id")
    private TablaDescripcion procesoId;

    @ManyToOne
    @JoinColumn(name = "rie_procedimiento_id")
    private TablaDescripcion procedimientoId;

    @ManyToOne
    @JoinColumn(name = "rie_dueno_cargo_id")
    private TablaDescripcion duenoCargoId;

    @ManyToOne
    @JoinColumn(name = "rie_responsable_cargo_id")
    private TablaDescripcion responsableCargoId;

    @ManyToOne
    @JoinColumn(name = "rie_identificado_id")
    private TablaDescripcionMatrizRiesgo identificadoId;

    @ManyToOne
    @JoinColumn(name = "rie_efecto_perdida_id")
    private TablaDescripcion efectoPerdidaId;

    @ManyToOne
    @JoinColumn(name = "rie_perdida_asfi_id")
    private TablaDescripcionMatrizRiesgo perdidaAsfiId;

    @ManyToOne
    @JoinColumn(name = "rie_factor_riesgo_id")
    private TablaDescripcion factorRiesgoId;

    @ManyToOne
    @JoinColumn(name = "rie_probabilidad_id")
    private TablaDescripcionMatrizRiesgo probabilidadId;

    @ManyToOne
    @JoinColumn(name = "rie_impacto_id")
    private TablaDescripcionMatrizRiesgo impactoId;

    @ManyToOne
    @JoinColumn(name = "rie_control_id")
    private TablaDescripcionMatrizRiesgo controlId;

    @ManyToOne
    @JoinColumn(name = "rie_tipo_fraude_interno")
    private TablaDescripcionMatrizRiesgo tipoFraudeId;

    @ManyToOne
    @JoinColumn(name = "rie_subtipo_fraude_interno")
    private TablaDescripcionMatrizRiesgo subtipoFraudeId;

    /* -------- FIN RELACION DE PARAMETROS  --------- */

    /*@JsonIgnore
    @OneToMany(mappedBy = "eventoId")
    private List<Archivo> archivoId;
*/
    @JsonIgnore
    @OneToMany(mappedBy = "matrizRiesgoId")
    private List<Observacion> observacionId;

    @Column(name = "rie_usuario_id")
    private int usuario_id;

    /* RELACION CON EVENTO DE RIESGO */
    @Column(name = "rie_evento_materializado")
    private Boolean eventoMaterializado;

    @Column(name = "rie_evento_riesgo_id")
    private Long eventoRiesgoId;

    /* FIN RELACION CON EVENTO DE RIESGO */

    @CreationTimestamp
    @Column(name = "rie_dateTimeCreate")
    private Timestamp created;

    @UpdateTimestamp
    @Column(name = "rie_dateTimeUpdate")
    private Timestamp updated;

    @Column(name = "rie_delete")
    private boolean deleted;

    @Column(name = "rie_probabilidad_residual")
    private Integer probabilidadResidual;

    @Column(name = "rie_impacto_residual")
    private Integer impactoResidual;

    final public MatrizRiesgo deleteOnly() {
        this.deleted = true;
        return this;
    }


}