package atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa2;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class MapaResumenDTO {
    private String val1;
    private String val2;
    private String val3;
    private String val4;
    private String val5;

    public MapaResumenDTO(String val1, String val2, String val3, String val4, String val5) {
        this.val1 = val1;
        this.val2 = val2;
        this.val3 = val3;
        this.val4 = val4;
        this.val5 = val5;
    }
}
