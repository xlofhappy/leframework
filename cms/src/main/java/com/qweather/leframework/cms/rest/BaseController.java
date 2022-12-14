package com.qweather.leframework.cms.rest;

import com.qweather.leframework.base.common.util.BaseConstant;
import com.qweather.leframework.base.rbac.user.service.po.UserEntity;
import com.qweather.leframework.cms.common.util.CmsConstant;
import com.qweather.leframework.cms.common.util.CmsUtil;
import com.qweather.leframework.core.properties.LeProperties;
import com.qweather.leframework.core.util.CookieUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @author xiaole
 * @date 2020-08-15 14:38:21
 */

public class BaseController {

    protected static final String PATH = "/v1/rest";

    protected final LeProperties leProperties;

    public BaseController(LeProperties leProperties) {
        this.leProperties = leProperties;
    }

    protected boolean checkSessionCode(HttpServletRequest request, String code) {
        String cacheCode = (String) request.getSession().getAttribute(CmsConstant.VERIFY_CODE);
        return Optional.ofNullable(code).orElse("-1").equalsIgnoreCase(cacheCode);
    }

    protected String createSessionCode(HttpServletRequest request) {
        // verify code
        String randomString = CmsUtil.getRandomString(4);
        request.getSession().setAttribute(CmsConstant.VERIFY_CODE, randomString);
        request.setAttribute(CmsConstant.VERIFY_CODE, randomString);
        return randomString;
    }

    protected void clearSession(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.removeCookie(request.getSession().getId(), null, request, response);
        request.getSession().invalidate();
        request.removeAttribute(request.getSession().getId());
    }

    protected UserEntity getSessionUser(HttpServletRequest request) {
        return (UserEntity) request.getSession().getAttribute(BaseConstant.SESSION_USER);
    }
}
