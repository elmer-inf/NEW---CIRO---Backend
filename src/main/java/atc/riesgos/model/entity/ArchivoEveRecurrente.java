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
@Table(name = "tbl_archivo_eve_recurrentes")

public class ArchivoEveRecurrente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "arc_id")
    private Long id;

    @Column(name = "arc_nombre_archivo", length = 500)
    private String nombreArchivo;

    @Column(name = "arc_tipo", columnDefinition = "text")
    private String tipo;

    @Column(name = "arc_size")
    private Long size;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "arc_base64")
    private byte[] archivoBase64;

    @Column(name="evento_id")
    private Long eventoId;

    @CreationTimestamp
    @Column(name = "arc_dateTimeCreate")
    private Timestamp created;

    @UpdateTimestamp
    @Column(name = "arc_dateTimeUpdate")
    private Timestamp updated;

    @Column(name = "arc_delete")
    private boolean deleted;

    final public ArchivoEveRecurrente deleteOnly() {
        this.deleted = true;
        return this;
    }

}
