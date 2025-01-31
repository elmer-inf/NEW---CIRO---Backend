package atc.riesgos.model.dto.Reporte.oportunidades;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class FiltroReporteConfigOportunidad {

    private List<DataColumn> dataColumns;
    private DataFilter dataFilter;

    public FiltroReporteConfigOportunidad() {
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
}