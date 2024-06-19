package atc.riesgos.model.dto.MatrizRiesgo.mapas;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MapaInherenteResidual2DTO {
    private List<MapaInherente2DTO> listMapaInherente2DTO;
    private List<MapaResidual2DTO> listMapaResidual2DTO;
    private List<MapaResumenDTO> listMapaResumenDTO;

    public MapaInherenteResidual2DTO(List<MapaInherente2DTO> listMapaInherente2DTO, List<MapaResidual2DTO> listMapaResidual2DTO, List<MapaResumenDTO> listMapaResumenDTO) {
        this.listMapaInherente2DTO = listMapaInherente2DTO;
        this.listMapaResidual2DTO = listMapaResidual2DTO;
        this.listMapaResumenDTO = listMapaResumenDTO;
    }
}