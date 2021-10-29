package ciro.atc.model.entity;

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
@Table(name = "tbl_observacion_evento")

public class Observacion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "obs_id")
    private Long id;

    @Column(name = "obs_lista_observacion", length = 500)
    private String listaObservacion;

    @Column(name = "obs_nota", length = 1000)
    private String nota;

    @Column(name = "obs_estado", length = 50)
    private String estado;

    @ManyToOne
    @JoinColumn(name = "obs_evento_id")
    private EventoRiesgo eventoId;

    @Column(name = "obs_usuario_id")
    private int usuarioId;

    @CreationTimestamp
    @Column(name = "obs_dateTimeCreate")
    private Timestamp created;

    @UpdateTimestamp
    @Column(name = "obs_dateTimeUpdate")
    private Timestamp updated;

    @Column(name = "obs_delete")
    private boolean deleted;

    final public Observacion deleteOnly() {
        this.deleted = true;
        return this;
    }
}
