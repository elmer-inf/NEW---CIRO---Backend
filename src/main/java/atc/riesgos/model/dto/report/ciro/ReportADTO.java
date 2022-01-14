package atc.riesgos.model.dto.report.ciro;
// 2.1. Evento de Riesgo Operativo

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ReportADTO extends MainReport {

    private String tipoEntidad;
    private String descripcionResumida;
    private String factorRiesgo;
    private String cargoInvolucrado;
    private String areaInvolucrada;
    private String categoria;
    private String perdidaRiesgoOperativoContable;
    private String perdidaRiesgoOperativoMercado;
    private String gastoAsociadoPerdida;
    private String montoTotalRecuperado;
    private String montoRecuperadoCoberturaSeguro;
    private String recuperacionActivo;
    private String relacionRiesgoCredito;
    private Integer eventoCritico;
    private String detalleEventoCritico;
    private String monedaMontoEvento;
    private Date fechaDescubrimiento;
    private String horaDescubrimiento;
    private Date fechaInicio;
    private String horaInicio;
    private Date fechaFinalizacion;
    private String horaFinalizacion;
    private String elaborador;
    private String revisor;
    private String qprobador;
    private Integer estadoEvento;
    private String detalleEstadoEvento;
    private String codigoEventoRelacionado;

    public ReportADTO() {
    }

    public ReportADTO(String tipoEntidad, String descripcionResumida, String factorRiesgo, String cargoInvolucrado, String areaInvolucrada, String categoria, String perdidaRiesgoOperativoContable, String perdidaRiesgoOperativoMercado, String gastoAsociadoPerdida, String montoTotalRecuperado, String montoRecuperadoCoberturaSeguro, String recuperacionActivo, String relacionRiesgoCredito, Integer eventoCritico, String detalleEventoCritico, String monedaMontoEvento, Date fechaDescubrimiento, String horaDescubrimiento, Date fechaInicio, String horaInicio, Date fechaFinalizacion, String horaFinalizacion, String elaborador, String revisor, String qprobador, Integer estadoEvento, String detalleEstadoEvento, String codigoEventoRelacionado) {
        this.tipoEntidad = tipoEntidad;
        this.descripcionResumida = descripcionResumida;
        this.factorRiesgo = factorRiesgo;
        this.cargoInvolucrado = cargoInvolucrado;
        this.areaInvolucrada = areaInvolucrada;
        this.categoria = categoria;
        this.perdidaRiesgoOperativoContable = perdidaRiesgoOperativoContable;
        this.perdidaRiesgoOperativoMercado = perdidaRiesgoOperativoMercado;
        this.gastoAsociadoPerdida = gastoAsociadoPerdida;
        this.montoTotalRecuperado = montoTotalRecuperado;
        this.montoRecuperadoCoberturaSeguro = montoRecuperadoCoberturaSeguro;
        this.recuperacionActivo = recuperacionActivo;
        this.relacionRiesgoCredito = relacionRiesgoCredito;
        this.eventoCritico = eventoCritico;
        this.detalleEventoCritico = detalleEventoCritico;
        this.monedaMontoEvento = monedaMontoEvento;
        this.fechaDescubrimiento = fechaDescubrimiento;
        this.horaDescubrimiento = horaDescubrimiento;
        this.fechaInicio = fechaInicio;
        this.horaInicio = horaInicio;
        this.fechaFinalizacion = fechaFinalizacion;
        this.horaFinalizacion = horaFinalizacion;
        this.elaborador = elaborador;
        this.revisor = revisor;
        this.qprobador = qprobador;
        this.estadoEvento = estadoEvento;
        this.detalleEstadoEvento = detalleEstadoEvento;
        this.codigoEventoRelacionado = codigoEventoRelacionado;
    }

    public ReportADTO(Object[] columns) {
        codigoEnvio = (String) columns[0];
        fechaCorte = (String) columns[1];
        codigoEvento = (String) columns[2];
        this.tipoEntidad = (String) columns[3];
        this.descripcionResumida = (String) columns[4];
        this.factorRiesgo = (String) columns[5];
        this.cargoInvolucrado = (String) columns[6];
        this.areaInvolucrada = (String) columns[7];
        this.categoria = (String) columns[8];
        this.perdidaRiesgoOperativoContable = (String) columns[9];
        this.perdidaRiesgoOperativoMercado = (String) columns[10];
        this.gastoAsociadoPerdida = (String) columns[11];
        this.montoTotalRecuperado = (String) columns[12];
        this.montoRecuperadoCoberturaSeguro = (String) columns[13];
        this.recuperacionActivo = (String) columns[14];
        this.relacionRiesgoCredito = (String) columns[15];
        this.eventoCritico = (Integer) columns[16];
        this.detalleEventoCritico = (String) columns[17];
        this.monedaMontoEvento = (String) columns[18];
        this.fechaDescubrimiento = (Date) columns[19];
        this.horaDescubrimiento = (String) columns[20];
        this.fechaInicio = (Date) columns[21];
        this.horaInicio = (String) columns[22];
        this.fechaFinalizacion = (Date) columns[23];
        this.horaFinalizacion = (String) columns[24];
        this.elaborador = (String) columns[25];
        this.revisor = (String) columns[26];
        this.qprobador = (String) columns[27];
        this.estadoEvento = (Integer) columns[28];
        this.detalleEstadoEvento = (String) columns[29];
        this.codigoEventoRelacionado = (String) columns[30];
        tipoEnvio = (String) columns[31];

    }
}
