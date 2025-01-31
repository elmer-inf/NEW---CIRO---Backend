package atc.riesgos.model.dto.report.ciro.riesgos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class FiltroReporteConfigRiesgo {

    private List<DataColumn> dataColumns;
    private DataFilter dataFilter;
    private String estadoPlan;

    public FiltroReporteConfigRiesgo() {
        // Constructor vacío
    }

    @Getter
    @Setter
    public static class DataColumn {
        private int id;
        private String label;

        public DataColumn() {
            // Constructor vacío
        }
    }

    @Getter
    @Setter
    public static class DataFilter {
        private Date fechaDesde;
        private Date fechaHasta;

        public DataFilter() {
            // Constructor vacío
        }
    }

    @Setter
    @Getter
    public static class FiltroReporteGerencial {

        private Date fechaDesde;
        private Date fechaHasta;
    }
}