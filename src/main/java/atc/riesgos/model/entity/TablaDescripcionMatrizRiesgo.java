package atc.riesgos.model.entity;

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
@Table(name = "tbl_tabla_descripcion_matriz_riesgo")

public class TablaDescripcionMatrizRiesgo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "des_id")
    private Long id;

    @Column(name = "des_nombre", length = 1000)
    private String nombre;

    @Column(name = "des_campo_a", length = 1000)
    private String campoA;

    @Column(name = "des_campo_b", length = 1000)
    private String campoB;

    @Column(name = "des_campo_c", length = 1000)
    private String campoC;

    @Column(name = "des_campo_d", length = 1000)
    private String campoD;

    @Column(name = "des_campo_e", length = 1000)
    private Float campoE;

    @Column(name = "des_campo_f", length = 1000)
    private Float campoF;

    @Column(name = "des_campo_g", length = 1000)
    private String campoG;



    @Column(name = "des_usuario_id")
    private int usuarioId;

    @ManyToOne
    @JoinColumn(name = "des_tabla_id")
    private TablaListaMatrizRiesgo tablaId;

    @Column(name = "des_nivel2_id")
    private int nivel2_id;

    @CreationTimestamp
    @Column(name = "des_dateTimeCreate")
    private Timestamp created;

    @UpdateTimestamp
    @Column(name = "des_dateTimeUpdate")
    private Timestamp updated;

    @Column(name = "des_delete")
    private boolean deleted;

    final public TablaDescripcionMatrizRiesgo deleteOnly() {
        this.deleted = true;
        return this;
    }


}
