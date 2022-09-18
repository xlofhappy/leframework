package org.le.cms.rest.admin.rbac.role.request;

/**
 * @author xiaole
 * @date 2020-08-15 15:35:27
 */
public class RoleRequest {

    private String remark;
    /**
     * 多语言字符串
     */
    private String code;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 权限个数限制
     */
    private Integer permissionLimit;
    /**
     * 先决条件角色
     */
    private Long needRoleId;
    /**
     * 角色描述
     */
    private String description;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPermissionLimit() {
        return permissionLimit;
    }

    public void setPermissionLimit(Integer permissionLimit) {
        this.permissionLimit = permissionLimit;
    }

    public Long getNeedRoleId() {
        return needRoleId;
    }

    public void setNeedRoleId(Long needRoleId) {
        this.needRoleId = needRoleId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
