package atc.riesgos.model.dto.Reporte.riesgos.mapas.mapa2.conRiesgos;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class MapaResidual2ConRiesgosListDTO {

    private List<MapaResidual2ConRiesgosDTO> listMapaResidual2ConRiesgosDTO;

    public MapaResidual2ConRiesgosListDTO() {
        this.listMapaResidual2ConRiesgosDTO = new ArrayList<>();
    }
}