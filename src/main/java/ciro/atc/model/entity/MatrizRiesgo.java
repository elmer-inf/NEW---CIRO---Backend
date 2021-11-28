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

    @Column(name = "eve_estado_registro", length = 30)
    private String estadoRegistro;

    @Column(name = "rie_fecha_evaluacion")
    private Date fechaEvaluacion;

    @Column(name = "rie_definicion", length = 500)
    private String definicion;

    @Column(name = "rie_causa", length = 500)
    private String causa;

    @Column(name = "rie_consecuencia", length = 500)
    private String consecuencia;

    @Column(name = "rie_def_concatenado", length = 500)
    private String defConcatenado;

    @Column(name = "rie_monetario")
    private Boolean monetario;

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
    @JoinColumn(name = "rie_efecto_perdida_id")
    private TablaDescripcion efectoPerdidaId;

    @ManyToOne
    @JoinColumn(name = "rie_perdida_asfi_id")
    private TablaDescripcionMatrizRiesgo perdidaAsfiId;

    @ManyToOne
    @JoinColumn(name = "rie_factor_riesgo_id")
    private TablaDescripcion factorRiesgoId;

    @ManyToOne
    @JoinColumn(name = "rie_prob_tiempo_id")
    private TablaDescripcionMatrizRiesgo probTiempoId;

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

    @CreationTimestamp
    @Column(name = "rie_dateTimeCreate")
    private Timestamp created;

    @UpdateTimestamp
    @Column(name = "rie_dateTimeUpdate")
    private Timestamp updated;

    @Column(name = "rie_delete")
    private boolean deleted;

    final public MatrizRiesgo deleteOnly() {
        this.deleted = true;
        return this;
    }


}