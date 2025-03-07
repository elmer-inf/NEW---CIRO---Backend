package atc.riesgos.model.dto.Reporte.oportunidades.mapas;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class MapaResumenDTO {
    private String val1;
    private String val2;
    private String val3;
    private String val4;

    public MapaResumenDTO(String val1, String val2, String val3, String val4) {
        this.val1 = val1;
        this.val2 = val2;
        this.val3 = val3;
        this.val4 = val4;
    }
}
