package ciro.atc.model.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Setter
@Getter
@Entity
@Table(name = "tbl_tabla_descripcion")

public class TablaDescripcion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "des_id", nullable = false)
    private Long id;

    @Column(name = "des_clave", length = 20)
    private String clave;

    @Column(name = "des_nombre", length = 500)
    private String nombre;

    @Column(name = "des_descripcion", length = 500)
    private String descripcion;

    // FK de Usuario
    @Column(name = "des_usuario_id", length = 10)
    private int usuario_id;

    @ManyToOne//(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "des_tabla_id", nullable = true)
    //@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private TablaLista tablaLista;

    @Column(name = "des_nivel2_id", length = 500)
    private int nivel2_id;

    @Column(name = "des_nivel3_id", length = 500)
    private int nivel3_id;

    /*@ManyToOne(fetch = FetchType.LAZY, optional=true)
    @JoinTable(
            name = "tbl_lista_descripcion",
            joinColumns = @JoinColumn(name = "descripcion_id"),
            inverseJoinColumns = @JoinColumn(name = "lista_id")
    )
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private TablaLista tablaLista;*/

    @CreationTimestamp
    @Column(name = "des_dateTimeCreate", nullable = true)
    private Timestamp created;

    @UpdateTimestamp
    @Column(name = "des_dateTimeUpdate", nullable = true)
    private Timestamp updated;

    @Column(name = "des_delete")
    private boolean deleted;

    final public TablaDescripcion deleteOnly() {
        this.deleted = true;
        return this;
    }


}
