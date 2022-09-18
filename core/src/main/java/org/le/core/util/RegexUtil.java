package org.le.core.util;

/**
 * @author xiaole
 */
public class RegexUtil {

    /**
     * IP
     */
    public static String IP_REGEX    = "^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$";
    /**
     * Email
     */
    public static String EMAIL_REGEX = "[A-Za-z0-9._%-]+@[A-Za-z0-9._%-]+\\.[A-Za-z]{2,10}";
}
