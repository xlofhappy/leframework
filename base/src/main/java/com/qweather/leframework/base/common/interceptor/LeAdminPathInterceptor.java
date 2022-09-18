package com.qweather.leframework.base.common.interceptor;

import com.qweather.leframework.base.common.util.BaseConstant;
import com.qweather.leframework.base.rbac.permission.service.PermissionService;
import com.qweather.leframework.base.rbac.permission.service.po.PermissionEntity;
import com.qweather.leframework.base.rbac.user.service.po.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Intercept the backend path
 * inject the menu for  breadcrumbs
 *
 * @author xiaole
 * @date 2018-11-02 10:48:51
 */
public class LeAdminPathInterceptor implements HandlerInterceptor {

    private final Map<String, PermissionEntity> cachedMenu = new HashMap<>();

    private final PermissionService permissionService;

    @Autowired
    public LeAdminPathInterceptor(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {
        String uri = request.getRequestURI();
        UserEntity user = (UserEntity) request.getSession().getAttribute(BaseConstant.SESSION_USER);
        if (user != null) {
            List<PermissionEntity> permissionEntities = user.getPermissionEntities();
            if (permissionEntities != null) {
                PermissionEntity permissionEntity = permissionEntities.stream().filter(p -> p.verify(uri)).findFirst().orElse(null);
                if (permissionEntity != null) {
                    PermissionEntity menu;
                    String url = permissionEntity.getUrl();
                    if (cachedMenu.containsKey(url)) {
                        menu = cachedMenu.get(url);
                    } else {
                        String method = request.getMethod();
                        synchronized (this) {
                            menu = permissionService.createPermissionQuery().url(url).operationLike(method).one();
                            cachedMenu.put(url, menu);
                        }
                    }
                    request.setAttribute(BaseConstant.SESSION_USER_ADMIN_MENU, menu);
                }
            }
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

}
