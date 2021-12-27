package ciro.atc.auth.util;


import org.apache.commons.codec.binary.Base64;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Config {

    private String line;

    final private Map<String, String> values;
    final private StandardPBEStringEncryptor read;

    final public static String CLEAR = "(Bearer|Basic)\\s";
    final public static String URI_POS = "^/cliente/(.*)$";
    final public static String IS_BIN = "^([1-9][0-9]{5,7})$";
    final public static String AUTHORIZATION = "^(Bearer|Basic)\\s(.*)$";

    final public static String NAME = "riesgos.properties";
    final public static String FAILED = "%1$s%3$sfailed%2$s%3$s";
    final public static String PROCESSED = "%1$s%3$sprocessed%2$s%3$s";
    final public static String DS = System.getProperty("file.separator");

    final public static String LDAP_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
    final public static String LDAP_FILTER_BY = "(userPrincipalName|displayName|sAMAccountName)";

    final public static String SRC_PATH = Config.path(System.getProperty("jboss.server.base.dir"));

    final private static Map<String, String> prop = Config.load();

    final public static Integer TIME_OUT = Config.timeOut("app.rest.timeout");

    private Config() {
        values = new HashMap<>();
        read = ciro.atc.auth.util.StringPassword.get();
    }

    private static String path(String src) {
        File file = new File(src, NAME);
        if (!file.exists()) {
            src = String.format("%s%sdeployments", src, DS);
            if (!(file = new File(src, NAME)).exists()) {
                throw new RuntimeException("Properties not found");
            }
        }
        return src;
    }

    final public Map<String, String> get(
            FileReader stream
    ) throws IOException {
        try (BufferedReader reader = new BufferedReader(stream)) {
            while ((line = reader.readLine()) != null) {
                String text = line.trim();
                if (text.length() > 0) {
                    char c = text.charAt(0);
                    if (!(c == '#')) {
                        String[] row = text.split("=", 2);
                        set(row[0], row[1]);
                    }
                }
            }
        }
        return values;
    }

    private static String by(String auth) {
        return auth.isEmpty() ? null : auth.matches(AUTHORIZATION) ? auth : auth.contains(":")
                ? String.format("Basic %s", new String(Base64.encodeBase64(auth.getBytes())))
                : String.format("Bearer %s", auth);
    }

    final public static String enc(String credential) {
        return credential != null ? by(credential.trim()) : null;
    }

    private void set(String key, String val) {
        values.put(key, val.contains("ENC(")
                ? read.decrypt(val.substring(3, val.length() - 1))
                : val);
    }

    private static Map<String, String> load() {
        File file = new File(SRC_PATH, NAME);
        try (FileReader reader = new FileReader(file)) {
            return new Config().get(reader);
        } catch (IOException ex) {
            return new HashMap<>();
        }
    }

    final public static String get(String name) {
        return prop.containsKey(name) ? prop.get(name) : "";
    }

    private static Integer timeOut(String name) {
        return prop.containsKey(name)
                ? Integer.parseInt(prop.get(name))
                : 2000;
    }
}

