package org.le.base.common.interceptor;

import org.le.base.common.util.BaseConstant;
import org.le.base.rbac.permission.service.po.PermissionEntity;
import org.le.base.rbac.user.service.po.UserEntity;
import org.le.core.properties.LePropertyPath;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Auth check
 * Created by xiaole on 2015-06-23.
 * Modified by xiaole on 2018-08-01.
 *
 * @author xiaole
 */
public class LeAuthInterceptor implements HandlerInterceptor {

    private List<String> free;
    private List<String> login;
    private String errorCode;
    private String redirectKey;
    private String unAuthorized;

    private final LePropertyPath lePropertyPath;
    private final PathMatcher pathMatcher;

    public LeAuthInterceptor(LePropertyPath lePropertyPath, PathMatcher pathMatcher) {
        this.lePropertyPath = lePropertyPath;
        this.pathMatcher = pathMatcher;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute(this.redirectKey, request.getSession().getAttribute(this.redirectKey));
        String uri = request.getRequestURI();

        // unauthorized , passed
        if (uri.equals(unAuthorized)) {
            return true;
        }

        if (free != null && free.parallelStream().anyMatch(path -> pathMatcher.match(path, uri))) {
            return true;
        }

        UserEntity user = (UserEntity) request.getSession().getAttribute(BaseConstant.SESSION_USER);

        if (user == null) {
           return false;
        } else if (login != null && login.parallelStream().anyMatch(path -> pathMatcher.match(path, uri))) {
            return true;
        } else {
            List<PermissionEntity> permissionEntities = user.getPermissionEntities();
            if (permissionEntities.stream().anyMatch(p -> p.verify(uri))) {
                return true;
            }
            response.sendRedirect(this.unAuthorized);
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) {
    }

    @PostConstruct
    public void init() {
        if (lePropertyPath != null) {
            this.login = lePropertyPath.getLogin();
            this.errorCode = lePropertyPath.getErrorCode();
            this.unAuthorized = lePropertyPath.getUnAuthorized();
            this.redirectKey = lePropertyPath.getRedirectKey();
        }
    }


}
