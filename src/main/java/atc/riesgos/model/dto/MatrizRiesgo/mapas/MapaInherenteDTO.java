package atc.riesgos.model.dto.MatrizRiesgo.mapas;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MapaInherenteDTO {
    private List<ValoracionExposicionDTO> listValoracionExposicionDTO;
    private PerfilRiesgoInherenteDTO perfilRiesgoInherenteDTO;

    public MapaInherenteDTO(List<ValoracionExposicionDTO> listValoracionExposicionDTO, PerfilRiesgoInherenteDTO perfilRiesgoInherenteDTO) {
        this.listValoracionExposicionDTO = listValoracionExposicionDTO;
        this.perfilRiesgoInherenteDTO = perfilRiesgoInherenteDTO;
    }
}