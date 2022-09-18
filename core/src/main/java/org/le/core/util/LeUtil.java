package org.le.core.util;

import com.google.common.base.Strings;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Base Util Class
 *
 * @author xiaole
 * @date 2018-08-04 11:58:19
 */
public class LeUtil {

    /**
     * 字符串是否为 ip
     *
     * @param str 字符串
     * @return boolean
     */
    public static boolean isIp(String str) {
        return !Strings.isNullOrEmpty(str) && Pattern.matches(RegexUtil.IP_REGEX, str);
    }

    /**
     * 字符串是否为 邮箱
     *
     * @param str 字符串
     * @return boolean
     */
    public static boolean isEmail(String str) {
        return !Strings.isNullOrEmpty(str) && Pattern.matches(RegexUtil.EMAIL_REGEX, str);
    }

    /**
     * 获取随级字符串
     *
     * @param length 长度
     * @return String
     */
    public static String getRandomString(int length) {
        StringBuilder buffer = new StringBuilder("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        int range = buffer.length();
        for (int i = 0; i < length; i++) {
            sb.append(buffer.charAt(r.nextInt(range)));
        }
        return sb.toString();
    }

    public static String getRandomNumberString(int length) {
        StringBuilder buffer = new StringBuilder("0123456789");
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        int range = buffer.length();
        for (int i = 0; i < length; i++) {
            sb.append(buffer.charAt(r.nextInt(range)));
        }
        return sb.toString();
    }

    /**
     * 获取网络请求的IP地址
     *
     * @return 格式为  xxx.xxx.xxx.xxx
     * @author xiaole
     */
    public static String getIP(HttpServletRequest request) {
        String ip;
        if (request != null) {
            ip = request.getHeader("x-forwarded-for");
            String unknown = "unknown";
            if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            if (ip != null && ip.length() > 15) {
                if (ip.indexOf(",") > 0) {
                    ip = ip.substring(0, ip.indexOf(","));
                }
            }
        } else {
            InetAddress addr;
            try {
                addr = InetAddress.getLocalHost();
                //获得本机IP
                ip = addr.getHostAddress();
            } catch (UnknownHostException e) {
                ip = "127.0.0.1";
            }
        }
        return ip;
    }

    /**
     * 获得一个16位的UUID
     *
     * @return String 16位字符串
     */
    public static String getUUID() {
        return getUUID(false);
    }

    public static String getUUID(boolean withDashed) {
        String s = UUID.randomUUID().toString();
        if (withDashed) {
            return s;
        } else {
            //去掉“-”符号
            return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
        }
    }


    public static String toStackTrace(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}
