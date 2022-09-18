package org.le.cms.rest.admin.rbac.permission;

import org.le.base.rbac.permission.service.PermissionService;
import org.le.base.rbac.permission.service.po.PermissionEntity;
import org.le.base.rbac.user.service.po.UserEntity;
import org.le.cms.common.enums.ReturnStatusEnum;
import org.le.cms.common.util.CmsConstant;
import org.le.cms.rest.admin.BaseAdminController;
import org.le.core.properties.LeProperties;
import org.le.core.result.LeResult;
import org.le.model.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Role manage controller
 *
 * @author xiaole
 * @date 2019-03-01 12:00:00
 */
@RestController
@RequestMapping(value = PermissionController.PATH)
public class PermissionController extends BaseAdminController {

    private final Logger logger = LoggerFactory.getLogger(PermissionController.class);

    protected static final String PATH = BaseAdminController.PATH + "/rbac/permission";

    private final PermissionService permissionService;

    @Autowired
    public PermissionController(LeProperties leProperties, PermissionService permissionService) {
        super(leProperties);
        this.permissionService = permissionService;
    }

    @GetMapping(value = "/pid/{pid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public LeResult<List<PermissionEntity>> get(@PathVariable Long pid) {
        try {
            if (CmsConstant.SYSTEM_PERMISSION_ROOT_ID.equals(pid)) {
                return LeResult.error(ReturnStatusEnum.PARAMETER_INVALID.getCode());
            }
            List<PermissionEntity> list = this.permissionService.createPermissionQuery().pid(pid).orderBySort(Query.Direction.ASCENDING).list();
            return LeResult.success(ReturnStatusEnum.OK.getCode(), list);
        } catch (Exception e) {
            logger.error("get permission list error", e);
        }
        return LeResult.error(ReturnStatusEnum.INTERNAL_ERROR.getCode());
    }

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public LeResult<PermissionEntity> getDetail(@PathVariable Long id) {
        try {
            if (CmsConstant.SYSTEM_PERMISSION_ROOT_ID.equals(id)) {
                return LeResult.error(ReturnStatusEnum.PARAMETER_INVALID.getCode());
            }
            PermissionEntity one = this.permissionService.createPermissionQuery().id(id).one();
            if (one == null) {
                return LeResult.error(ReturnStatusEnum.PARAMETER_INVALID.getCode());
            }
            return LeResult.success(ReturnStatusEnum.OK.getCode(), one);
        } catch (Exception e) {
            logger.error("get permission detail error", e);
        }
        return LeResult.error(ReturnStatusEnum.INTERNAL_ERROR.getCode());
    }

    @PostMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public LeResult<Void> update(@PathVariable Long id, PermissionEntity permission) {
        try {
            this.permissionService.savePermission(permission);
            return LeResult.success(ReturnStatusEnum.OK.getCode());
        } catch (Exception e) {
            logger.error("update permission error", e);
        }
        return LeResult.error(ReturnStatusEnum.INTERNAL_ERROR.getCode());
    }

    @DeleteMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public LeResult<Void> delete(HttpSession session, @PathVariable Long id) {
        try {
            PermissionEntity one = this.permissionService.createPermissionQuery().id(id).one();
            if (one == null) {
                return LeResult.error(ReturnStatusEnum.PARAMETER_INVALID.getCode());
            }
            UserEntity user = (UserEntity) session.getAttribute(CmsConstant.SESSION_USER);
            if (!user.hasPermission(one)) {
                return LeResult.error(ReturnStatusEnum.PERMISSION_DENIED.getCode());
            }
            this.permissionService.deletePermission(id);
            return LeResult.success(ReturnStatusEnum.OK.getCode());
        } catch (Exception e) {
            logger.error("delete permission error", e);
        }
        return LeResult.error(ReturnStatusEnum.INTERNAL_ERROR.getCode());
    }

}
