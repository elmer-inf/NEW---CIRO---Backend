package atc.riesgos.model.dto.MatrizRiesgo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter

public class MatrizRiesgoGetDTONotificaciones {

    private Long id;
    private String codigo;
    private String descripcion;
    private String fechaImpl;
    private String estado;


    public MatrizRiesgoGetDTONotificaciones(Long id, String codigo, String descripcion, String fechaImpl, String estado) {
        this.id = id;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.fechaImpl = fechaImpl;
        this.estado = estado;
    }
}
