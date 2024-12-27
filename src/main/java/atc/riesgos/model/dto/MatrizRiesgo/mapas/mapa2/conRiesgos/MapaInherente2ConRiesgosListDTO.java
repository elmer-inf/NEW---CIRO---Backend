package atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa2.conRiesgos;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class MapaInherente2ConRiesgosListDTO {

    private List<MapaInherente2ConRiesgosDTO> listMapaInherente2ConRiesgosDTO;

    public MapaInherente2ConRiesgosListDTO() {
        this.listMapaInherente2ConRiesgosDTO = new ArrayList<>();
    }
}