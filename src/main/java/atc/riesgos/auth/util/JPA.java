package atc.riesgos.auth.util;


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


}
