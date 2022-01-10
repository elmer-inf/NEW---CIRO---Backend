package atc.riesgos.model.entity;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Setter
@Getter
@Entity
@Table(name = "tbl_archivo")

public class Archivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "arch_id")
    private Long id;

    @Column(name = "arch_nombre_archivo", length = 500)
    private String nombreArchivo;

    @Column(name = "arch_tipo", length = 50)
    private String tipo;

    @Column(name = "arch_size")
    private Long size;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "arch_base64")
    private byte[] archivoBase64;

    @Column(name="evento_id")
    private Long eventoId;

    //@ManyToOne
    //@JoinColumn(name = "arc_evento_id")
   // private EventoRiesgo eventoId;

    //@Column(name = "arc_usuario_id")
   // private int usuarioId;


    @CreationTimestamp
    @Column(name = "arch_dateTimeCreate")
    private Timestamp created;

    @UpdateTimestamp
    @Column(name = "arch_dateTimeUpdate")
    private Timestamp updated;

    @Column(name = "arch_delete")
    private boolean deleted;

    final public Archivo deleteOnly() {
        this.deleted = true;
        return this;
    }

}
