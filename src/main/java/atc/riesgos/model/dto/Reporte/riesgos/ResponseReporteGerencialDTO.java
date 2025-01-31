package atc.riesgos.model.dto.Reporte.riesgos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ResponseReporteGerencialDTO {

    private Long apetitoRiesgo;
    private List<DatoMeses> datoMeses;

    // Constructor vacío necesario para la inicialización sin parámetros
    public ResponseReporteGerencialDTO() {
    }

    public ResponseReporteGerencialDTO(Long apetitoRiesgo, List<DatoMeses> datoMeses) {
        this.apetitoRiesgo = apetitoRiesgo;
        this.datoMeses = datoMeses;
    }

    @Getter
    @Setter
    public static class DatoMeses {
        private int anio;
        private String mes;
        private BigDecimal totalPerdida;
        private BigDecimal porcentajeTotalPerdida;

        // Constructor vacío
        public DatoMeses() {
        }

        public DatoMeses(int anio, String mes, BigDecimal totalPerdida, BigDecimal porcentajeTotalPerdida) {
            this.anio = anio;
            this.mes = mes;
            this.totalPerdida = totalPerdida;
            this.porcentajeTotalPerdida = porcentajeTotalPerdida;
        }
    }
}