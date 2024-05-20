package atc.riesgos.model.dto.MatrizRiesgo;

import atc.riesgos.model.entity.TablaDescripcion;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class MatrizRiesgoGetDTOPlanesParaEvento {
    private Long id;
    private String codigo;
    private TablaDescripcion areaId;
    private String planesAccion;

    public MatrizRiesgoGetDTOPlanesParaEvento(Long id, String codigo, TablaDescripcion areaId, String planesAccion) {
        this.id = id;
        this.codigo = codigo;
        this.areaId = areaId;
        this.planesAccion = planesAccion;
    }

}
