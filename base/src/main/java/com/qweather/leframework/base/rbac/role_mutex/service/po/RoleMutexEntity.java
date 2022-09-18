package com.qweather.leframework.base.rbac.role_mutex.service.po;

import com.qweather.leframework.model.BasePo;

/**
 * Created at 2018-08-03 14:34:14
 *
 * @author xiaole
 */
public class RoleMutexEntity extends BasePo {

    /**
     * 角色A
     */
    private Long roleA;
    /**
     * 角色B
     */
    private Long roleB;

    public RoleMutexEntity() {
        super();
    }

    public RoleMutexEntity(Long id) {
        super(id);
    }

    public Long getRoleA() {
        return this.roleA;
    }

    public void setRoleA(Long roleA) {
        this.roleA = roleA;
    }

    public Long getRoleB() {
        return this.roleB;
    }

    public void setRoleB(Long roleB) {
        this.roleB = roleB;
    }

}