package atc.riesgos.model.dto.Reporte.oportunidades.mapas.conOportunidades;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class MapaInherenteConOportunidadesListDTO {

    private List<MapaInherenteConOportunidadesDTO> listMapaInherenteConOportunidadesDTO;

    public MapaInherenteConOportunidadesListDTO() {
        this.listMapaInherenteConOportunidadesDTO = new ArrayList<>();
    }
}