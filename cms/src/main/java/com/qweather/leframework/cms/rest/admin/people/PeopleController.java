package com.qweather.leframework.cms.rest.admin.people;

import com.google.common.base.Strings;
import com.qweather.leframework.base.common.util.BaseConstant;
import com.qweather.leframework.base.rbac.role.service.RoleService;
import com.qweather.leframework.base.rbac.role.service.po.RoleEntity;
import com.qweather.leframework.base.rbac.unique.service.UniqueService;
import com.qweather.leframework.base.rbac.unique.service.po.UniqueEntity;
import com.qweather.leframework.base.rbac.unique.service.po.UniqueTypeEnum;
import com.qweather.leframework.base.rbac.user.service.UserQuery;
import com.qweather.leframework.base.rbac.user.service.UserService;
import com.qweather.leframework.base.rbac.user.service.po.UserEntity;
import com.qweather.leframework.cms.common.enums.ReturnStatusEnum;
import com.qweather.leframework.cms.common.util.CmsConstant;
import com.qweather.leframework.cms.rest.admin.BaseAdminController;
import com.qweather.leframework.cms.rest.admin.people.request.PeopleRequest;
import com.qweather.leframework.core.properties.LeProperties;
import com.qweather.leframework.core.result.LeResult;
import com.qweather.leframework.core.result.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * People manage controller
 *
 * @author xiaole
 * @date 2020-08-15 16:06:00
 */
@RestController
@RequestMapping(value = PeopleController.PATH)
public class PeopleController extends BaseAdminController {

    private final Logger logger = LoggerFactory.getLogger(PeopleController.class);

    protected static final String PATH = BaseAdminController.PATH + "/people";

    private final UserService userService;
    private final RoleService roleService;
    private final UniqueService uniqueService;

    @Autowired
    public PeopleController(LeProperties leProperties, UserService userService, RoleService roleService, UniqueService uniqueService) {
        super(leProperties);
        this.userService = userService;
        this.roleService = roleService;
        this.uniqueService = uniqueService;
    }


    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public LeResult<List<UserEntity>> get(Page page, String unionId, String phone) {
        try {
            UserQuery userQuery = this.userService.createUserQuery().idNotEquals(CmsConstant.SYSTEM_ADMIN_ID);
            if (!Strings.isNullOrEmpty(unionId)) {
                userQuery = userQuery.unionIdLike(unionId);
                userQuery = userQuery.type(UniqueTypeEnum.EMAIL);
            }
            if (!Strings.isNullOrEmpty(phone)) {
                userQuery = userQuery.phone(phone);
            }
            long count = userQuery.count();
            page.setCount(count);
            List<UserEntity> userEntities = userQuery.listPage(page.getSkip(), page.getLimit());
            return LeResult.success(ReturnStatusEnum.OK.getCode(), userEntities).setPage(page);
        } catch (Exception e) {
            logger.error("get people error", e);
        }
        return LeResult.error(ReturnStatusEnum.INTERNAL_ERROR.getCode());
    }

    @PutMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public LeResult<Void> create(HttpServletRequest request) {
        try {
            UserEntity userEntity = this.userService.createUser();
            this.userService.saveUser(userEntity, true);
            return LeResult.success(ReturnStatusEnum.OK.getCode());
        } catch (Exception e) {
            logger.error("add people error", e);
        }
        return LeResult.error(ReturnStatusEnum.INTERNAL_ERROR.getCode());
    }

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public LeResult<UserEntity> edit(HttpServletRequest request, @PathVariable Long id) {
        try {
            if (!CmsConstant.SYSTEM_ADMIN_ID.equals(id)) {
                UserEntity one = this.userService.createUserQuery().id(id).one();
                if (one != null) {
                    return LeResult.success(ReturnStatusEnum.OK.getCode(), one);
                }
            }
        } catch (Exception e) {
            logger.error("get people detail error", e);
        }
        return LeResult.error("");
    }

    @PostMapping(value = "/id/{id}")
    public LeResult<UniqueEntity> editSubmit(HttpServletRequest request, @PathVariable Long id, PeopleRequest people) {
        try {
            UserEntity userEntity = this.userService.createUserQuery().id(id).one();
            if (userEntity == null) {
                return LeResult.error(ReturnStatusEnum.PARAMETER_INVALID.getCode());
            }
            // set email
            if (!Strings.isNullOrEmpty(people.getEmail())) {
                if (!people.getEmail().equals(userEntity.getEmail())) {
                    UniqueEntity one = this.uniqueService.createUniqueQuery().unionId(people.getEmail()).type(UniqueTypeEnum.EMAIL).one();
                    if (one != null) {
                        return LeResult.error(ReturnStatusEnum.EMAIL_EXISTS.getCode());
                    }
                }
                userEntity.setEmail(people.getEmail().trim());
            }
            userEntity.setPhone(people.getPhone());
            userEntity.setBirthday(people.getBirthday());
            userEntity.setCountry(people.getCountry());
            userEntity.setNickname(people.getNickname());
            userEntity.setRealname(people.getRealname());
            userEntity.setSex(people.getSex());
            this.userService.saveUser(userEntity, false);
            return LeResult.success(ReturnStatusEnum.OK.getCode());
        } catch (Exception e) {
            logger.error("update people error", e);
        }
        return LeResult.error(ReturnStatusEnum.INTERNAL_ERROR.getCode());
    }

    @PostMapping(value = "/grant/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public LeResult<Void> grant(HttpSession session, @PathVariable Long id, Long[] roles) {
        if (CmsConstant.SYSTEM_ADMIN_ID.equals(id)) {
            return LeResult.error(ReturnStatusEnum.INTERNAL_ERROR.getCode());
        }
        try {
            if (roles != null && roles.length > 0) {
                return LeResult.error(ReturnStatusEnum.PARAMETER_INVALID.getCode());
            }
            UserEntity one = this.userService.createUserQuery().id(id).one();
            if (one == null) {
                return LeResult.error(ReturnStatusEnum.PARAMETER_INVALID.getCode());
            }

            UserEntity sessionUser = (UserEntity) session.getAttribute(BaseConstant.SESSION_USER);
            List<RoleEntity> roleEntities = new ArrayList<>();
            for (int i = 0; i < roles.length; i++) {
                RoleEntity roleEntity = this.roleService.createRoleQuery().id(roles[i]).one();
                if (roleEntity != null && sessionUser.hasRole(roleEntity.getId())) {
                    if (!roleEntities.contains(roleEntity)) {
                        roleEntities.add(roleEntity);
                    }
                }
            }
            this.userService.grantUser(one, roleEntities);
            return LeResult.success(ReturnStatusEnum.OK.getCode());
        } catch (Exception e) {
            logger.error("grant people error", e);
        }
        return LeResult.error(ReturnStatusEnum.INTERNAL_ERROR.getCode());
    }

}
