package atc.riesgos.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@Setter
@Getter
@Entity

@Table(name = "tbl_seguridad")

public class Seguridad implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seg_id")
    private Long id;

    @Column(name = "seg_fecha_registro")
    private Date fechaRegistro;

    @Column(name = "seg_ip_url", length = 200)
    private String ipUrl;

    @Column(name = "seg_activo_informacion", length = 500)
    private String activoInformacion;

    @Column(name = "seg_red", length = 200)
    private String red;

    @Column(name = "seg_descripcion_riesgo", length = 2000)
    private String descripcionRiesgo;

    @Column(name = "seg_recomendacion", length = 1000)
    private String recomendacion;

    @Column(name = "seg_fecha_solucion")
    private Date fechaSolucion;

    @Column(name = "seg_fecha_limite")
    private Date fechaLimite;

    @Column(name = "seg_plan_trabajo", columnDefinition = "text" )
    private String planTrabajo;

    @Column(name = "seg_informe_emitido", length = 1000)
    private String informeEmitido;

    @Column(name = "seg_ci_seguimiento", length = 1000)
    private String ciSeguimiento;

    @Column(name = "seg_comentario", length = 1000)
    private String comentario;

    /* -------- RELACION DE PARAMETROS --------- */
    @ManyToOne
    @JoinColumn(name = "seg_tipo_activo_id")
    private TablaDescripcionSeguridad tipoActivoId;

    @ManyToOne
    @JoinColumn(name = "seg_estado_id")
    private TablaDescripcionSeguridad estadoId;

    @ManyToOne
    @JoinColumn(name = "seg_nivel_riesgo_id")
    private TablaDescripcionMatrizRiesgo nivelRiesgoId;

    @ManyToOne
    @JoinColumn(name = "seg_area_id")
    private TablaDescripcion areaId;

    /* -------- FIN RELACION DE PARAMETROS  --------- */

    @Column(name = "seg_usuario_id")
    private int usuarioId;

    @CreationTimestamp
    @Column(name = "seg_dateTimeCreate")
    private Timestamp created;

    @UpdateTimestamp
    @Column(name = "seg_dateTimeUpdate")
    private Timestamp updated;

    @Column(name = "seg_delete")
    private boolean deleted;

    final public Seguridad deleteOnly() {
        this.deleted = true;
        return this;
    }

}