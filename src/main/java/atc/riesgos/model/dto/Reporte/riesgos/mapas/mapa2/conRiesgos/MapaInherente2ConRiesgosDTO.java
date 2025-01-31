package atc.riesgos.model.dto.Reporte.riesgos.mapas.mapa2.conRiesgos;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class MapaInherente2ConRiesgosDTO {
    private List<String> col1;
    private List<String> col2;
    private List<String> col3;
    private List<String> col4;
    private List<String> col5;

    public MapaInherente2ConRiesgosDTO() {
        this.col1 = new ArrayList<>();
        this.col2 = new ArrayList<>();
        this.col3 = new ArrayList<>();
        this.col4 = new ArrayList<>();
        this.col5 = new ArrayList<>();
    }
}