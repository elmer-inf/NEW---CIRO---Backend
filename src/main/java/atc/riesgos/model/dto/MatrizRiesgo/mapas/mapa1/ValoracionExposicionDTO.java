package atc.riesgos.model.dto.MatrizRiesgo.mapas;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter

public class ValoracionExposicionDTO {

    private int nro;
    private String codigo;
    private String macroproceso;
    private int cantidad;
    private String prob;
    private int probabilidad;
    private Float factorProbabilidad;
    private String probabilidadDesc;
    private Float impactoPor;
    private int impacto;
    private String impactoDesc;
    private Float montoRiesgo;
    private int valoracionRiesgo;
    private String riesgo;

    public ValoracionExposicionDTO(int nro, String codigo, String macroproceso, int cantidad, String prob, int probabilidad, Float factorProbabilidad,
                                   String probabilidadDesc, Float impactoPor, int impacto, String impactoDesc, Float montoRiesgo, int valoracionRiesgo, String riesgo) {
        this.nro = nro;
        this.codigo = codigo;
        this.macroproceso = macroproceso;
        this.cantidad = cantidad;
        this.prob = prob;
        this.probabilidad = probabilidad;
        this.factorProbabilidad = factorProbabilidad;
        this.probabilidadDesc = probabilidadDesc;
        this.impactoPor = impactoPor;
        this.impacto = impacto;
        this.impactoDesc = impactoDesc;
        this.montoRiesgo = montoRiesgo;
        this.valoracionRiesgo = valoracionRiesgo;
        this.riesgo = riesgo;
    }
}
