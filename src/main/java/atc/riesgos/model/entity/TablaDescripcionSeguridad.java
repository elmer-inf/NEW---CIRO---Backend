package atc.riesgos.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "tbl_tabla_descripcion_seguridad")

public class TablaDescripcionSeguridad implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "des_id")
    private Long id;

    @Column(name = "des_nombre", length = 1000)
    private String nombre;

    /* -------- RELACION DE PARAMETROS --------- */
    @JsonIgnore
    @OneToMany(mappedBy = "tipoActivoId")
    private List<Seguridad> seguridad1;

    @JsonIgnore
    @OneToMany(mappedBy = "estadoId")
    private List<Seguridad> seguridad2;

    @JsonIgnore
    @OneToMany(mappedBy = "nivelRiesgoId")
    private List<Seguridad> seguridad3;

    @JsonIgnore
    @OneToMany(mappedBy = "areaId")
    private List<Seguridad> seguridad4;

    /* -------- FIN RELACION DE PARAMETROS  --------- */

    @Column(name = "des_usuario_id")
    private int usuario_id;

    @ManyToOne
    @JoinColumn(name = "des_tabla_id")
    private TablaListaSeguridad tablaId;

    @CreationTimestamp
    @Column(name = "des_dateTimeCreate")
    private Timestamp created;

    @UpdateTimestamp
    @Column(name = "des_dateTimeUpdate")
    private Timestamp updated;

    @Column(name = "des_delete")
    private boolean deleted;

    final public TablaDescripcionSeguridad deleteOnly() {
        this.deleted = true;
        return this;
    }

}
