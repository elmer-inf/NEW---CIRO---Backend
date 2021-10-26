package ciro.atc.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "tbl_tabla_archivo")

public class Archivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "arc_id", nullable = false)
    private Long id;

    @Column(name = "arc_archivo", nullable = true)
    private String archivo;

    @ManyToOne
    @JoinColumn(name = "arc_evento_id", nullable = true)
    private EventoRiesgo eventoId;

    @Column(name = "arc_usuario_id", nullable = true, length = 10)
    private int usuarioId;

    @CreationTimestamp
    @Column(name = "arc_dateTimeCreate", nullable = true)
    private Timestamp created;

    @UpdateTimestamp
    @Column(name = "arc_dateTimeUpdate", nullable = true)
    private Timestamp updated;

    @Column(name = "arc_delete")
    private boolean deleted;

    final public Archivo deleteOnly() {
        this.deleted = true;
        return this;
    }



}
