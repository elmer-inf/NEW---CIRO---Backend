package atc.riesgos.auth.util;


import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Grupo TIC
 * @copyright (c) 2019, ATC - Red Enlace
 * @developer David Quenallata, dqpsoft@gmail.com
 */
public class StringPassword {

    private static PasswordEncoder encoder;
    private static StandardPBEStringEncryptor standard;

    final public static String MASTER_KEY = StringPassword.key();
    final public static String MASTER_KEY_ENCODED = StringPassword.master();

    private static String master() {

        return encoder().encode(MASTER_KEY);
    }

    /**
     *
     * @return
     */
    final public static PasswordEncoder encoder() {

        if (encoder == null) {

            encoder = new BCryptPasswordEncoder();
        }

        return encoder;
    }

    /**
     *
     * @return
     */
    final public static StandardPBEStringEncryptor get() {

        if (standard == null) {

            standard = new StandardPBEStringEncryptor();

            standard.setAlgorithm("PBEWithMD5AndTripleDES");

            standard.setPassword(MASTER_KEY);
        }

        return standard;
    }

    /**
     *
     * @return
     */
    private static String key() {
        try {
            StringBuilder sb = new StringBuilder();

            InetAddress ip = InetAddress.getLocalHost();

            NetworkInterface network = NetworkInterface.getByInetAddress(ip);

            byte[] mac = network.getHardwareAddress();

            if (mac != null) {

                for (int i = 0; i < mac.length; i++) {

                    sb.append(String.format("%02X%s", mac[i], i < mac.length - 1 ? "-" : ""));
                }
            }
          //  String k = toSHA256(String.format("%s;%s", ip.getHostAddress(), sb.toString()));
            return toSHA256(String.format("%s;%s", ip.getHostAddress(), sb.toString()));

           // return k;

        } catch (SocketException | UnknownHostException ex) {

            return null;
        }
    }

    /**
     *
     * @param text
     * @return
     */
    final public static String toSHA256(String text) {

        try {

            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] encoded = digest.digest(text.getBytes(StandardCharsets.UTF_8));

            return toHex(encoded);

        } catch (NoSuchAlgorithmException ex) {

            return null;
        }
    }

    /**
     *
     * @param hash
     * @return
     */
    private static String toHex(byte[] hash) {

        StringBuilder hexString = new StringBuilder();

        for (int i = 0; i < hash.length; i++) {

            String hex = Integer.toHexString(0xff & hash[i]);

            if (hex.length() == 1) {

                hexString.append('0');
            }

            hexString.append(hex);
        }

        return hexString.toString();
    }
}
