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

@Table(name = "tbl_matriz_oportunidad")

public class MatrizOportunidad implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "opo_id")
    private Long id;

    @Column(name = "opo_codigo", length = 20)
    private String codigo;

    @Column(name = "opo_id_macro_correlativo")
    private Integer idMacroCorrelativo;

    @Column(name = "eve_estado_registro", length = 30)
    private String estadoRegistro;

    @Column(name = "opo_fecha_evaluacion")
    private Date fechaEvaluacion;

    @Column(name = "opo_definicion", columnDefinition = "text")
    private String definicion;

    @Column(name = "opo_causa", columnDefinition = "text")
    private String causa;

    @Column(name = "opo_consecuencia", columnDefinition = "text")
    private String consecuencia;

    @Column(name = "opo_factor")
    private String factor;

    // Controles
    @Column(name = "opo_controles_tiene")
    private Boolean controlesTiene;

    @Column(name = "opo_controles", columnDefinition = "text")
    private String controles;
    
    @Column(name = "opo_control_comentario", columnDefinition = "text")
    private String controlComentario;

    // Planes
    @Column(name = "opo_planes_accion", columnDefinition = "text")
    private String planesAccion;

    /* -------- RELACION DE PARAMETROS --------- */
    @ManyToOne
    @JoinColumn(name = "opo_area_id")
    private TablaDescripcion areaId;

    @ManyToOne
    @JoinColumn(name = "opo_unidad_id")
    private TablaDescripcion unidadId;

    @ManyToOne
    @JoinColumn(name = "opo_proceso_id")
    private TablaDescripcion procesoId;

    @ManyToOne
    @JoinColumn(name = "opo_procedimiento_id")
    private TablaDescripcion procedimientoId;

    @ManyToOne
    @JoinColumn(name = "opo_dueno_cargo_id")
    private TablaDescripcion duenoCargoId;

    @ManyToOne
    @JoinColumn(name = "opo_responsable_cargo_id")
    private TablaDescripcion responsableCargoId;

    @ManyToOne
    @JoinColumn(name = "opo_foda_id")
    private TablaDescripcionMatrizOportunidad fodaId;

    @ManyToOne
    @JoinColumn(name = "opo_foda_desc_id")
    private TablaDescripcionMatrizOportunidad fodaDescripcionId;

    @ManyToOne
    @JoinColumn(name = "opo_grupo_interes_id")
    private TablaDescripcionMatrizOportunidad grupoInteresId;

    @ManyToOne
    @JoinColumn(name = "opo_probabilidad_id")
    private TablaDescripcionMatrizRiesgo probabilidadId;

    @ManyToOne
    @JoinColumn(name = "opo_impacto_opor_id")
    private TablaDescripcionMatrizOportunidad impactoOporId;

    @ManyToOne
    @JoinColumn(name = "opo_fortaleza_id")
    private TablaDescripcionMatrizOportunidad fortalezaId;

    /* -------- FIN RELACION DE PARAMETROS  --------- */

    @JsonIgnore
    @OneToMany(mappedBy = "matrizOportunidadId")
    private List<Observacion> observacionId;

    @Column(name = "opo_usuario_id")
    private int usuarioId;

    @CreationTimestamp
    @Column(name = "opo_dateTimeCreate")
    private Timestamp created;

    @UpdateTimestamp
    @Column(name = "opo_dateTimeUpdate")
    private Timestamp updated;

    @Column(name = "opo_delete")
    private boolean deleted;

    final public MatrizOportunidad deleteOnly() {
        this.deleted = true;
        return this;
    }
}