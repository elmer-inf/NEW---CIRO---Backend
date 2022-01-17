package atc.riesgos.config.log;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    final public static Logger logger = LoggerFactory.getLogger(Log.class);
    final private static String JWT = "%s -> Message: {} ";

    final public static String toJSON(Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            return ex.getMessage();
        }
    }


     final public static <T extends Object> T jsonToObject(String data, Class<T> entity) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            T ob = mapper.readValue(data, entity);
            return ob;
        } catch (Exception ex) {
            System.out.println("Error when you try to convert String to Json " + ex.getMessage());
            return null;

        }
    }

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

    static final public String isNull(String str){
        if(!str.equals("null") || !str.equals("Null") || !str.equals("NULL") || str != null){
            return str;
        }

        return "";
    }


    static final public String convertDate(Date date){

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String s = df.format(date);
       // System.out.println("TO STRING::: " + s);
       // String result = s;
       /* try {
            date=df.parse(result);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
        return s;
    }

    //<editor-fold defaultstate="collapsed" desc="Logger">
    final public static void log(Object obj) {
        logger.info(Log.toJSON(obj));
    }

    final public static void log(String message) {
        logger.info(message);
    }

    final public static void auth(String msg, Object exception) {
        logger.info(" ----------- JWT ----------- ");
        logger.info(String.format(JWT, msg), exception);
        logger.info(" ----------- END ----------- ");
    }

    final public static void log(String message, Object exception) {
        logger.info(message, exception);
    }

    final public static void log(String message, Object... exception) {
        logger.info(message, exception);
    }

    final public static void warn(String message) {
        logger.warn(message);
    }

    final public static void warn(String message, Object exception) {
        logger.warn(message, exception);
    }

    final public static void error(String message, Object exception) {
        logger.error(message, exception);
    }
    final public static void error(String message) {
        logger.error(message);
    }
    //</editor-fold>

}
