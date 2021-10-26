package ciro.atc.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@Entity

@Table(name = "tbl_tabla_evento_riesgo")

public class EventoRiesgo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eve_id", nullable = false)
    private Long id;

    @Column(name = "eve_codigo", length = 50)
    private String codigo;

    @Column(name = "eve_fecha_ini", nullable = false)
    private Date fechaIni;

    @Column(name = "eve_hora_ini", nullable = false)
    private Time horaIni;

    @Column(name = "eve_fecha_desc", nullable = false)
    private Date fechaDesc;

    @Column(name = "eve_hora_desc", nullable = false)
    private Time horaDesc;



    @ManyToOne
    @JoinColumn(name = "eve_agencia_id", nullable = true)
    private TablaDescripcion agenciaId;

    @ManyToOne
    @JoinColumn(name = "eve_ciudad_id", nullable = true)
    private TablaDescripcion ciudadId;

    @ManyToOne
    @JoinColumn(name = "eve_area_id", nullable = true)
    private TablaDescripcion areaID;

   /* @Column(name = "eve_unidad_id", nullable = true)
    private TablaDescripcion unidadId;

    @Column(name = "eve_entidad_afectada", nullable = true)
    private Boolean entidadAfectada;

    @Column(name = "eve_comercio_afectado", nullable = true)
    private Boolean comercioAfectado;

    @Column(name = "eve_entidad_id", nullable = true)
    private TablaDescripcion entidadId;

    @Column(name = "eve_cargo_id", nullable = false)
    private TablaDescripcion CargoId;

    @Column(name = "eve_estado_reportado", nullable = true)
    private String estadoReportado; // reportado, no reportado

    @Column(name = "eve_fuente_inf_id", nullable = true)
    private TablaDescripcion fuenteInfId;

    @Column(name = "eve_canal_asfi_id", nullable = false)
    private TablaDescripcion canalAsfiId;

    @Column(name = "eve_descripcion", nullable = false)
    private String descripcion;

    @Column(name = "eve_descripcion_com", nullable = true)
    private String descripcionCom;*/


    @JsonIgnore
    @OneToMany(mappedBy = "eventoId", cascade = CascadeType.ALL)
    private List<Archivo> archivoId;

    @CreationTimestamp
    @Column(name = "eve_dateTimeCreate", nullable = true)
    private Timestamp created;

    @UpdateTimestamp
    @Column(name = "eve_dateTimeUpdate", nullable = true)
    private Timestamp updated;

    @Column(name = "eve_delete")
    private boolean deleted;

    final public EventoRiesgo deleteOnly() {
        this.deleted = true;
        return this;
    }


}