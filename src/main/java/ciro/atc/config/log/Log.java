package ciro.atc.config.log;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

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
    //</editor-fold>

}
