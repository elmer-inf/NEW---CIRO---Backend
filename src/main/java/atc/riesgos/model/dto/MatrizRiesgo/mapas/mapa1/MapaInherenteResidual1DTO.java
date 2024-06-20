package atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa1;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MapaInherenteResidual1DTO {
    private MapaInherente1DTO mapaInherente1DTO;
    private MapaResidual1DTO mapaResidual1DTO;

    public MapaInherenteResidual1DTO(MapaInherente1DTO mapaInherente1DTO, MapaResidual1DTO mapaResidual1DTO) {
        this.mapaInherente1DTO = mapaInherente1DTO;
        this.mapaResidual1DTO = mapaResidual1DTO;
    }
}