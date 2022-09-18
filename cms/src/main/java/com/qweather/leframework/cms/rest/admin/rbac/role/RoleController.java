package com.qweather.leframework.cms.rest.admin.rbac.role;

import com.qweather.leframework.base.rbac.permission.service.po.PermissionEntity;
import com.qweather.leframework.base.rbac.role.service.RoleService;
import com.qweather.leframework.base.rbac.role.service.po.RoleEntity;
import com.qweather.leframework.base.rbac.user.service.UserService;
import com.qweather.leframework.base.rbac.user.service.po.UserEntity;
import com.qweather.leframework.cms.common.enums.ReturnStatusEnum;
import com.qweather.leframework.cms.common.util.CmsConstant;
import com.qweather.leframework.cms.rest.admin.BaseAdminController;
import com.qweather.leframework.cms.rest.admin.rbac.role.request.RoleRequest;
import com.qweather.leframework.cms.rest.admin.rbac.role.response.RoleResponse;
import com.qweather.leframework.core.properties.LeProperties;
import com.qweather.leframework.core.result.LeResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Role manage controller
 *
 * @author xiaole
 * @date 2020-08-15 12:00:00
 */
@RestController
@RequestMapping(value = RoleController.PATH)
public class RoleController extends BaseAdminController {

    private final Logger logger = LoggerFactory.getLogger(RoleController.class);

    protected static final String PATH = BaseAdminController.PATH + "/rbac/role";

    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public RoleController(LeProperties leProperties, RoleService roleService, UserService userService) {
        super(leProperties);
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping(value = "/pid/{pid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public LeResult<List<RoleResponse>> get(HttpServletRequest request, @PathVariable Long pid) {
        try {
            List<RoleEntity> list = this.roleService.createRoleQuery().pid(pid).list();
            List<RoleResponse> collect = list.stream().map(roleEntity -> {
                RoleResponse roleResponse = new RoleResponse();
                roleResponse.setId(roleEntity.getId());
                roleResponse.setCode(roleEntity.getCode());
                roleResponse.setName(roleEntity.getName());
                roleResponse.setNeedRoleId(roleEntity.getNeedRoleId());
                roleResponse.setPermissionLimit(roleEntity.getPermissionLimit());
                roleResponse.setDescription(roleEntity.getDescription());
                if (!roleEntity.getClosestChildren().isEmpty()) {
                    roleResponse.setHasChildren(true);
                }
                return roleResponse;
            }).collect(Collectors.toList());
            return LeResult.success(ReturnStatusEnum.OK.getCode(), collect);
        } catch (Exception e) {
            logger.error("get role error", e);
        }
        return LeResult.error(ReturnStatusEnum.INTERNAL_ERROR.getCode());
    }

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public LeResult<RoleEntity> getEntity(@PathVariable Long id) {
        try {
            if (!CmsConstant.SYSTEM_ROLE_ROOT_ID.equals(id)) {
                RoleEntity one = this.roleService.createRoleQuery().id(id).one();
                return LeResult.success(ReturnStatusEnum.OK.getCode(), one);
            } else {
                return LeResult.error(ReturnStatusEnum.PARAMETER_INVALID.getCode());
            }
        } catch (Exception e) {
            logger.error("get role detail error", e);
        }
        return LeResult.error(ReturnStatusEnum.INTERNAL_ERROR.getCode());
    }

    @PutMapping(value = "/pid/{pid}")
    public LeResult<Void> create(@PathVariable Long pid, @RequestBody RoleRequest roleRequest) {
        try {
            RoleEntity one = this.roleService.createRoleQuery().id(pid).one();
            if (one == null) {
                return LeResult.error(ReturnStatusEnum.PARAMETER_INVALID.getCode());
            }
            RoleEntity roleEntity = this.roleService.createRole();
            roleEntity.setRemark(roleRequest.getRemark());
            roleEntity.setPid(pid);
            roleEntity.setCode(roleRequest.getCode());
            roleEntity.setName(roleRequest.getName());
            roleEntity.setNeedRoleId(roleRequest.getNeedRoleId());
            roleEntity.setPermissionLimit(roleRequest.getPermissionLimit());
            roleEntity.setDescription(roleRequest.getDescription());
            this.roleService.saveRole(roleEntity);
            return LeResult.success(ReturnStatusEnum.OK.getCode());
        } catch (Exception e) {
            logger.error("create role error", e);
        }
        return LeResult.error(ReturnStatusEnum.INTERNAL_ERROR.getCode());
    }

    @PostMapping(value = "/id/{id}")
    public LeResult<Void> update(@PathVariable Long id, RoleRequest roleRequest) {
        try {
            if (roleRequest.getNeedRoleId() != null) {
                RoleEntity roleEntity = roleService.createRoleQuery().id(roleRequest.getNeedRoleId()).one();
                if (roleEntity == null) {
                    return LeResult.error(ReturnStatusEnum.PARAMETER_INVALID.getCode());
                }
            }
            RoleEntity roleEntity = roleService.createRoleQuery().id(id).one();
            if (roleEntity == null) {
                return LeResult.success(ReturnStatusEnum.PARAMETER_INVALID.getCode());
            }
            roleEntity.setDescription(roleRequest.getDescription());
            roleEntity.setRemark(roleRequest.getRemark());
            roleEntity.setNeedRoleId(roleRequest.getNeedRoleId());
            roleEntity.setName(roleRequest.getName());
            roleEntity.setCode(roleRequest.getCode());
            roleEntity.setPermissionLimit(roleRequest.getPermissionLimit());
            this.roleService.saveRole(roleEntity);
            return LeResult.success(ReturnStatusEnum.OK.getCode());
        } catch (Exception e) {
            logger.error("update role error", e);
        }
        return LeResult.error(ReturnStatusEnum.INTERNAL_ERROR.getCode());
    }

    @DeleteMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public LeResult<Void> delete(HttpSession session, @PathVariable Long id) {
        try {
            RoleEntity one = this.roleService.createRoleQuery().id(id).one();
            if (one == null) {
                return LeResult.error(ReturnStatusEnum.PARAMETER_INVALID.getCode());
            }
            UserEntity sessionUser = (UserEntity) session.getAttribute(CmsConstant.SESSION_USER);
            UserEntity user = this.userService.createUserQuery().id(sessionUser.getId()).one();
            if (!user.hasRole(one.getId())) {
                return LeResult.error(ReturnStatusEnum.PERMISSION_DENIED.getCode());
            }

            this.roleService.deleteRole(id);
            return LeResult.success(ReturnStatusEnum.OK.getCode());
        } catch (Exception e) {
            logger.error("delete role error", e);
        }
        return LeResult.error(ReturnStatusEnum.INTERNAL_ERROR.getCode());
    }

    @GetMapping(value = "/grant/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public LeResult<List<PermissionEntity>> grant(HttpServletRequest request, @PathVariable Long id) {
        try {
            RoleEntity roleEntity = this.roleService.createRoleQuery().id(id).one();
            if (roleEntity == null) {
                return LeResult.error(ReturnStatusEnum.PARAMETER_INVALID.getCode());
            }
            UserEntity user = getSessionUser(request);
            if (!user.hasRole(roleEntity.getId())) {
                return LeResult.error(ReturnStatusEnum.PERMISSION_DENIED.getCode());
            }

            List<PermissionEntity> permissionEntities = user.getPermissionEntities();
            List<PermissionEntity> topPermissionEntities = permissionEntities.stream().map(permissionEntity -> {
                PermissionEntity parent = permissionEntity.getParent();
                boolean ifContains = permissionEntities.contains(parent);
                // find user's each permission tree's top permission
                while (parent != null && ifContains) {
                    permissionEntity = parent;
                    parent = parent.getParent();
                    ifContains = permissionEntities.contains(permissionEntity);
                }
                permissionEntity.removeNoAccessNodeCascade(user);
                return permissionEntity;
            }).distinct().collect(Collectors.toList());

            return LeResult.success(ReturnStatusEnum.OK.getCode(), topPermissionEntities);
        } catch (Exception e) {
            logger.error("get role detail error", e);
        }
        return LeResult.error(ReturnStatusEnum.INTERNAL_ERROR.getCode());
    }

    @PostMapping(value = "/grant/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public LeResult<Void> grant(HttpSession session, @PathVariable Long id, Long[] permissions) {
        try {
            if (permissions == null || permissions.length <= 0) {
                return LeResult.error(ReturnStatusEnum.PARAMETER_INVALID.getCode());
            }
            RoleEntity roleEntity = this.roleService.createRoleQuery().id(id).one();
            if (roleEntity == null) {
                return LeResult.error(ReturnStatusEnum.PARAMETER_INVALID.getCode());
            }

            UserEntity user = (UserEntity) session.getAttribute(CmsConstant.SESSION_USER);
            if (!user.hasRole(roleEntity.getId())) {
                return LeResult.error(ReturnStatusEnum.PERMISSION_DENIED.getCode());
            }

            this.roleService.grantPermission(user, id, permissions);
            return LeResult.success(ReturnStatusEnum.OK.getCode());
        } catch (Exception e) {
            logger.error("role grant error", e);
        }
        return LeResult.error(ReturnStatusEnum.INTERNAL_ERROR.getCode());
    }

}
