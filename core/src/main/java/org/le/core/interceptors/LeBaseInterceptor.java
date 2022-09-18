package org.le.core.interceptors;

import com.google.common.base.Strings;
import org.le.core.properties.LeProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * CMS base Interceptor
 * init base info
 *
 * @author xiaole
 * @date 2018-11-01 13:48:51
 */
public class LeBaseInterceptor implements HandlerInterceptor {

    private final ServerProperties serverProperties;
    private final LeProperties leProperties;

    public LeBaseInterceptor(ServerProperties serverProperties, LeProperties leProperties) {
        this.serverProperties = serverProperties;
        this.leProperties = leProperties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute("appDomain", getBaseDomain(request));
        request.setAttribute("resourceVersion", leProperties.getHttp().getResourceVersion());
        if (leProperties.getHttp().isAppUri()) {
            request.setAttribute("appUri", request.getRequestURI());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) {
    }

    private String getBaseDomain(HttpServletRequest request) {
        String scheme = null;
        if (serverProperties.getTomcat() != null && !Strings.isNullOrEmpty(serverProperties.getTomcat().getProtocolHeader())) {
            scheme = request.getHeader(serverProperties.getTomcat().getProtocolHeader());
        }
        StringBuffer urlTemp = request.getRequestURL();
        String contextPath = request.getContextPath();
        String baseUrl = urlTemp.delete(urlTemp.length() - request.getRequestURI().length(), urlTemp.length()).append(contextPath).toString();
        if (contextPath.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }
        if (!Strings.isNullOrEmpty(scheme)) {
            baseUrl = baseUrl.replaceAll("^" + request.getScheme(), scheme);
        }
        return baseUrl;
    }

}
