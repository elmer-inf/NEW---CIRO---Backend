package atc.riesgos.model.dto.MatrizRiesgo.mapas;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class PerfilRiesgoDTO {

    private String prob;
    private int probabilidad;
    private Float factorProbabilidad;
    private String probabilidadDesc;
    private Float impactoPor;
    private int impacto;
    private String valoracionImpacto;
    private Float montoRiesgo;
    private int valoracionRiesgo;
    private String riesgo;

    public PerfilRiesgoDTO(String prob, int probabilidad, Float factorProbabilidad, String probabilidadDesc,
                           Float impactoPor, int impacto, String valoracionImpacto, Float montoRiesgo,
                           int valoracionRiesgo, String riesgo) {
        this.prob = prob;
        this.probabilidad = probabilidad;
        this.factorProbabilidad = factorProbabilidad;
        this.probabilidadDesc = probabilidadDesc;
        this.impactoPor = impactoPor;
        this.impacto = impacto;
        this.valoracionImpacto = valoracionImpacto;
        this.montoRiesgo = montoRiesgo;
        this.valoracionRiesgo = valoracionRiesgo;
        this.riesgo = riesgo;
    }
}