package atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa1;

import atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa1.PerfilRiesgoDTO;
import atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa1.ValoracionExposicionDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MapaInherenteResidual1DTO {
    private List<ValoracionExposicionDTO> listValoracionExposicionDTO;
    private PerfilRiesgoDTO perfilRiesgoDTO;

    public MapaInherenteResidual1DTO(List<ValoracionExposicionDTO> listValoracionExposicionDTO, PerfilRiesgoDTO perfilRiesgoDTO) {
        this.listValoracionExposicionDTO = listValoracionExposicionDTO;
        this.perfilRiesgoDTO = perfilRiesgoDTO;
    }
}