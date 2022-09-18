package org.le.base.rbac.user_role.service.po;


import java.io.Serializable;

/**
 * Created at 2018-08-03 14:44:56
 *
 * @author xiaole
 */
public class UserRoleEntity implements Serializable {

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 角色ID
     */
    private Long roleId;

    public UserRoleEntity() {
        super();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

}