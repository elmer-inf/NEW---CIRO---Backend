package atc.riesgos.auth;


import atc.riesgos.auth.util.JPA;
import atc.riesgos.config.log.Log;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.sql.Date;
import java.util.*;
import java.util.function.Function;

//import static atc.sired.util.JPA.*;

/**
 *
 * @author Grupo TIC
 * @copyright (c) 2019, ATC - Red Enlace
 * @developer David Quenallata, dqpsoft@gmail.com
 */
public abstract class Controller {


    private static Long N;
    private static Long R;

    @PersistenceContext
    protected EntityManager manager;

    public Controller() {
        Controller.N = 1L;
        Controller.R = 1L;
    }

    //<editor-fold defaultstate="collapsed" desc="Util">
    final protected NativeQuery nativeQuery(String sql) {

        Session sess = (Session) manager.getDelegate();

        return sess.createSQLQuery(sql);
    }

    final protected <T extends Object> QueryHQL createHQL(
            Class<T> model, Function<QueryHQL<T>, QueryHQL<T>> query
    ) {

        return query.apply(new QueryHQL<>(model));
    }

    final protected <T extends Object> QueryHQL createHQL(Class<T> model) {

        return new QueryHQL<>(model);
    }

    final protected List<Long> ids(String hql, Function<Query, Query> query) {

        return query.apply(manager.createQuery(hql)).getResultList();
    }

    final protected <T extends Object> List<T> list(
            String hql, Class<T> model, Function<TypedQuery<T>, TypedQuery<T>> query
    ) {

        return query.apply(manager.createQuery(hql, model)).getResultList();
    }

    final protected <T extends Object> List<T> to(
            String hql, Class<T> model, Function<TypedQuery<T>, TypedQuery<T>> query
    ) {

        return query.apply(manager.createQuery(hql, model)).getResultList();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Hibernate Query Language">
    /**
     *
     * @author Grupo TIC
     * @copyright (c) 2019, ATC - Red Enlace
     * @developer David Quenallata, dqpsoft@gmail.com
     * @param <T>
     */
    public class QueryHQL<T extends Object> {

        private String name;

        private String sort = "";
        private String where = "";
        private String op = JPA.AND;

        final private Long as;
        final private Class<T> entity;
        final private List<String> names = new ArrayList();
        final private Map<String, Long> join = new HashMap();
        final private Map<String, Object> map = new HashMap();

        final private static String LK = "%2$s%1$s%2$s";
        final private static String CLEAR = "^\\s(AND|OR)";
        final private static String BOOLEAN = "^(true|false)$";
        final private static String IS_JOIN = "^([a-z][\\w]+\\.[a-z][\\w]+)+$";
        final private static String SELECT_IN = "SELECT distinct a%3$s_.%1$s %2$s";

        final private static String LONG = "^(\\+|-)?[0-9]+$";
        final private static String INTEGER = "^(\\+|-)?i([0-9]+)$";
        final private static String DOUBLE = "^(\\+|-)?([0-9]+)\\.([0-9]+)$";
        final protected static String DATE = "^\\d{4}-([0-1])?[0-9]-([0-3])?[0-9]$";

        final private static String TO_STRING = "^(telefono|dni|nit|cedula|nroTarjeta)+$";

        //<editor-fold defaultstate="collapsed" desc="Constructor">
        public QueryHQL(Class<T> entity) {
            this.as = N++;
            this.entity = entity;
            this.name = className();
        }

        private String className() {
            String temp = entity.getAnnotation(Entity.class).name();
            String className = temp.isEmpty() ? entity.getSimpleName() : temp;
            return String.format(JPA.ENTITY, className, this.as);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Where">
        final public QueryHQL or() {
            this.op = JPA.OR;
            return this;
        }

        final public QueryHQL and() {
            this.op = JPA.AND;
            return this;
        }

        private QueryHQL where(String clause) {
            where += String.format(op, clause);
            return this;
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Operators">
        final public QueryHQL map(HttpServletRequest data) {
            Enumeration<String> columns = data.getParameterNames();
            while (columns.hasMoreElements()) {
                String col = columns.nextElement();
                parse(col, data.getParameter(col));
            }
            return this;
        }

        final public QueryHQL eq(String name, Object value) {
            return add(name, value, "=");
        }

        final public QueryHQL gt(String name, Object value) {
            return add(name, value, ">");
        }

        final public QueryHQL gte(String name, Object value) {
            return add(name, value, ">=");
        }

        final public QueryHQL lt(String name, Object value) {
            return add(name, value, "<");
        }

        final public QueryHQL lte(String name, Object value) {
            return add(name, value, "<=");
        }

        final public QueryHQL ne(String name, Object value) {
            return add(name, value, "<>");
        }

        final public QueryHQL is(String name, Object... value) {
            return to(name, value, JPA.IS);
        }

        final public QueryHQL nis(String name, Object... value) {
            return to(name, value, JPA.IS_NOT);
        }

        final public QueryHQL not(String name, Object value) {
            return add(name, value, "is not");
        }

        final public QueryHQL lk(String name, String value) {
            return like(JPA.LIKE, name, value);
        }

        final public QueryHQL nlk(String name, String value) {
            return like(JPA.NOT_LIKE, name, value);
        }

        final public QueryHQL set(String name, Object value) {
            map.put(String.format(JPA.NAME, name, as), value);
            return this;
        }

        final public QueryHQL op(String name, Object value, String op) {
            return add(name, value, op);
        }

        final public QueryHQL join(String name, String col, Object val) {
            return add(col, val, "=", as(name));
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Process">
        private Long as(String key) {
            if (!join.containsKey(key)) {
                join.put(key, R);
                name += String.format(JPA.JOIN, key, R++, as);
            }
            return join.get(key);
        }

        private QueryHQL add(String col, Object val, String op, Long as) {
            map.put(String.format(JPA.RNAME, col, as), val);
            System.out.println("printttttttttttt");
            Log.log(map);
            Log.log(String.format(JPA.RWHERE, col, op, as));
            return where(String.format(JPA.RWHERE, col, op, as));
        }

        final protected QueryHQL add(String name, Object value, String op) {
            map.put(String.format(JPA.NAME, name, as), value);
            return where(String.format(JPA.WHERE, name, op, as));
        }

        final protected QueryHQL by(String name, Object start, Object end) {
            map.put(String.format(JPA.END, name, as), end);
            map.put(String.format(JPA.START, name, as), start);
            return where(String.format(JPA.BETWEEN, name, op, as));
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Mapper">
        private Object to(String name, String value) {
            return name.matches(TO_STRING) ? value
                    : value.matches(DATE) ? Date.valueOf(value)
                    : value.matches(INTEGER) ? Integer.parseInt(value.replace("i", ""))
                    : value.matches(DOUBLE) ? Double.parseDouble(value)
                    : value.matches(LONG) ? Long.parseLong(value)
                    : value.matches(BOOLEAN) ? Boolean.valueOf(value) : value;
        }

        private void parse(String name, String value) {
            Object data = to(name, value);
            if (name.matches(IS_JOIN)) {
                map(name.split("\\."), data);
            } else if (data instanceof Boolean) {
                add(name, data, "is");
            } else if (data instanceof String) {
                lk(name, "%" + data + "%");
            } else {
                add(name, data, "=");
            }
        }

        private void map(String[] name, Object data) {
            if (data instanceof Boolean) {
                add(name[1], data, "is", as(name[0]));
            } else if (data instanceof String) {
                lk(name[1], "%" + data + "%", as(name[0]));
            } else {
                add(name[1], data, "=", as(name[0]));
            }
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Util">
        private void lk(String col, String val, Long as) {
            map.put(String.format(JPA.RNAME, col, as), val);
            where(String.format(JPA.RLIKE, col, as));
        }

        private QueryHQL like(String op, String name, String value) {
            map.put(String.format(JPA.NAME, name, as), value.toLowerCase());
            return where(String.format(op, name, as));
        }

        private QueryHQL func(String mode, String name) {
            names.add(String.format(mode, name, as));
            return this;
        }

        private QueryHQL toIn(String op, QueryHQL q, String c, String s) {
            map.putAll(q.map);
            return where(String.format(op, c, q.select(s), as));
        }

        private QueryHQL to(String name, Object[] val, String op) {
            String type = val.length > 0 ? val[0].toString() : "null";
            return where(String.format(op, name, type, as));
        }

        private QueryHQL in(String name, List<Object> values, String op) {
            StringJoiner joiner = new StringJoiner(",");
            values.forEach(id -> joiner.add(id.toString()));
            String hql = String.format(op, name, joiner.toString(), as);
            where += String.format(this.op, hql);
            return this;
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Query (IN & NOT IN)">
        final public QueryHQL in(QueryHQL query, String col, String sel) {
            return toIn(JPA.IN, query, col, sel);
        }

        private QueryHQL join(String col, Object[] sel, String in, String op) {
            StringJoiner joiner = new StringJoiner(op);
            for (Object selected : sel) {
                joiner.add(selected.toString());
            }
            in = String.format(in, col, joiner.toString(), as);
            where += String.format(this.op, in);
            return this;
        }

        final public QueryHQL in(String name, String data) {
            where += String.format(this.op, String.format(JPA.IN, name, data, as));
            return this;
        }

        final public QueryHQL in(String name, List<Object> values) {
            return in(name, values, JPA.IN);
        }

        final public QueryHQL in(String name, String... values) {
            return join(name, values, JPA.IN_CHAR, "','");
        }

        final public QueryHQL in(String name, Integer... values) {
            return join(name, values, JPA.IN, ",");
        }

        final public QueryHQL not(QueryHQL query, String col, String sel) {
            return toIn(JPA.NOT_IN, query, col, sel);
        }

        final public QueryHQL not(String name, List<Object> values) {
            return in(name, values, JPA.NOT_IN);
        }

        final public QueryHQL order(String by) {
            by = by == null ? "id" : by;
            sort = String.format(
                    by.matches("\\w+:desc") ? JPA.DESC : JPA.ASC,
                    by.replaceAll(":(desc|asc)", ""), as
            );
            return this;
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Select">
        final public QueryHQL select(String... names) {
            for (String selected : names) {
                func("a%2$d_.%1$s", selected);
            }
            return this;
        }

        final public QueryHQL avg(String name) {
            return func("AVG(a%2$d_.%1$s)", name);
        }

        final public QueryHQL sum(String name) {
            return func("SUM(a%2$d_.%1$s)", name);
        }

        final public QueryHQL year(String name) {
            return func("YEAR(a%2$d_.%1$s)", name);
        }

        final public QueryHQL month(String name) {
            return func("MONTH(a%2$d_.%1$s)", name);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Query">
        final public String sql() {
            String from = join.size() > 0 ? String.format(JPA.SELECT, name, as) : name;
            return String.format(JPA.FROM, from, where, sort, as);
        }

        private String sql(String to) {
            String columns = String.join(", ", names);
            String by = where.replaceAll(CLEAR, " WHERE");
            String select = String.format("new %s(%s)", to, columns);
            return String.format(JPA.TO, select, name, by, sort);
        }

        private String select(String name) {
            return String.format(SELECT_IN, name, sql(), as);
        }

        private Long count() {
            Query query = manager.createQuery(String.format(JPA.COUNT, name, where, as));
            map.keySet().forEach(key -> query.setParameter(key, map.get(key)));
            return (Long) query.getSingleResult();
        }

        private TypedQuery<T> getQuery(String hql) {
            TypedQuery<T> query = manager.createQuery(hql, entity);
            map.keySet().forEach(key -> query.setParameter(key, map.get(key)));
            return query;
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="GET Result List">
        final public Paging paging(int page, int size) {
            Long total = count();
            return new Paging(getQuery(sql()).setMaxResults(size)
                    .setFirstResult(page * size).getResultList(), new Data(
                    page, size, total, total.doubleValue() / size
            ));
        }

        final public <S extends Object> List<S> list(Class<S> model) {
            Query query = manager.createQuery(sql(model.getName()));
            map.keySet().forEach(key -> query.setParameter(key, map.get(key)));
            return (List<S>) query.getResultList();
        }

        final public List<T> all() {
            String by = where.replaceAll(CLEAR, " WHERE");
            String jpa = String.format("%s%s%s", name, by, sort);
            return getQuery(jpa).getResultList();
        }

        final public List<T> list() {
            return getQuery(sql()).getResultList();
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="GET Result Get">
        final public Optional<T> get() {
            List<T> collection = getQuery(sql()).getResultList();
            return Optional.ofNullable(
                    collection.isEmpty() ? null : collection.get(0)
            );
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Pagination Class">
        @lombok.Getter
        public class Paging<R extends Object> {

            final private List<R> data;
            final private Data paging;

            public Paging(List<R> data, Data paging) {
                this.data = data;
                this.paging = paging;
            }

        }

        @lombok.Getter
        public class Data {

            final private Integer size;
            final private Integer page;
            final private Long pages;
            final private Long total;

            public Data(Integer page, Integer size, Long total, Double pages) {

                Double temp = Math.ceil(pages);
                this.page = page;
                this.pages = temp.longValue();
                this.total = total;
                this.size = size;
            }

        }
        //</editor-fold>
    }
    //</editor-fold>

    final public <T extends Object> T delete(T obj) {
        try {
            Class<?> cls = obj.getClass();
            Field field = cls.getDeclaredField("deleted");
            field.setAccessible(true);
            field.set(obj, true);
        } catch (IllegalAccessException | IllegalArgumentException
                | NoSuchFieldException | SecurityException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return obj;
    }
}
