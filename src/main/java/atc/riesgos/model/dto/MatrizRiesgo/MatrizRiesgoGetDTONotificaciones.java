package atc.riesgos.model.dto.MatrizRiesgo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class MatrizRiesgoGetDTONotificaciones {

    private Long idRiesgo;
    private int nroPlan;
    private String codigo;
    private String descripcion;
    private String fechaImpl;
    private String estado;
    private String cargo;
    private String informadoPorCorreo;
    private String correCargo;

    public MatrizRiesgoGetDTONotificaciones(Long idRiesgo, int nroPlan, String codigo, String descripcion, String fechaImpl, String estado, String cargo, String informadoPorCorreo, String correoCargo) {
        this.idRiesgo = idRiesgo;
        this.nroPlan = nroPlan;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.fechaImpl = fechaImpl;
        this.estado = estado;
        this.cargo = cargo;
        this.informadoPorCorreo = informadoPorCorreo;
        this.correCargo = correoCargo;
    }
}
