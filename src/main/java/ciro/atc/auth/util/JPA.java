package ciro.atc.auth.util;


/**
 *
 * @author Grupo TIC
 * @copyright (c) 2019, ATC - Red Enlace
 * @developer David Quenallata, dqpsoft@gmail.com
 */
public interface JPA {

    final static String ENTITY = "FROM %s a%d_";
    final static String SELECT = "SELECT a%2$d_ %1$s";

    final static String OR = " OR %s";
    final static String AND = " AND %s";

    final static String END = "e%s%d_";
    final static String START = "s%s%d_";

    final static String NAME = "%s%d_";
    final static String RNAME = "r%s%d_";

    final static String WHERE = "a%3$d_.%1$s %2$s :%1$s%3$d_";
    final static String RWHERE = "r%3$d_.%1$s %2$s :r%1$s%3$d_";

    final static String BETWEEN = "a%3$d_.%1$s BETWEEN :s%1$s%3$d_ AND :e%1$s%3$d_";

    final static String IS = "a%3$d_.%1$s is %2$s";
    final static String IS_NOT = "a%3$d_.%1$s is not %2$s";

    final static String GROUP = " GROUP BY a%2$d_.%1$s";
    final static String JOIN = " JOIN a%3$d_.%1$s r%2$d_";

    final static String ASC = " ORDER BY a%2$d_.%1$s asc";
    final static String DESC = " ORDER BY a%2$d_.%1$s desc";

    final static String LIKE = "LOWER(a%2$d_.%1$s) LIKE :%1$s%2$d_";
    final static String RLIKE = "LOWER(r%2$d_.%1$s) LIKE :r%1$s%2$d_";
    final static String NOT_LIKE = "LOWER(a%2$d_.%1$s) NOT LIKE :%1$s%2$d_";

    final static String IN = "a%3$d_.%1$s in (%2$s)";
    final static String IN_CHAR = "a%3$d_.%1$s in ('%2$s')";
    final static String NOT_IN = "a%3$d_.%1$s not in (%2$s)";

    final static String ALL = "%1$s %2$s%3$s";
    final static String TO = "SELECT %1$s %2$s %3$s%4$s";
    final static String FROM = "%1$s WHERE a%4$d_.deleted is false%2$s%3$s";
    final static String COUNT = "SELECT COUNT(1) %1$s WHERE a%3$d_.deleted is false%2$s";

    final static String OPTIONS_CUPON = "select a.lote, a.nombre, sum(a.stock), a.auto from MapCupon as a where a.stock > 0 group by a.lote, a.nombre, a.stock, a.auto";
    final static String OPTIONS_CUPON_TYPE = "select a.lote, a.nombre, sum(a.stock), a.descuento, a.detalle from MapCupon as a where a.stock > 0 AND a.lote = :lote AND a.auto is :auto group by a.descuento, a.lote, a.nombre, a.stock, a.detalle";

    final static String FILTER_BY_ENTIDAD = "SELECT e FROM Entidad e WHERE e.deleted is false AND e.nivel in (?1,2) AND e.id <> ?2";
    final static String ENTIDAD_BY_PATROCINADOR = "SELECT e FROM Entidad e WHERE e.deleted is false AND e.nivel in (1,2) AND e.id in (SELECT distinct p.idEntidad FROM Programa p)";
    final static String CUPON_BY_LOTE = "SELECT c FROM Cupon c WHERE c.deleted is false AND c.auto is false AND c.stockAux > 0 AND c.lote = ?1";
    final static String CUPON_BY_LOTE_AND_DESCUENTO = "SELECT c FROM QCupon c WHERE c.deleted is false AND c.auto is true AND c.stockAux > 0 AND c.lote = ?1 AND c.descuento = ?2";
    final static String LOGIN = "SFROM Login c WHERE c.deleted is false AND c.auto is true AND c.stockAux > 0 AND c.lote = ?1 AND c.descuento = ?2";

    final static String FIDELIZACION_BY_CEDULA_AND_ENTIDAD = "SELECT e FROM Fidelizacion e WHERE e.deleted is false AND e.cedula like ?1 AND e.idEntidad = ?2";

    final static String FIDELIZACION_BY_CLIENTE = "SELECT e FROM Fidelizacion e WHERE (puntos - puntosRedimidos) > 0 AND e.cedula = :cedula AND e.idEntidad = :idComercio";

    //<editor-fold defaultstate="collapsed" desc="Query Menu's POS">
    final static String Q_MENU = "SELECT e1 FROM PInput e1 WHERE e1.deleted = false AND e1.idPromocion IN :ids ORDER BY e1.orden, e1.id DESC";

    final static String Q_CUPON = "SELECT new org.atc.pos.Match(e1.idPromocion%s) FROM Ticket e1 LEFT JOIN QPromocion e2 ON e1.idPromocion = e2.id LEFT JOIN QPrograma e3 ON e3.id = e2.idPrograma LEFT JOIN QCupon e4 ON e4.id = e1.idCuponera WHERE e2.deleted = false AND e3.fechaInicio <= CURRENT_DATE AND e3.fechaFinal >= CURRENT_DATE AND e2.bines IS%s NULL AND e4.idEntidad = :codigo AND e4.stock > 0";
    final static String Q_NORMAL = "SELECT DISTINCT new org.atc.pos.Match(e1.idPromocion%s) FROM QNormal e1 LEFT JOIN QPromocion e2 ON e1.idPromocion = e2.id LEFT JOIN QPrograma e3 ON e3.id = e2.idPrograma WHERE e2.deleted IS false AND e3.fechaInicio <= CURRENT_DATE AND e3.fechaFinal >= CURRENT_DATE AND e2.bines IS%s NULL AND e1.idEntidad = :codigo";

    final static String Q_BY_CUPON = String.format(Q_CUPON, "", "");
    final static String Q_BY_NORMAL = String.format(Q_NORMAL, "", "");

    final static String Q_BY_BIN_CUPON = String.format(Q_CUPON, ", e2.bines", " NOT");
    final static String Q_BY_BIN_NORMAL = String.format(Q_NORMAL, ", e2.bines", " NOT");
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Query chart">
    final static String FILTER = "WHERE e1.create >= :create";

    final static String PROMOCION = "FROM CPromocion e LEFT JOIN CRegla e1 ON e.id = e1.id";
    final static String CHART_PROMOCION = String.format("%s %s", PROMOCION, FILTER);
    final static String CHART_PROMOCION_BY_ENTIDAD = String.format("%s LEFT JOIN CEntidad e2 ON e.idEntidad = e2.id %s AND e2.id = :id", PROMOCION, FILTER);

    final static String CUPON = "FROM CCuponera e LEFT JOIN CCupon e1 ON e.idCuponera = e1.id";
    final static String CHART_ALL_CUPON = String.format("%s %s", CUPON, FILTER);
    final static String CHART_CUPON_BY_TYPE = String.format("%s AND e1.auto is :type", CHART_ALL_CUPON);
    final static String CHART_CUPON_BY_TYPE_AND_ENTIDAD = String.format("%s AND e1.idEntidad = :id", CHART_CUPON_BY_TYPE);
    //</editor-fold>

    final static String MAP_COMERCIO = "SELECT e FROM Comercio e WHERE e.deleted IS false AND e.nivel IN (0,2) AND e.id IN :ids";
    final static String MAP_BY_SALDO = "SELECT new org.atc.base.Ticket(e3.id, e3.regla, e1.cantidad) FROM QAsignado e1 LEFT JOIN QRule e2 ON e2.idPromocion = e1.id LEFT JOIN QPolitica e3 ON e3.id = e2.idPolitica WHERE e1.id = :id";

    final static String COMERCIO_BY_ID = "SELECT e FROM QComercio e WHERE e.id = (SELECT e1.idPadre FROM QComercio e1 WHERE e1.codigoNazir = :codigo)";

    final static String CUPONERA = "SELECT e FROM RCuponera e WHERE e.id = :id";

}
