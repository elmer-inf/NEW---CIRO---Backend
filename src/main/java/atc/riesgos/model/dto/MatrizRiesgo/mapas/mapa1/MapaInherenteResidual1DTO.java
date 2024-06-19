package atc.riesgos.model.dto.MatrizRiesgo.mapas;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MapaInherenteResidual1DTO {
    private List<ValoracionExposicionDTO> listValoracionExposicionDTO;
    private PerfilRiesgoDTO perfilRiesgoInherenteDTO;

    public MapaInherenteResidual1DTO(List<ValoracionExposicionDTO> listValoracionExposicionDTO, PerfilRiesgoDTO perfilRiesgoInherenteDTO) {
        this.listValoracionExposicionDTO = listValoracionExposicionDTO;
        this.perfilRiesgoInherenteDTO = perfilRiesgoInherenteDTO;
    }
}