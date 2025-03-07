package atc.riesgos.model.dto.Reporte.oportunidades.mapas;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MapaInherenteOportunidadDTO {
    private List<MapaInherenteDTO> listMapaInherenteDTO;
    private List<MapaResumenDTO> listMapaResumenDTO;

    public MapaInherenteOportunidadDTO(List<MapaInherenteDTO> listMapaInherenteDTO, List<MapaResumenDTO> listMapaResumenDTO) {
        this.listMapaInherenteDTO = listMapaInherenteDTO;
        this.listMapaResumenDTO = listMapaResumenDTO;
    }
}