package com.qweather.leframework.base.rbac.role_permission.service.po;

import com.qweather.leframework.model.BasePo;

/**
 * Created at 2018-08-03 14:34:13
 *
 * @author xiaole
 */
public class RolePermissionEntity extends BasePo {

    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 权限ID
     */
    private Long permissionId;

    public RolePermissionEntity() {
    }

    public RolePermissionEntity(Long id) {
        super(id);
    }

    public RolePermissionEntity(Long roleId, Long permissionId) {
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

    public Long getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermissionId() {
        return this.permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

}