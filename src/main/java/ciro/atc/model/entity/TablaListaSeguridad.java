package ciro.atc.model.entity;

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
@Table(name = "tbl_tabla_lista_seguridad")

public class TablaListaSeguridad implements Serializable  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lis_id")
    private Long id;

    @Column(name = "lis_nombre_tabla", length = 100)
    private String nombreTabla;

    // FK de Usuario
    @Column(name = "lis_usuario_id")
    private int usuarioId;

    @JsonIgnore
    @OneToMany(mappedBy = "tablaId", cascade = CascadeType.ALL)
    private List<TablaDescripcionSeguridad> tablaDescripcionSeguridad;

    @CreationTimestamp
    @Column(name = "lis_dateTimeCreate")
    private Timestamp created;

    @UpdateTimestamp
    @Column(name = "lis_dateTimeUpdate")
    private Timestamp updated;

    @Column(name = "lis_delete")
    private boolean deleted;

    final public TablaListaSeguridad deleteOnly() {
        this.deleted = true;
        return this;
    }

}


