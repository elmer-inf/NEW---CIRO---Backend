package atc.riesgos.model.dto.Reporte.oportunidades.mapas.conOportunidades;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class MapaInherenteConOportunidadesDTO {
    private List<String> col1;
    private List<String> col2;
    private List<String> col3;
    private List<String> col4;
    private List<String> col5;

    public MapaInherenteConOportunidadesDTO() {
        this.col1 = new ArrayList<>();
        this.col2 = new ArrayList<>();
        this.col3 = new ArrayList<>();
        this.col4 = new ArrayList<>();
        this.col5 = new ArrayList<>();
    }
}