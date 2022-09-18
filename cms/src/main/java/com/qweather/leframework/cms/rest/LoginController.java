package com.qweather.leframework.cms.rest;

import com.google.common.base.Strings;
import com.qweather.leframework.base.common.util.BaseConstant;
import com.qweather.leframework.base.rbac.permission.service.PermissionService;
import com.qweather.leframework.base.rbac.permission.service.po.PermissionEntity;
import com.qweather.leframework.base.rbac.unique.service.po.UniqueTypeEnum;
import com.qweather.leframework.base.rbac.user.service.UserService;
import com.qweather.leframework.base.rbac.user.service.po.UserEntity;
import com.qweather.leframework.cms.common.enums.ReturnStatusEnum;
import com.qweather.leframework.cms.common.util.CmsConstant;
import com.qweather.leframework.core.properties.LeProperties;
import com.qweather.leframework.core.result.LeResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author xiaole
 */
@RestController
@RequestMapping(value = LoginController.PATH)
public class LoginController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    protected static final String PATH = BaseController.PATH + "/login";

    private final UserService userService;
    private final PermissionService permissionService;

    public LoginController(LeProperties leProperties, UserService userService, PermissionService permissionService) {
        super(leProperties);
        this.userService = userService;
        this.permissionService = permissionService;
    }

    @PostMapping(value = "/do", produces = MediaType.APPLICATION_JSON_VALUE)
    public LeResult<Void> loginSubmit(HttpSession session, String username, String password, String verify) {

        if (Strings.isNullOrEmpty(username) || Strings.isNullOrEmpty(password)) {
            return LeResult.error(ReturnStatusEnum.PARAMETER_INVALID.getCode());
        }
        UserEntity user = (UserEntity) session.getAttribute(CmsConstant.SESSION_USER);
        if (user != null) {
            return LeResult.success(ReturnStatusEnum.LOGGED.getCode());
        }

        String verifyCode = (String) session.getAttribute(CmsConstant.VERIFY_CODE);
        if (Strings.isNullOrEmpty(verify) || !verify.equalsIgnoreCase(verifyCode)) {
            return LeResult.error(ReturnStatusEnum.VERIFY_CODE_ERROR.getCode());
        }

        try {
            user = this.userService.login(username, UniqueTypeEnum.EMAIL, password);
            if (user != null) {
                List<PermissionEntity> userMenu = this.permissionService.listMenuByUser(user, CmsConstant.USER_PERMISSION_ROOT_ID);
                session.setAttribute(BaseConstant.SESSION_USER_MENU, userMenu);
                if (user.isAdmin()) {
                    List<PermissionEntity> adminMenu = this.permissionService.listMenuByUser(user, CmsConstant.ADMIN_PERMISSION_ROOT_ID);
                    session.setAttribute(BaseConstant.SESSION_USER_ADMIN_MENU, adminMenu);
                }

                session.setAttribute(BaseConstant.SESSION_USER, user);
                return LeResult.success(ReturnStatusEnum.OK.getCode());
            } else {
                return LeResult.error(ReturnStatusEnum.LOGIN_FAILED.getCode());
            }
        } catch (Exception ex) {
            logger.error(" login failed ", ex);
        }

        return LeResult.error(ReturnStatusEnum.INTERNAL_ERROR.getCode());
    }

    @GetMapping(value = "/logout")
    public LeResult<Void> logout(HttpSession session, HttpServletResponse response) {
        session.invalidate();
        return LeResult.success(ReturnStatusEnum.OK.getCode());
    }

}
