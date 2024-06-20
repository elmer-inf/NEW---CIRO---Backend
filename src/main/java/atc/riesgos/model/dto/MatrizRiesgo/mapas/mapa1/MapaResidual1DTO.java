package atc.riesgos.model.dto.MatrizRiesgo.mapas.mapa1;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MapaResidual1DTO {
    private List<ValoracionExposicionDTO> listValoracionExposicionDTO;
    private PerfilRiesgoDTO perfilRiesgoDTO;

    public MapaResidual1DTO(List<ValoracionExposicionDTO> listValoracionExposicionDTO, PerfilRiesgoDTO perfilRiesgoDTO) {
        this.listValoracionExposicionDTO = listValoracionExposicionDTO;
        this.perfilRiesgoDTO = perfilRiesgoDTO;
    }
}