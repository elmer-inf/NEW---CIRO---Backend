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
@Table(name = "tbl_tabla_lista")

public class TablaLista implements Serializable  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lis_id")
    private Long id;

    @Column(name = "lis_nombre_tabla", length = 100)
    private String nombre_tabla;

    // FK de Usuario
    @Column(name = "lis_usuario_id")
    private int usuario_id;

    @JsonIgnore
    @OneToMany(mappedBy = "tablaLista", cascade = CascadeType.ALL)
    private List<TablaDescripcion> tablaDescripcion;//private Set<Card> cards;

    @Column(name = "lis_nivel2")
    private int nivel2;

    @Column(name = "lis_nivel3")
    private int nivel3;

    @CreationTimestamp
    @Column(name = "lis_dateTimeCreate")
    private Timestamp created;

    @UpdateTimestamp
    @Column(name = "lis_dateTimeUpdate")
    private Timestamp updated;

    @Column(name = "lis_delete")
    private boolean deleted;

    final public TablaLista deleteOnly() {
        this.deleted = true;
        return this;
    }

}


