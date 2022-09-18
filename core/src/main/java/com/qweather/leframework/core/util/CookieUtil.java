package com.qweather.leframework.core.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xiaole on 15/11/11.
 * @author xiaole 
 */
public class CookieUtil {

    /**
     * 添加cookie
     *
     * @param name     cookie的key
     * @param value    cookie的value
     * @param response 响应对象
     */
    public static void addCookie(String name, String value, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 添加cookie
     *
     * @param name     cookie的key
     * @param value    cookie的value
     * @param domain   domain
     *                 ＠param  path path
     * @param maxAge   最长存活时间 单位为秒
     * @param response 响应
     */
    public static void addCookie(String name, String value, String domain,
                                 int maxAge, String path, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, value);
        if (domain != null) {
            cookie.setDomain(domain);
        }
        cookie.setMaxAge(maxAge);
        cookie.setPath(path);
        response.addCookie(cookie);
    }

    /**
     * 往根下面存一个cookie
     * * @param name cookie的key
     *
     * @param value    cookie的value
     * @param domain   domain
     * @param maxAge   最长存活时间 单位为秒
     * @param response 响应
     */
    public static void addCookie(String name, String value, String domain,
                                 int maxAge, HttpServletResponse response) {
        addCookie(name, value, domain, maxAge, "/", response);
    }

    /**
     * 从cookie值返回cookie值，如果没有返回 null
     *
     * @param request 请求
     * @param name key
     * @return cookie的值
     */
    public static String getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals(name)) {
                return cookies[i].getValue();
            }
        }
        return null;
    }

    public static void removeCookie(String name, String domain, HttpServletRequest request, HttpServletResponse response) {
        String cookieVal = getCookie(request, name);
        if (cookieVal != null) {
            CookieUtil.addCookie(name, null, domain, 0, response);
        }
    }

    public static void clearCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                removeCookie(cookies[i].getName(), cookies[i].getDomain(), request, response);
            }
        }
    }

    public static void clearDomainCookie(String domain, HttpServletRequest request, HttpServletResponse response) {
        if(domain==null || "".equals(domain.trim())){
            clearCookie(request, response);
        }else {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (int i = 0; i < cookies.length; i++) {
                    removeCookie(cookies[i].getName(), domain, request, response);
                }
            }
        }
    }

}
