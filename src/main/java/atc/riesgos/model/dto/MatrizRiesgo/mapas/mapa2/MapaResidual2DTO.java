package atc.riesgos.model.dto.MatrizRiesgo.mapas;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MapaResidual2DTO {
    private String val1;
    private String val2;
    private String val3;
    private String val4;
    private String val5;
    private String val6;
    private String val7;
    private String val8;

    public MapaResidual2DTO(String val1, String val2, String val3, String val4, String val5, String val6, String val7, String val8) {
        this.val1 = val1;
        this.val2 = val2;
        this.val3 = val3;
        this.val4 = val4;
        this.val5 = val5;
        this.val6 = val6;
        this.val7 = val7;
        this.val8 = val8;
    }
}